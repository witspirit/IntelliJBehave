package com.github.kumaraman21.intellijbehave.service;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.java.stubs.index.JavaFullClassNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caches the {@link PsiClass} instances of the {@link Given}, {@link When} and {@link Then} classes
 * for each module in the project.
 */
@Service(Service.Level.PROJECT)
final class StepAnnotationsCache {
    private final Map<Module, StepAnnotations> stepAnnotationClasses = new ConcurrentHashMap<>(4);
    private final Project project;

    public StepAnnotationsCache(Project project) {
        this.project = project;
    }

    /**
     * Caches the Given-When-Then annotations' PsiClass instances for the provided module if they haven't been.
     *
     * @return the cached PsiClass instances wrapped in a {@link StepAnnotations}.
     */
    public StepAnnotations cacheStepAnnotations(Module module, GlobalSearchScope dependenciesScope) {
        return stepAnnotationClasses.computeIfAbsent(module, __ -> new StepAnnotations(
            findStepAnnotation(Given.class.getName(), dependenciesScope),
            findStepAnnotation(When.class.getName(), dependenciesScope),
            findStepAnnotation(Then.class.getName(), dependenciesScope)
        ));
    }

    /**
     * Finds the {@code PsiClass} instance of the {@code stepAnnotationClassFqn}.
     *
     * @param stepAnnotationClassFqn the fully qualified name of the step annotation to find
     * @param dependenciesScope the dependencies scope within the current module. See {@link JBehaveStepsIndex#loadStepsFor(Module)}.
     * @return the PsiClass instance for the step annotation, or null if not found
     */
    @Nullable("When there is no annotation class found.")
    private PsiClass findStepAnnotation(String stepAnnotationClassFqn, GlobalSearchScope dependenciesScope) {
        var stepDefAnnotationCandidates = JavaFullClassNameIndex.getInstance().get(stepAnnotationClassFqn, project, dependenciesScope);
        for (PsiClass stepDefAnnotations : stepDefAnnotationCandidates) {
            if (stepAnnotationClassFqn.equals(stepDefAnnotations.getQualifiedName())) {
                return stepDefAnnotations;
            }
        }
        return null;
    }

    public static StepAnnotationsCache getInstance(Project project) {
        return project.getService(StepAnnotationsCache.class);
    }

    /**
     * Wrapper class for the Given-When-Then annotation classes for easier caching and handling.
     */
    record StepAnnotations(@Nullable PsiClass given, @Nullable PsiClass when, @Nullable PsiClass then) {
        boolean isAnyAnnotationMissing() {
            return given == null || when == null || then == null;
        }
    }
}
