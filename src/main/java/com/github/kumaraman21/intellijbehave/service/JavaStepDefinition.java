package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.SmartPointerManager;
import com.intellij.psi.SmartPsiElementPointer;
import com.intellij.psi.util.PsiTreeUtil;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;

public class JavaStepDefinition {
    private final SmartPsiElementPointer<PsiAnnotation> myElementPointer;
    private final StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();

    public JavaStepDefinition(PsiAnnotation annotation) {
        myElementPointer = SmartPointerManager.getInstance(annotation.getProject()).createSmartPsiElementPointer(annotation);
    }

    public boolean matches(String stepText) {
        Set<StepMatcher> stepMatchers = getStepMatchers();
        for (StepMatcher stepMatcher : stepMatchers) {
            if (stepMatcher.matches(stepText)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public String getAnnotationTextFor(String stepText) {
        Set<String> annotationTexts = getAnnotationTexts();

        if (annotationTexts.size() == 1) {//small optimization: it doesn't create matchers if no step variants found
            return annotationTexts.iterator().next();
        }

        Set<StepMatcher> stepMatchers = getStepMatchers(annotationTexts);

        for (StepMatcher stepMatcher : stepMatchers) {
            if (stepMatcher.matches(stepText)) {
                return stepMatcher.pattern().annotated();
            }
        }

        return null;
    }

    @Nullable
    private PsiAnnotation getAnnotation() {
        return myElementPointer.getElement();
    }

    @Nullable
    public PsiMethod getAnnotatedMethod() {
        return PsiTreeUtil.getParentOfType(getAnnotation(), PsiMethod.class);
    }

    private Set<StepMatcher> getStepMatchers() {
        return getStepMatchers(getAnnotationTexts());
    }

    private Set<StepMatcher> getStepMatchers(Set<String> annotationTextVariants) {
        final StepType annotationType = getAnnotationType();

        return annotationTextVariants.stream()
                .map(annotationText -> stepPatternParser.parseStep(annotationType, annotationText))
                .map(OptimizedStepMatcher::new)
                .collect(Collectors.toSet());
    }

    @NotNull
    private Set<String> getAnnotationTexts() {
        PsiAnnotation element = getAnnotation();

        return element == null ? Collections.emptySet() : JBehaveUtil.getAnnotationTexts(element);

    }

    @Nullable
    public StepType getAnnotationType() {
        final PsiAnnotation element = getAnnotation();
        if (element == null) {
            return null;
        }

        String qualifiedName = ApplicationManager.getApplication().runReadAction(new Computable<String>() {
            public String compute() {
                return element.getQualifiedName();
            }
        });

        return ANNOTATION_TO_STEP_TYPE_MAPPING.get(qualifiedName);
    }

    @NotNull
    public Integer getAnnotationPriority() {
        PsiAnnotation element = getAnnotation();

        if (element == null) {
            return -1;
        }

        return JBehaveUtil.getAnnotationPriority(element);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        else if (o != null && getClass() == o.getClass()) {
            JavaStepDefinition that = (JavaStepDefinition) o;
            return myElementPointer.equals(that.myElementPointer);
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return myElementPointer.hashCode();
    }

    public boolean supportsStep(@NotNull JBehaveStep step) {
        StepType stepType = step.getStepType();
        StepType annotationType = getAnnotationType();

        return Objects.equals(stepType, annotationType);
    }
}
