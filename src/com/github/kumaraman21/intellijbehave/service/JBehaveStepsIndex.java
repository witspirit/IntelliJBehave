package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.utility.TokenMap;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.java.stubs.index.JavaFullClassNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static java.util.Arrays.asList;

public class JBehaveStepsIndex {

    private static final TreeSet<JavaStepDefinition> emptyTreeSet = new TreeSet<JavaStepDefinition>();

    public static JBehaveStepsIndex getInstance(Project project) {
        return ServiceManager.getService(project, JBehaveStepsIndex.class);
    }

    @NotNull
    public TokenMap findAllStepDefinitionsByType(@NotNull JBehaveStep step) {
        Module module = ModuleUtilCore.findModuleForPsiElement(step);

        if (module == null) {
            return new TokenMap();
        }

        return loadStepsFor(module);
    }

    @NotNull
    public Collection<JavaStepDefinition> findStepDefinitions(@NotNull JBehaveStep step) {
        final TokenMap tokenMap = findAllStepDefinitionsByType(step);
        return getJavaStepDefinitions(step, tokenMap);
    }

    @NotNull
    public Collection<JavaStepDefinition> getJavaStepDefinitions(@NotNull JBehaveStep step,
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

    private String getStoryLineShrinked(@NotNull final JBehaveStep step) {
        String storyLine = step.getStoryStepLine().getText();
        if (step.hasStoryStepPostParameters()) {
            return storyLine + " dummy";
        }
        return storyLine;
    }

    private String getTableOffset(@NotNull final JBehaveStep step) {
        String storyLine = step.getAnnotatedStoryLine();
        if (step.hasStoryStepPostParameters()) {
            return storyLine + " dummy";
        }
        return storyLine;
    }

    @NotNull
    private static Integer getPriorityByDefinition(@Nullable JavaStepDefinition definition) {
        if (definition == null) {
            return -1;
        }

        return definition.getAnnotationPriority();
    }

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
}
