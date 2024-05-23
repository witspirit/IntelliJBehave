package com.github.kumaraman21.intellijbehave.service;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import com.github.kumaraman21.intellijbehave.JBehaveStepDefClassesModificationTracker;
import com.github.kumaraman21.intellijbehave.kotlin.KotlinConfigKt;
import com.github.kumaraman21.intellijbehave.kotlin.support.services.KotlinAnnotationsLoader;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootModificationTracker;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.java.stubs.index.JavaFullClassNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.QualifiedName;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project service that provides Java step definitions for JBehave Story steps.
 */
@Service(Service.Level.PROJECT)
public final class JBehaveStepsIndex implements Disposable {

    //Argument is necessary for project-level service creation
    public JBehaveStepsIndex(Project project) {
    }

    public static JBehaveStepsIndex getInstance(Project project) {
        return project.getService(JBehaveStepsIndex.class);
    }

    @NotNull
    public Collection<JavaStepDefinition> findStepDefinitions(@NotNull JBehaveStep step) {
        Module module = ModuleUtilCore.findModuleForPsiElement(step);

        if (module == null) {
            return emptyList();
        }

        Map<Class, JavaStepDefinition> definitionsByClass = new HashMap<>(2);
        String stepText = step.getStepText();

        for (JavaStepDefinition stepDefinition : loadStepsFor(module)) {
            if (stepDefinition.matches(stepText) && stepDefinition.supportsStep(step)) {
                Integer currentHighestPriority = getPriorityByDefinition(definitionsByClass.get(stepDefinition.getClass()));
                Integer newPriority = getPriorityByDefinition(stepDefinition);

                if (newPriority > currentHighestPriority) {
                    definitionsByClass.put(stepDefinition.getClass(), stepDefinition);
                }
            }
        }

        return definitionsByClass.values();
    }

    @NotNull
    private List<JavaStepDefinition> loadStepsFor(@NotNull Module module) {
        GlobalSearchScope dependenciesScope = module.getModuleWithDependenciesAndLibrariesScope(true);

        PsiClass givenAnnotationClass = findStepAnnotation(Given.class.getName(), module, dependenciesScope);
        if (givenAnnotationClass == null) return emptyList();
        PsiClass whenAnnotationClass = findStepAnnotation(When.class.getName(), module, dependenciesScope);
        if (whenAnnotationClass == null) return emptyList();
        PsiClass thenAnnotationClass = findStepAnnotation(Then.class.getName(), module, dependenciesScope);
        if (thenAnnotationClass == null) return emptyList();

        List<JavaStepDefinition> result = new ArrayList<>();

        for (PsiClass stepAnnotation : asList(givenAnnotationClass, whenAnnotationClass, thenAnnotationClass)) {
            for (PsiAnnotation stepDefAnnotation : getAllStepAnnotations(stepAnnotation, dependenciesScope)) {
                result.add(new JavaStepDefinition(stepDefAnnotation));
            }
        }

        return result;
    }

    @NotNull
    private static Integer getPriorityByDefinition(@Nullable JavaStepDefinition definition) {
        return definition != null ? definition.getAnnotationPriority() : -1;
    }

    /**
     * Collects all {@link PsiAnnotation}s in the given {@code scope} that reference the {@code annClass}.
     *
     * @param annClass the PsiClass representing the {@code @Given}, {@code @When} or {@code @Then} step annotations
     *                 Since the annotation classes doesn't change much, unless e.g. updating the library version,
     *                 the same PsiClass instance will be available for the same annotations throughout the IDE session,
     *                 thus it should be safe to use it as the cache location
     */
    @NotNull
    private static Collection<PsiAnnotation> getAllStepAnnotations(@NotNull final PsiClass annClass, @NotNull final GlobalSearchScope scope) {
        return CachedValuesManager.getCachedValue(annClass, (CachedValueProvider<? extends Collection<PsiAnnotation>>) () -> {
            Collection<PsiAnnotation> annotations = ReadAction.compute(() -> {
                Project project = annClass.getProject();
                Collection<PsiAnnotation> psiAnnotations = new ArrayList<>();
                if (KotlinConfigKt.getPluginIsEnabled()) {
                    String annotationFqn = annClass.getQualifiedName();
                    if (annotationFqn != null) {
                        psiAnnotations.addAll(KotlinAnnotationsLoader.getAnnotations(QualifiedName.fromDottedString(annotationFqn), project, scope));
                    }
                }
                psiAnnotations.addAll(JavaAnnotationIndex.getInstance().get(annClass.getName(), project, scope));
                return psiAnnotations;
            });

            return new CachedValueProvider.Result<>(annotations,
                JBehaveStepDefClassesModificationTracker.getInstance(annClass.getProject()),
                ProjectRootModificationTracker.getInstance(annClass.getProject()));
        });
    }

    @Nullable
    private PsiClass findStepAnnotation(String stepClass, Module module, GlobalSearchScope dependenciesScope) {
        var stepDefAnnotationCandidates = JavaFullClassNameIndex.getInstance().get(stepClass, module.getProject(), dependenciesScope);
        for (PsiClass stepDefAnnotations : stepDefAnnotationCandidates) {
            if (stepClass.equals(stepDefAnnotations.getQualifiedName())) {
                return stepDefAnnotations;
            }
        }
        return null;
    }

    @Override
    public void dispose() {
        //No-op. Used for ProjectStartupActivity as parent Disposable
    }
}
