package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStepLine;
import com.github.kumaraman21.intellijbehave.utility.TokenMap;
import com.intellij.codeInsight.completion.CompletionUtilCore;
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
    private final Map<Module, TokenMap<JavaStepDefinition>> tokenMaps =
            new HashMap<Module, TokenMap<JavaStepDefinition>>();
    private final AtomicBoolean needsUpdate = new AtomicBoolean(true);
    private final ChangeListener changeListener = new ChangeListener(this);

    private JavaStepDefinitionsIndex(Project project) {
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
        tokenMaps.clear();
    }

    private synchronized void updated() {
        needsUpdate.set(false);
        manager.addPsiTreeChangeListener(changeListener);
    }

    @NotNull
    public TokenMap<JavaStepDefinition> findAllStepDefinitionsByType(@NotNull ScenarioStep step) {
        Module module = ModuleUtilCore.findModuleForPsiElement(step);

        if (module == null) {
            return new TokenMap<JavaStepDefinition>();
        }
        if (needsUpdate.get()) {
            updated();
        }
        TokenMap<JavaStepDefinition> tokenMap = tokenMaps.get(module);
        if (tokenMap == null) {
            tokenMap = loadStepsFor(module);
            tokenMaps.put(module, tokenMap);
        }
        return tokenMap;
    }

    @NotNull
    public Collection<JavaStepDefinition> findStepDefinitions(@NotNull ScenarioStep step) {
        final TokenMap<JavaStepDefinition> tokenMap = findAllStepDefinitionsByType(step);
        return getJavaStepDefinitions(step, tokenMap);
    }

    @NotNull
    private static Collection<JavaStepDefinition> getJavaStepDefinitions(@NotNull ScenarioStep step,
                                                                         @NotNull TokenMap<JavaStepDefinition> tokenMap) {
        if (tokenMap.isEmpty()) return Collections.emptyList();
        final Map<Class, JavaStepDefinition> definitionsByClass = new HashMap<Class, JavaStepDefinition>();

        String reallyFind = step.getAnnotatedStoryLine();
        final int rulezzz = reallyFind.indexOf(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED);
        if (rulezzz >= 0) {
            reallyFind = reallyFind.substring(0, rulezzz);
        }
        final List<JavaStepDefinition> stepDefinitions = tokenMap.get(reallyFind, true);
        for (JavaStepDefinition stepDefinition : stepDefinitions) {
            if (stepDefinition != null) {
                Integer currentHighestPriority =
                        getPriorityByDefinition(definitionsByClass.get(stepDefinition.getClass()));
                Integer newPriority = getPriorityByDefinition(stepDefinition);

                if (newPriority > currentHighestPriority) {
                    definitionsByClass.put(stepDefinition.getClass(), stepDefinition);
                }
            }

        }
        return definitionsByClass.values();
    }

    private static String getStoryLineShrinked(@NotNull final ScenarioStep step) {
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
    private TokenMap<JavaStepDefinition> loadStepsFor(@NotNull Module module) {
        GlobalSearchScope dependenciesScope = module.getModuleWithDependenciesAndLibrariesScope(true);

        PsiClass givenAnnotationClass = findStepAnnotation(Given.class.getName(), module, dependenciesScope);
        PsiClass whenAnnotationClass = findStepAnnotation(When.class.getName(), module, dependenciesScope);
        PsiClass thenAnnotationClass = findStepAnnotation(Then.class.getName(), module, dependenciesScope);

        if (givenAnnotationClass == null || whenAnnotationClass == null || thenAnnotationClass == null) {
            return new TokenMap<JavaStepDefinition>();
        }

        TokenMap<JavaStepDefinition> result = new TokenMap<JavaStepDefinition>();
        List<PsiClass> stepAnnotations = asList(givenAnnotationClass, whenAnnotationClass, thenAnnotationClass);
        for (PsiClass stepAnnotation : stepAnnotations) {
            Collection<PsiAnnotation> allStepAnnotations = getAllStepAnnotations(stepAnnotation, dependenciesScope);

            for (PsiAnnotation stepDefAnnotation : allStepAnnotations) {
                JavaStepDefinition javaStepDefinition = new JavaStepDefinition(stepDefAnnotation);
                Collection<String> paths = javaStepDefinition.toStrings();
                result.put(javaStepDefinition, paths);
            }
        }

        return result;
    }

    @Nullable
    private PsiClass findStepAnnotation(String stepClass, Module module, GlobalSearchScope dependenciesScope) {
        Collection<PsiClass> stepDefAnnotationCandidates =
                JavaFullClassNameIndex.getInstance().get(stepClass.hashCode(), module.getProject(), dependenciesScope);

        for (PsiClass stepDefAnnotations : stepDefAnnotationCandidates) {
            if (stepClass.equals(stepDefAnnotations.getQualifiedName())) {
                return stepDefAnnotations;
            }
        }

        return null;
    }

    private static class ChangeListener extends PsiTreeChangeAdapter {
        private final JavaStepDefinitionsIndex theIndexer;

        public ChangeListener(JavaStepDefinitionsIndex theIndexer) {
            this.theIndexer = theIndexer;
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

    }
}
