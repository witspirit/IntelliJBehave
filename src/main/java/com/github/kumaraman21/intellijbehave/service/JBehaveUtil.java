package com.github.kumaraman21.intellijbehave.service;

import static com.intellij.openapi.util.text.StringUtil.isEmptyOrSpaces;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.PatternVariantBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiArrayInitializerMemberValue;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.TextOccurenceProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;

public class JBehaveUtil {

    public static final Set<String> JBEHAVE_ANNOTATIONS_SET = Set.of(Given.class.getName(), When.class.getName(),
            Then.class.getName());

    public static boolean isJBehaveStepAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = getAnnotationName(annotation);

        return annotationName != null && JBEHAVE_ANNOTATIONS_SET.contains(annotationName);
    }

    public static boolean isAnnotationOfClass(@NotNull PsiAnnotation annotation,
            @NotNull Class<? extends Annotation> annotationClass) {
        String annotationName = getAnnotationName(annotation);

        return annotationName != null && Objects.equals(annotationName, annotationClass.getName());
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

        return Stream.of(annotations)
                .filter(JBehaveUtil::isJBehaveStepAnnotation)
                .collect(Collectors.toList());
    }

    private static Optional<PsiAnnotation> findAnnotation(PsiAnnotation @NotNull [] annotations,
            @NotNull Class<? extends Annotation> annotationClass) {
        return Stream.of(annotations)
                .filter(annotation -> isAnnotationOfClass(annotation, annotationClass))
                .findFirst();
    }

    public static boolean isStepDefinition(@NotNull PsiMethod method) {
        return getJBehaveStepAnnotations(method).stream()
                .map(stepAnnotation -> stepAnnotation.findAttributeValue("value"))
                .anyMatch(Objects::nonNull);
    }

    @NotNull
    public static Set<String> getAnnotationTexts(@NotNull PsiAnnotation stepAnnotation) {
        Set<String> annotationTexts = new HashSet<>();
        getAnnotationText(stepAnnotation).ifPresent(annotationTexts::add);

        PsiMethod method = PsiTreeUtil.getParentOfType(stepAnnotation, PsiMethod.class);
        if (method != null) {

            PsiAnnotation[] annotations = method.getModifierList().getAnnotations();

            findAnnotation(annotations, Alias.class)
                    .flatMap(JBehaveUtil::getAnnotationText)
                    .ifPresent(annotationTexts::add);

            findAnnotation(annotations, Aliases.class)
                    .map(JBehaveUtil::getAliasesAnnotationTexts)
                    .ifPresent(annotationTexts::addAll);
        }

        return annotationTexts.stream()
                .map(PatternVariantBuilder::new)
                .map(PatternVariantBuilder::allVariants)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private static Optional<String> getAnnotationText(@NotNull PsiAnnotation annotation) {
        return Optional.ofNullable(AnnotationUtil.getStringAttributeValue(annotation, "value"));
    }

    @NotNull
    private static Set<String> getAliasesAnnotationTexts(@NotNull PsiAnnotation aliasAnnotation) {
        PsiAnnotationMemberValue values = aliasAnnotation.findAttributeValue("values");

        if (!(values instanceof PsiArrayInitializerMemberValue)) {
            return Collections.emptySet();
        }

        final PsiArrayInitializerMemberValue attrValue = (PsiArrayInitializerMemberValue) values;

        final PsiConstantEvaluationHelper constantEvaluationHelper = JavaPsiFacade.getInstance(aliasAnnotation.getProject()).getConstantEvaluationHelper();

        return Stream.of(attrValue.getInitializers())
                .map(constantEvaluationHelper::computeConstantExpression)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .collect(Collectors.toSet());
    }

    @NotNull
    public static List<String> getAnnotationTexts(@NotNull PsiMethod method) {
        return getJBehaveStepAnnotations(method)
                .stream()
                .map(JBehaveUtil::getAnnotationTexts)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
    }

    @NotNull
    public static Integer getAnnotationPriority(@NotNull PsiAnnotation stepAnnotation) {
        PsiAnnotationMemberValue attrValue = stepAnnotation.findAttributeValue("priority");
        // TODO test change doesn't break other languages; this change works as a quick fix for Kotlin
        //Object constValue = JavaPsiFacade.getInstance(stepAnnotation.getProject()).getConstantEvaluationHelper().computeConstantExpression(attrValue);
        Object constValue = JavaPsiFacade.getInstance(stepAnnotation.getProject()).getConstantEvaluationHelper().computeConstantExpression(attrValue.getOriginalElement());
        Integer priority = constValue instanceof Integer ? (Integer) constValue : null;

        if (priority != null) {
            return priority;
        }

        return -1;
    }

    public static boolean findJBehaveReferencesToElement(@NotNull PsiElement stepDefinitionElement, @NotNull String stepText, @NotNull Processor<? super PsiReference> consumer, @NotNull final SearchScope effectiveSearchScope) {
        String word = getTheBiggestWordToSearchByIndex(stepText);

        if (isEmptyOrSpaces(word)) {
            return true;
        }

        SearchScope searchScope = restrictScopeToJBehaveFiles(new Computable<SearchScope>() {
            public SearchScope compute() {
                return effectiveSearchScope;
            }
        });

        PsiSearchHelper instance = PsiSearchHelper.getInstance(stepDefinitionElement.getProject());
        return instance.processElementsWithWord(new MyReferenceCheckingProcessor(stepDefinitionElement, consumer), searchScope, word, (short) 5, true);
    }

    public static SearchScope restrictScopeToJBehaveFiles(final Computable<SearchScope> originalScopeComputation) {
        return (SearchScope) ApplicationManager.getApplication().runReadAction(new Computable() {
            public SearchScope compute() {
                SearchScope originalScope = originalScopeComputation.compute();
                if (originalScope instanceof GlobalSearchScope) {
                    return GlobalSearchScope.getScopeRestrictedByFileTypes((GlobalSearchScope) originalScope, StoryFileType.STORY_FILE_TYPE);
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
        private final Processor<? super PsiReference> myConsumer;

        private MyReferenceCheckingProcessor(@NotNull PsiElement elementToFind, @NotNull Processor<? super PsiReference> consumer) {
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
