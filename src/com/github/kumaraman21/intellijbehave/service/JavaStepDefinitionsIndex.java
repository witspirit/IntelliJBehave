package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStepLine;
import com.github.kumaraman21.intellijbehave.utility.TokenMap;
import com.intellij.lang.Language;
import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.java.stubs.index.JavaFullClassNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Arrays.asList;

public class JavaStepDefinitionsIndex {
    private final PsiManager manager;
    private TokenMap tokenMap = new TokenMap();
    private AtomicBoolean needsUpdate = new AtomicBoolean(true);
    private ChangeListener changeListener = new ChangeListener(this);

    public JavaStepDefinitionsIndex(Project project) {
        manager = PsiManager.getInstance(project);
    }

    public static JavaStepDefinitionsIndex getInstance(Project project) {
        return ServiceManager.getService(project, JavaStepDefinitionsIndex.class);
    }

    @NotNull
    private static Integer getPriorityByDefinition(@Nullable JavaStepDefinition definition) {
        if (definition == null) {
            return -1;
        }

        return definition.getAnnotationPriority();
    }

    @NotNull
    private static Collection<PsiAnnotation> getAllStepAnnotations(@NotNull final PsiClass annClass,
                                                                   @NotNull final GlobalSearchScope scope) {
        return ApplicationManager.getApplication().runReadAction(new Computable<Collection<PsiAnnotation>>() {
            @Override
            public Collection<PsiAnnotation> compute() {
                return JavaAnnotationIndex.getInstance().get(annClass.getName(), annClass.getProject(), scope);
            }
        });
    }

    private synchronized void needsUpdate() {
        manager.removePsiTreeChangeListener(changeListener);
        needsUpdate.set(true);
    }

    private synchronized void updated() {
        needsUpdate.set(false);
        manager.addPsiTreeChangeListener(changeListener);
    }

    @NotNull
    public TokenMap findAllStepDefinitionsByType(@NotNull ScenarioStep step) {
        if (needsUpdate.get()) {
            Module module = ModuleUtilCore.findModuleForPsiElement(step);

            if (module == null) {
                return new TokenMap();
            }
            this.tokenMap = loadStepsFor(module);
            updated();
        }
        return this.tokenMap;
    }

    @NotNull
    public Collection<JavaStepDefinition> findStepDefinitions(@NotNull ScenarioStep step) {
        final TokenMap tokenMap = findAllStepDefinitionsByType(step);
        return getJavaStepDefinitions(step, tokenMap);
    }

    @NotNull
    public Collection<JavaStepDefinition> getJavaStepDefinitions(@NotNull ScenarioStep step,
                                                                 @NotNull TokenMap tokenMap) {
        if (tokenMap.isEmpty()) return Collections.emptyList();
        final Map<Class, JavaStepDefinition> definitionsByClass = new HashMap<Class, JavaStepDefinition>();

        final String storyLine = getStoryLineShrinked(step);
        final String text = step.getAnnotatedStoryLine();

        final List<JavaStepDefinition> stepDefinitions = tokenMap.getConcerned(text, true);
        for (JavaStepDefinition stepDefinition : stepDefinitions) {
            if (stepDefinition != null && stepDefinition.matches(storyLine) && stepDefinition.supportsStep(step)) {
                Integer currentHighestPriority = getPriorityByDefinition(
                        definitionsByClass.get(stepDefinition.getClass()));
                Integer newPriority = getPriorityByDefinition(stepDefinition);

                if (newPriority > currentHighestPriority) {
                    definitionsByClass.put(stepDefinition.getClass(), stepDefinition);
                }
            }

        }
        return definitionsByClass.values();
    }

    private String getStoryLineShrinked(@NotNull final ScenarioStep step) {
        JBehaveStepLine storyStepLine = step.getStoryStepLine();
        if (storyStepLine != null) {
            String storyLine = storyStepLine.getText();
            if (step.hasStoryStepPostParameters()) {
                return storyLine + " dummy";
            }
            return storyLine;
        }
        return "";
    }

//    private String getTableOffset(@NotNull final ScenarioStep step) {
//        String storyLine = step.getAnnotatedStoryLine();
//        if (step.hasStoryStepPostParameters()) {
//            return storyLine + " dummy";
//        }
//        return storyLine;
//    }

    @NotNull
    public TokenMap loadStepsFor(@NotNull Module module) {
        GlobalSearchScope dependenciesScope = module.getModuleWithDependenciesAndLibrariesScope(true);

        PsiClass givenAnnotationClass = findStepAnnotation(Given.class.getName(), module, dependenciesScope);
        PsiClass whenAnnotationClass = findStepAnnotation(When.class.getName(), module, dependenciesScope);
        PsiClass thenAnnotationClass = findStepAnnotation(Then.class.getName(), module, dependenciesScope);

        if (givenAnnotationClass == null || whenAnnotationClass == null || thenAnnotationClass == null) {
            return new TokenMap();
        }

        TokenMap result = new TokenMap();
        List<PsiClass> stepAnnotations = asList(givenAnnotationClass, whenAnnotationClass, thenAnnotationClass);
        for (PsiClass stepAnnotation : stepAnnotations) {
            Collection<PsiAnnotation> allStepAnnotations = getAllStepAnnotations(stepAnnotation, dependenciesScope);

            for (PsiAnnotation stepDefAnnotation : allStepAnnotations) {
                result.put(new JavaStepDefinition(stepDefAnnotation));
            }
        }

        return result;
    }

    @Nullable
    private PsiClass findStepAnnotation(String stepClass, Module module, GlobalSearchScope dependenciesScope) {
        Collection<PsiClass> stepDefAnnotationCandidates = JavaFullClassNameIndex.getInstance().get(
                stepClass.hashCode(), module.getProject(), dependenciesScope);

        for (PsiClass stepDefAnnotations : stepDefAnnotationCandidates) {
            if (stepClass.equals(stepDefAnnotations.getQualifiedName())) {
                return stepDefAnnotations;
            }
        }

        return null;
    }

    private class ChangeListener implements PsiTreeChangeListener {
        private JavaStepDefinitionsIndex theIndexer;

        public ChangeListener(JavaStepDefinitionsIndex theIndexer) {
            this.theIndexer = theIndexer;
        }

        @Override
        public void beforeChildAddition(@NotNull PsiTreeChangeEvent event) {

        }

        @Override
        public void beforeChildRemoval(@NotNull PsiTreeChangeEvent event) {

        }

        @Override
        public void beforeChildReplacement(@NotNull PsiTreeChangeEvent event) {

        }

        @Override
        public void beforeChildMovement(@NotNull PsiTreeChangeEvent event) {

        }

        @Override
        public void beforeChildrenChange(@NotNull PsiTreeChangeEvent event) {

        }

        @Override
        public void beforePropertyChange(@NotNull PsiTreeChangeEvent event) {

        }

        private void changed(@NotNull PsiTreeChangeEvent event) {
            PsiFile file = event.getFile();
            if (file != null) {
                Language language = file.getLanguage();
                if (language == JavaLanguage.INSTANCE) {
                    theIndexer.needsUpdate();
                }
            }
        }

        @Override
        public void childAdded(@NotNull PsiTreeChangeEvent event) {
            changed(event);
        }

        @Override
        public void childRemoved(@NotNull PsiTreeChangeEvent event) {
            changed(event);

        }

        @Override
        public void childReplaced(@NotNull PsiTreeChangeEvent event) {
            changed(event);

        }

        @Override
        public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
            changed(event);

        }

        @Override
        public void childMoved(@NotNull PsiTreeChangeEvent event) {
            changed(event);

        }

        @Override
        public void propertyChanged(@NotNull PsiTreeChangeEvent event) {

        }
    }
}
