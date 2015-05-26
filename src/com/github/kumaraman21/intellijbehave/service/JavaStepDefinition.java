package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.github.kumaraman21.intellijbehave.utility.StepTypeMappings.ANNOTATION_TO_STEP_TYPE_MAPPING;
import static com.google.common.collect.FluentIterable.from;

public class JavaStepDefinition implements Comparable<JavaStepDefinition> {
    private final SmartPsiElementPointer<PsiAnnotation> myElementPointer;
    private final StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();

    public JavaStepDefinition(PsiAnnotation annotation) {
        myElementPointer =
                SmartPointerManager.getInstance(annotation.getProject()).createSmartPsiElementPointer(annotation);
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
            return Iterables.getFirst(annotationTexts, null);
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
    public PsiAnnotation getAnnotation() {
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

        return from(annotationTextVariants).transform(toStepMatchers(annotationType)).toSet();
    }

    private Function<String, StepMatcher> toStepMatchers(final StepType annotationType) {
        return new Function<String, StepMatcher>() {
            @Override
            public StepMatcher apply(@Nullable String annotationText) {
                return stepPatternParser.parseStep(annotationType, annotationText);
            }
        };
    }

    @NotNull
    public Set<String> getAnnotationTexts() {
        PsiAnnotation element = getAnnotation();

        if (element == null) {
            return ImmutableSet.of();
        }

        return JBehaveUtil.getAnnotationTexts(element);
    }

    @Nullable
    public StepType getAnnotationType() {
        final PsiAnnotation element = getAnnotation();
        if (element == null) {
            return null;
        }

        String qualifiedName = ApplicationManager.getApplication().runReadAction(new StringComputable(element));

        return ANNOTATION_TO_STEP_TYPE_MAPPING.get(qualifiedName);
    }

    private static String toType(StepType type) {
        switch (type) {
            case GIVEN:
                return "Given";
            case WHEN:
                return "When";
            case THEN:
                return "Then";
        }
        return "";
    }

    public String getAnnotationTypeAsString() {
        return toType(getAnnotationType());
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
        } else if (o != null && getClass() == o.getClass()) {
            JavaStepDefinition that = (JavaStepDefinition) o;
            return myElementPointer.equals(that.myElementPointer);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return myElementPointer.hashCode();
    }

    public boolean supportsStep(@NotNull ScenarioStep step) {
        StepType stepType = step.getStepType();
        if (stepType == StepType.AND) return true;
        StepType annotationType = getAnnotationType();

        return stepType == annotationType;
    }

    @Override
    public String toString() {
        PsiAnnotation element = myElementPointer.getElement();
        return element != null ? element.getText() : "";
    }

    public Set<ParametrizedString> toPString() {
        Set<ParametrizedString> result = new HashSet<ParametrizedString>();
        for (String text : getAnnotationTexts()) {
            result.add(new ParametrizedString(text));
        }
        return result;
    }

    public Collection<String> toStringWithoutIdentifiers() {
        final List<String> stringWithoutIdentifiers = new ArrayList<String>();
        for (ParametrizedString parametrizedString : toPString()) {
            stringWithoutIdentifiers.add(String.format("%s %s", getAnnotationTypeAsString(),
                                                       parametrizedString.toStringWithoutIdentifiers()));
        }
        return stringWithoutIdentifiers;
    }

    @Override
    public int compareTo(@NotNull JavaStepDefinition other) {
        StepType myType = getAnnotationType();
        StepType otherType = other.getAnnotationType();
        if (myType != otherType && myType != StepType.IGNORABLE && otherType != StepType.IGNORABLE) {
            if (myType == StepType.GIVEN) return -1;
            if (otherType == StepType.GIVEN) return 1;
            if (myType == StepType.WHEN) return -1;
            if (otherType == StepType.WHEN) return 1;
        }
        Set<ParametrizedString> myPstrings = toPString();
        Set<ParametrizedString> otherPstrings = other.toPString();
        int cmp = myPstrings.size() - otherPstrings.size();
        if (cmp == 0) {
            Iterator<ParametrizedString> otherIt = otherPstrings.iterator();
            for (ParametrizedString myPstring : myPstrings) {
                ParametrizedString otherPstring = otherIt.next();
                cmp = myPstring.compareTo(otherPstring);
                if (cmp != 0) break;
            }

        }
        return cmp;
    }

    public Map<String, PsiType> mapNameToType() {
        Map<String, PsiType> mapNameToType = new HashMap<String, PsiType>();
        PsiMethod method = getAnnotatedMethod();
        if (method != null) {
            PsiParameterList parameterList = method.getParameterList();
            PsiParameter[] parameters = parameterList.getParameters();
            for (PsiParameter parameter : parameters) {
                PsiTypeElement typeElement = parameter.getTypeElement();
                if (typeElement != null) {
                    PsiType type = typeElement.getType();
                    mapNameToType.put(parameter.getName(), type);
                }
            }
        }
        return mapNameToType;
    }

    private static class StringComputable implements Computable<String> {
        private final PsiAnnotation element;

        public StringComputable(PsiAnnotation element) {
            this.element = element;
        }

        public String compute() {
            return element.getQualifiedName();
        }
    }
}
