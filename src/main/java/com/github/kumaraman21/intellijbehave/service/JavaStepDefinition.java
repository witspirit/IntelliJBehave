package com.github.kumaraman21.intellijbehave.service;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static com.intellij.openapi.application.ReadAction.compute;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import com.intellij.psi.util.PsiTreeUtil;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Java step definition as a step annotation.
 */
public final class JavaStepDefinition {
    private static final StepPatternParser STEP_PATTERN_PARSER = new RegexPrefixCapturingPatternParser();
    private final SmartPsiElementPointer<PsiAnnotation> annotationPointer;

    public JavaStepDefinition(PsiAnnotation annotation) {
        annotationPointer = compute(() -> SmartPointerManager.getInstance(annotation.getProject()).createSmartPsiElementPointer(annotation));
    }

    /**
     * Returns if the step type of the provided Story step is the same as the step
     * type of the current annotation, and if any of the step annotation patterns match
     * the provided Story step text.
     *
     * @param step a step from a Story file
     * @param stepText the text of a step in a Story file
     */
    public boolean supportsStepAndMatches(@NotNull JBehaveStep step, String stepText) {
        StepType annotationType = getAnnotationType();
        if (!Objects.equals(step.getStepType(), annotationType)) return false;

        for (String annotationText : getAnnotationTexts()) {
            if (new OptimizedStepMatcher(STEP_PATTERN_PARSER.parseStep(annotationType, annotationText)).matches(stepText))
                return true;
        }

        return false;
    }

    /**
     * Returns the step pattern from the current annotation if it matches the Story {@code stepText}.
     * <p>
     * If there is only one step text without any text variant, it returns that pattern.
     * Otherwise, it returns the first annotation text that matches {@code stepText}.
     *
     * @param stepText the text of a step in a Story file
     */
    @Nullable("When there is no annotation text.")
    public String getAnnotationTextFor(String stepText) {
        Set<String> annotationTexts = getAnnotationTexts();

        if (annotationTexts.size() == 1) {//small optimization: it doesn't create matchers if no step variants found
            return annotationTexts.iterator().next();
        }

        final StepType annotationType = getAnnotationType();
        for (String annotationText : annotationTexts) {
            OptimizedStepMatcher stepMatcher = new OptimizedStepMatcher(STEP_PATTERN_PARSER.parseStep(annotationType, annotationText));
            if (stepMatcher.matches(stepText)) {
                return stepMatcher.pattern().annotated();
            }
        }

        return null;
    }

    @Nullable
    private PsiAnnotation getAnnotation() {
        return annotationPointer.getElement();
    }

    /**
     * Returns the method that the current annotation is placed on.
     */
    @Nullable
    public PsiMethod getAnnotatedMethod() {
        return compute(() -> PsiTreeUtil.getParentOfType(getAnnotation(), PsiMethod.class));
    }

    @NotNull
    private Set<String> getAnnotationTexts() {
        PsiAnnotation annotation = getAnnotation();

        return annotation == null ? Collections.emptySet() : JBehaveUtil.getAnnotationTexts(annotation, null);
    }

    /**
     * Returns the {@link StepType} mapped to the current annotation if it is one of {@code @Given},
     * {@code @When} or {@code @Then}.
     */
    @Nullable("When there is no step annotation to work with, or the annotation has no StepType mapped.")
    public StepType getAnnotationType() {
        final PsiAnnotation annotation = getAnnotation();
        return annotation == null
               ? null
               : ANNOTATION_TO_STEP_TYPE_MAPPING.get(compute(annotation::getQualifiedName));
    }

    /**
     * Returns the value of the {@code priority} attribute of the current annotation.
     */
    @NotNull
    public Integer getAnnotationPriority() {
        PsiAnnotation annotation = getAnnotation();

        return annotation != null ? JBehaveUtil.getAnnotationPriority(annotation) : -1;
    }

    //Equals and hashcode

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && getClass() == o.getClass()) {
            JavaStepDefinition that = (JavaStepDefinition) o;
            return annotationPointer.equals(that.annotationPointer);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return annotationPointer.hashCode();
    }
}
