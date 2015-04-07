package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.TextOccurenceProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.PatternVariantBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static com.intellij.openapi.util.text.StringUtil.isEmptyOrSpaces;
import static java.util.Arrays.asList;

public class JBehaveUtil {
    public static final Predicate<PsiAnnotation> JB_STEPS_ANNOTATIONS = new Predicate<PsiAnnotation>() {
        @Override
        public boolean apply(@Nullable PsiAnnotation annotation) {
            return annotation != null && isJBehaveStepAnnotation(annotation);
        }
    };
    public static final Predicate<PsiAnnotation> JB_ALIAS_ANNOTATION = new Predicate<PsiAnnotation>() {
        @Override
        public boolean apply(@Nullable PsiAnnotation annotation) {
            return annotation != null && isJBehaveAliasAnnotation(annotation);
        }
    };
    public static final Predicate<PsiAnnotation> JB_ALIASES_ANNOTATION = new Predicate<PsiAnnotation>() {
        @Override
        public boolean apply(@Nullable PsiAnnotation annotation) {
            return annotation != null && isJBehaveAliasesAnnotation(annotation);
        }
    };
    public static final Function<PsiAnnotation, Set<String>> TO_ANNOTATION_TEXTS = new Function<PsiAnnotation, Set<String>>() {
        @Override
        public Set<String> apply(PsiAnnotation stepAnnotation) {
            return getAnnotationTexts(stepAnnotation);
        }
    };
    public static final Function<String, Set<String>> TO_A_SET_OF_PATTERNS = new Function<String, Set<String>>() {
        @Override
        public Set<String> apply(@Nullable String value) {
            return new PatternVariantBuilder(value).allVariants();
        }
    };
    public static final ImmutableSet<String> JB_ANNOTATIONS_SET = ImmutableSet.of(Given.class.getName(),
            When.class.getName(), Then.class.getName());

    public static boolean isJBehaveStepAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = getAnnotationName(annotation);

        return annotationName != null && JB_ANNOTATIONS_SET.contains(annotationName);
    }

    public static boolean isJBehaveAliasAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = getAnnotationName(annotation);

        return annotationName != null && Objects.equal(annotationName, Alias.class.getName());
    }

    public static boolean isJBehaveAliasesAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = getAnnotationName(annotation);

        return annotationName != null && Objects.equal(annotationName, Aliases.class.getName());
    }

    @Nullable
    private static String getAnnotationName(@NotNull final PsiAnnotation annotation) {
        return ApplicationManager.getApplication().runReadAction(new Computable<String>() {
            @Override
            public String compute() {
                return annotation.getQualifiedName();
            }
        });
    }

    @NotNull
    private static List<PsiAnnotation> getJBehaveStepAnnotations(@NotNull PsiMethod method) {
        PsiAnnotation[] annotations = method.getModifierList().getAnnotations();

        return from(asList(annotations)).filter(JB_STEPS_ANNOTATIONS).toList();
    }

    @Nullable
    private static PsiAnnotation getJBehaveAliasAnnotation(@NotNull PsiMethod method) {
        PsiAnnotation[] annotations = method.getModifierList().getAnnotations();

        return from(asList(annotations)).filter(JB_ALIAS_ANNOTATION).first().orNull();
    }

    @Nullable
    private static PsiAnnotation getJBehaveAliasesAnnotation(@NotNull PsiMethod method) {
        PsiAnnotation[] annotations = method.getModifierList().getAnnotations();

        return from(asList(annotations)).filter(JB_ALIASES_ANNOTATION).first().orNull();
    }

    public static boolean isStepDefinition(@NotNull PsiMethod method) {
        List<PsiAnnotation> stepAnnotations = getJBehaveStepAnnotations(method);

        for (PsiAnnotation stepAnnotation : stepAnnotations) {
            if (stepAnnotation.findAttributeValue("value") != null) {
                return true;
            }
        }

        return false;
    }

    @NotNull
    public static Set<String> getAnnotationTexts(@NotNull PsiAnnotation stepAnnotation) {
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.addAll(getStepAnnotationTexts(stepAnnotation));

        PsiMethod method = PsiTreeUtil.getParentOfType(stepAnnotation, PsiMethod.class);
        if (method != null) {
            PsiAnnotation aliasAnnotation = getJBehaveAliasAnnotation(method);
            if (aliasAnnotation != null) {
                builder.addAll(getAliasAnnotationTexts(aliasAnnotation));
            }

            PsiAnnotation aliasesAnnotation = getJBehaveAliasesAnnotation(method);
            if (aliasesAnnotation != null) {
                builder.addAll(getAliasesAnnotationTexts(aliasesAnnotation));
            }
        }

        return builder.build();
    }

    @NotNull
    private static Set<String> getStepAnnotationTexts(@NotNull PsiAnnotation stepAnnotation) {
        final String annotationText = AnnotationUtil.getStringAttributeValue(stepAnnotation, "value");

        if (annotationText == null) {
            return ImmutableSet.of();
        }

        return new PatternVariantBuilder(annotationText).allVariants();
    }

    @NotNull
    private static Set<String> getAliasAnnotationTexts(@NotNull PsiAnnotation aliasAnnotation) {
        final String annotationText = AnnotationUtil.getStringAttributeValue(aliasAnnotation, "value");

        if (annotationText == null) {
            return ImmutableSet.of();
        }

        return new PatternVariantBuilder(annotationText).allVariants();
    }

    @NotNull
    private static Set<String> getAliasesAnnotationTexts(@NotNull PsiAnnotation aliasAnnotation) {
        final PsiArrayInitializerMemberValue attrValue = (PsiArrayInitializerMemberValue) aliasAnnotation.findAttributeValue(
                "values");

        if (attrValue == null) {
            return ImmutableSet.of();
        }

        final PsiConstantEvaluationHelper constantEvaluationHelper = JavaPsiFacade.getInstance(
                aliasAnnotation.getProject()).getConstantEvaluationHelper();

        return from(asList(attrValue.getInitializers())).transform(new Function<PsiAnnotationMemberValue, String>() {
            @Override
            public String apply(@Nullable PsiAnnotationMemberValue psiAnnotationMemberValue) {
                Object constValue = constantEvaluationHelper.computeConstantExpression(psiAnnotationMemberValue);
                return constValue instanceof String ? (String) constValue : null;
            }
        }).transformAndConcat(TO_A_SET_OF_PATTERNS).toSet();
    }

    @NotNull
    public static List<String> getAnnotationTexts(@NotNull PsiMethod method) {
        List<PsiAnnotation> stepAnnotations = getJBehaveStepAnnotations(method);

        return from(stepAnnotations).transformAndConcat(TO_ANNOTATION_TEXTS).toList();
    }

    @NotNull
    public static Integer getAnnotationPriority(@NotNull PsiAnnotation stepAnnotation) {
        PsiAnnotationMemberValue attrValue = stepAnnotation.findAttributeValue("priority");
        Object constValue = JavaPsiFacade.getInstance(
                stepAnnotation.getProject()).getConstantEvaluationHelper().computeConstantExpression(attrValue);
        Integer priority = constValue instanceof Integer ? (Integer) constValue : null;

        if (priority != null) {
            return priority;
        }

        return -1;
    }

    public static boolean findJBehaveReferencesToElement(@NotNull PsiElement stepDefinitionElement,
                                                         @NotNull String stepText,
                                                         @NotNull Processor<PsiReference> consumer,
                                                         @NotNull final SearchScope effectiveSearchScope) {
        String word = getTheBiggestWordToSearchByIndex(stepText);

        if (isEmptyOrSpaces(word)) {
            return true;
        }

        SearchScope searchScope = restrictScopeToJBehaveFiles(new Computable<SearchScope>() {
            public SearchScope compute() {
                return effectiveSearchScope;
            }
        });

        PsiSearchHelper instance = PsiSearchHelper.SERVICE.getInstance(stepDefinitionElement.getProject());
        return instance.processElementsWithWord(new MyReferenceCheckingProcessor(stepDefinitionElement, consumer),
                searchScope, word, (short) 5, true);
    }

    public static SearchScope restrictScopeToJBehaveFiles(final Computable<SearchScope> originalScopeComputation) {
        return (SearchScope) ApplicationManager.getApplication().runReadAction(new Computable() {
            public SearchScope compute() {
                SearchScope originalScope = originalScopeComputation.compute();
                if (originalScope instanceof GlobalSearchScope) {
                    return GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) originalScope,
                            StoryFileType.STORY_FILE_TYPE);
                } else {
                    return originalScope;
                }
            }
        });
    }

    public static String getTheBiggestWordToSearchByIndex(@NotNull String stepText) {
        String result = "";

        int par = 0;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < stepText.length(); ++i) {
            char c = stepText.charAt(i);

            if (c == '$') {
                ++par;
            }

            if (c == ' ' && par > 0) {
                --par;
            }

            if (par > 0) {
                if (par == 1) {
                    sb = new StringBuilder();
                }
            } else if (Character.isLetterOrDigit(c)) {
                sb.append(c);
                if (sb.length() > 0 && sb.toString().length() > result.length()) {
                    result = sb.toString();
                }
            } else {
                sb = new StringBuilder();
            }
        }

        if (sb.length() > 0 && sb.toString().length() > result.length()) {
            result = sb.toString();
        }

        return result;
    }

    private static class MyReferenceCheckingProcessor implements TextOccurenceProcessor {
        @NotNull
        private final PsiElement myElementToFind;
        @NotNull
        private final Processor<PsiReference> myConsumer;

        private MyReferenceCheckingProcessor(@NotNull PsiElement elementToFind,
                                             @NotNull Processor<PsiReference> consumer) {
            myElementToFind = elementToFind;
            myConsumer = consumer;
        }

        public boolean execute(@NotNull PsiElement element, int offsetInElement) {
            PsiElement parent = element.getParent();
            boolean result = executeInternal(element);
            return result && parent != null ? executeInternal(parent) : result;
        }

        private boolean executeInternal(@NotNull PsiElement referenceOwner) {
            for (PsiReference ref : referenceOwner.getReferences()) {
                if (ref != null && ref.isReferenceTo(myElementToFind) && !myConsumer.process(ref)) {
                    return false;
                }
            }

            return true;
        }
    }
}
