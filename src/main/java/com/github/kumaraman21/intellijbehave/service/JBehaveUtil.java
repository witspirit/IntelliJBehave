package com.github.kumaraman21.intellijbehave.service;

import static com.intellij.openapi.util.text.StringUtil.isEmptyOrSpaces;

import com.github.kumaraman21.intellijbehave.jbehave.core.steps.PatternVariantBuilder;
import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.TextOccurenceProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.Processor;
import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Aliases;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JBehaveUtil {

    private static final Set<String> JBEHAVE_ANNOTATIONS_SET = Set.of(Given.class.getName(), When.class.getName(),
        Then.class.getName());

    /**
     * Returns if the provided annotation is one of {@link #JBEHAVE_ANNOTATIONS_SET}.
     */
    public static boolean isJBehaveStepAnnotation(@NotNull PsiAnnotation annotation) {
        String annotationName = annotation.getQualifiedName();

        return annotationName != null && JBEHAVE_ANNOTATIONS_SET.contains(annotationName);
    }

    /**
     * Returns if the provided annotation is of type {@code annotationClass}.
     * <p>
     * For example, if the {@code @org.jbehave.core.annotations.Given} annotation is of type {@link Given}.
     */
    public static boolean isAnnotationOfClass(@NotNull PsiAnnotation annotation,
                                              @NotNull Class<? extends Annotation> annotationClass) {
        String annotationName = annotation.getQualifiedName();

        return annotationName != null && annotationName.equals(annotationClass.getName());
    }

    /**
     * Returns all {@code @Given}, {@code @When} and {@code @Then} step annotations on the provided {@code method}.
     */
    @NotNull
    private static List<PsiAnnotation> getJBehaveStepAnnotations(@NotNull PsiMethod method) {
        return Stream.of(method.getModifierList().getAnnotations())
            .filter(JBehaveUtil::isJBehaveStepAnnotation)
            .collect(Collectors.toList());
    }

    /**
     * Returns if the provided method is step definition, meaning has at least one annotation that
     * <ul>
     *     <li>is annotated with one of {@link #JBEHAVE_ANNOTATIONS_SET},</li>
     *     <li>and its {@code value} attribute is not null.</li>
     * </ul>
     */
    public static boolean isStepDefinition(@NotNull PsiMethod method) {
        return getJBehaveStepAnnotations(method).stream()
            .map(stepAnnotation -> stepAnnotation.findAttributeValue("value"))
            .anyMatch(Objects::nonNull);
    }

    /**
     * Collects all possible step annotation pattern variants for {@code stepAnnotation},
     * as well as the {@link Alias} and {@link Aliases} annotations on the parent
     * step definition method.
     *
     * @param stepAnnotation annotation for one of {@link #JBEHAVE_ANNOTATIONS_SET}
     * @return the collection of step patterns
     */
    @NotNull
    public static Set<String> getAnnotationTexts(@NotNull PsiAnnotation stepAnnotation) {
        var annotationTexts = new HashSet<String>(4);
        getAnnotationText(stepAnnotation).ifPresent(annotationTexts::add);

        PsiMethod method = PsiTreeUtil.getParentOfType(stepAnnotation, PsiMethod.class);
        if (method != null) {
            for (PsiAnnotation annotation : method.getModifierList().getAnnotations()) {
                if (isAnnotationOfClass(annotation, Alias.class)) {
                    getAnnotationText(annotation).ifPresent(annotationTexts::add);
                } else if (isAnnotationOfClass(annotation, Aliases.class)) {
                    annotationTexts.addAll(getAliasesAnnotationTexts(annotation));
                }
            }
        }

        return annotationTexts.stream()
            .map(PatternVariantBuilder::new)
            .flatMap(builder -> builder.allVariants().stream())
            .collect(Collectors.toSet());
    }

    /**
     * Returns the value of {@code annotation}'s {@code value} attribute, the step pattern.
     *
     * @param annotation a JBehave annotation: Given, When, Then, Alias or Aliases
     */
    private static Optional<String> getAnnotationText(@NotNull PsiAnnotation annotation) {
        return Optional.ofNullable(AnnotationUtil.getStringAttributeValue(annotation, "value"));
    }

    @NotNull
    private static Set<String> getAliasesAnnotationTexts(@NotNull PsiAnnotation aliasAnnotation) {
        return AnnotationUtil.arrayAttributeValues(aliasAnnotation.findAttributeValue("values"))
            .stream()
            .map(AnnotationUtil::getStringAttributeValue)
            .collect(Collectors.toSet());
    }

    /**
     * Collects all possible step annotation pattern variants for the annotations on {@code method}.
     *
     * @param method the step definition method
     * @return the list of step patterns
     */
    @NotNull
    public static List<String> getAnnotationTexts(@NotNull PsiMethod method) {
        return getJBehaveStepAnnotations(method)
            .stream()
            .map(JBehaveUtil::getAnnotationTexts)
            .flatMap(Set::stream)
            .collect(Collectors.toList());
    }

    /**
     * Returns the value of {@code stepAnnotation}'s {@code priority} attribute, or -1 if the
     * priority is not an integer.
     */
    @NotNull
    public static Integer getAnnotationPriority(@NotNull PsiAnnotation stepAnnotation) {
        PsiAnnotationMemberValue attrValue = stepAnnotation.findAttributeValue("priority");
        // TODO test change doesn't break other languages; this change works as a quick fix for Kotlin
        //Object constValue = JavaPsiFacade.getInstance(stepAnnotation.getProject()).getConstantEvaluationHelper().computeConstantExpression(attrValue);
        Object constValue = JavaPsiFacade.getInstance(stepAnnotation.getProject()).getConstantEvaluationHelper().computeConstantExpression(attrValue.getOriginalElement());
        Integer priority = constValue instanceof Integer ? (Integer) constValue : null;

        return priority != null ? priority : -1;
    }

    /**
     * Performs word based reference search for {@code stepDefinitionElement}.
     *
     * @param stepDefinitionElement a step definition method
     * @param stepText              the step pattern value of the step annotation
     * @param consumer
     * @param effectiveSearchScope  the search scope to find references in
     * @return true if the corresponding query execution in {@link JBehaveJavaStepDefinitionSearch} should continue,
     * false if it should stop
     */
    public static boolean findJBehaveReferencesToElement(@NotNull PsiElement stepDefinitionElement, @NotNull String stepText, @NotNull Processor<? super PsiReference> consumer, @NotNull final SearchScope effectiveSearchScope) {
        String word = getTheBiggestWordToSearchByIndex(stepText);

        if (isEmptyOrSpaces(word)) {
            return true;
        }

        SearchScope searchScope = restrictScopeToJBehaveFiles(effectiveSearchScope);

        return PsiSearchHelper.getInstance(stepDefinitionElement.getProject())
            .processElementsWithWord(new MyReferenceCheckingProcessor(stepDefinitionElement, consumer), searchScope, word, (short) 5, true);
    }

    /**
     * Returns a search scope that is based on the {@code originalScopeComputation} but that is restricted to JBehave Story file types.
     */
    private static SearchScope restrictScopeToJBehaveFiles(final SearchScope originalScope) {
        return ReadAction.compute(() ->
            originalScope instanceof GlobalSearchScope globalSearchScope
            ? GlobalSearchScope.getScopeRestrictedByFileTypes(globalSearchScope, StoryFileType.STORY_FILE_TYPE)
            : originalScope);
    }

    /**
     * Returns the longest non-placeholder (e.g. {@code $price}) word from {@code stepText}.
     *
     * @param stepText the step pattern value from a step annotation
     */
    public static String getTheBiggestWordToSearchByIndex(@NotNull String stepText) {
        //This helps avoid the creation of the variables below in case of empty and blank step text
        if (stepText.isBlank()) return "";

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

    private static final class MyReferenceCheckingProcessor implements TextOccurenceProcessor {
        @NotNull
        private final PsiElement myElementToFind;
        @NotNull
        private final Processor<? super PsiReference> myConsumer;

        private MyReferenceCheckingProcessor(@NotNull PsiElement elementToFind, @NotNull Processor<? super PsiReference> consumer) {
            myElementToFind = elementToFind;
            myConsumer = consumer;
        }

        @Override
        public boolean execute(@NotNull PsiElement element, int offsetInElement) {
            boolean result = executeInternal(element);
            //This avoids calculating the parent element when result is false
            if (result) {
                PsiElement parent = element.getParent();
                return parent == null || executeInternal(parent);
            }
            return false;
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

    private JBehaveUtil() {
    }
}
