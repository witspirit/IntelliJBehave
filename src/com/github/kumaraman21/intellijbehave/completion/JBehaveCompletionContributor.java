package com.github.kumaraman21.intellijbehave.completion;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.resolver.ScenarioStepReference;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionAnnotation;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionIterator;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.utility.LocalizedStorySupport;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.github.kumaraman21.intellijbehave.utility.ScanUtils;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.util.Consumer;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class JBehaveCompletionContributor extends CompletionContributor {
    public JBehaveCompletionContributor() {
    }

    private static Consumer<LookupElement> newConsumer(final CompletionResultSet result) {
        return new Consumer<LookupElement>() {
            @Override
            public void consume(LookupElement element) {
                result.addElement(element);
            }
        };
    }

    private static void addAllSteps(CompletionParameters parameters, PrefixMatcher prefixMatcher,
                                    Consumer<LookupElement> consumer, LocalizedKeywords keywords) {
        ScenarioStep step = getStepPsiElement(parameters);
        if (step == null) {
            return;
        }

        StepType stepType = step.getStepType();
        String actualStepPrefix = step.getActualStepPrefix();
        //
        String textBeforeCaret = CompletionUtil.findReferenceOrAlphanumericPrefix(parameters);

        // suggest only if at least the actualStepPrefix is complete
        if (isStepTypeComplete(keywords, textBeforeCaret)) {
            StepSuggester stepAnnotationFinder =
                    new StepSuggester(prefixMatcher, stepType, actualStepPrefix, textBeforeCaret, consumer,
                                      step.hasStoryStepPostParameters(), step.getProject());
            ScanUtils.iterateInContextOf(step, stepAnnotationFinder);
        }
    }

    private static boolean isStepTypeComplete(LocalizedKeywords keywords, String input) {
        return input.startsWith(keywords.given()) || input.startsWith(keywords.when()) ||
                input.startsWith(keywords.then()) || input.startsWith(keywords.and());
    }

    //    private static void addAllKeywords(PrefixMatcher prefixMatcher, Consumer<LookupElement> consumer,
    //                                       LocalizedKeywords keywords) {
    //        addIfMatches(consumer, prefixMatcher, keywords.narrative());
    //        addIfMatches(consumer, prefixMatcher, keywords.asA());
    //        addIfMatches(consumer, prefixMatcher, keywords.inOrderTo());
    //        addIfMatches(consumer, prefixMatcher, keywords.iWantTo());
    //        //
    //        addIfMatches(consumer, prefixMatcher, keywords.givenStories());
    //        addIfMatches(consumer, prefixMatcher, keywords.ignorable());
    //        addIfMatches(consumer, prefixMatcher, keywords.scenario());
    //        addIfMatches(consumer, prefixMatcher, keywords.examplesTable());
    //        //
    //        addIfMatches(consumer, prefixMatcher, keywords.given());
    //        addIfMatches(consumer, prefixMatcher, keywords.when());
    //        addIfMatches(consumer, prefixMatcher, keywords.then());
    //        addIfMatches(consumer, prefixMatcher, keywords.and());
    //    }
    //
    //    private static void addIfMatches(Consumer<LookupElement> consumer, PrefixMatcher prefixMatchers, String input) {
    //        if (prefixMatchers.prefixMatches(input)) {
    //            consumer.consume(LookupElementBuilder.create(input));
    //        }
    //    }

    private static ScenarioStep getStepPsiElement(CompletionParameters parameters) {
        PsiElement position = parameters.getPosition();
        while (position != null) {
            if (position instanceof ScenarioStep) return (ScenarioStep) position;
            if (position instanceof ScenarioStepReference) return ((ScenarioStepReference) position).getElement();
            position = position.getParent();
        }
        return null;
    }

    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters,
                                       @NotNull final CompletionResultSet _result) {
        if (parameters.getCompletionType() == CompletionType.BASIC) {
            ScenarioStep step = getStepPsiElement(parameters);
            if (step == null) {
                return;
            }
            String prefix = CompletionUtil.findReferenceOrAlphanumericPrefix(parameters);
            CompletionResultSet result = _result.withPrefixMatcher(prefix);

            LocalizedKeywords keywords = lookupLocalizedKeywords(parameters);
            Consumer<LookupElement> consumer = newConsumer(_result);

            //addAllKeywords(result.getPrefixMatcher(), consumer, keywords);
            addAllSteps(parameters, result.getPrefixMatcher(), consumer, keywords);
        }
    }

    private LocalizedKeywords lookupLocalizedKeywords(CompletionParameters parameters) {
        String locale = "en";
        ASTNode localeNode =
                parameters.getOriginalFile().getNode().findChildByType(IJBehaveElementType.JB_TOKEN_COMMENT);
        if (localeNode != null) {
            String localeFound = LocalizedStorySupport.checkForLanguageDefinition(localeNode.getText());
            if (localeFound != null) {
                locale = localeFound;
            }
        }
        return new LocalizedStorySupport().getKeywords(locale);
    }

    private static class StepSuggester extends StepDefinitionIterator {

        private final PrefixMatcher prefixMatcher;
        private final String actualStepPrefix;
        private final String textBeforeCaret;
        private final Consumer<LookupElement> consumer;
        private final boolean hasPostParameter;

        private StepSuggester(PrefixMatcher prefixMatcher, StepType stepType, String actualStepPrefix,
                              String textBeforeCaret, Consumer<LookupElement> consumer, boolean hasPostParameter,
                              Project project) {
            super(stepType, project);
            this.prefixMatcher = prefixMatcher;
            this.actualStepPrefix = actualStepPrefix;
            this.textBeforeCaret = textBeforeCaret;
            this.consumer = consumer;
            this.hasPostParameter = hasPostParameter;
        }

        @Override
        public boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation) {
            StepType annotationStepType = stepDefinitionAnnotation.getStepType();
            if (annotationStepType != getStepType()) {
                return true;
            }
            String annotationText = stepDefinitionAnnotation.getAnnotationText();
            String adjustedAnnotationText = actualStepPrefix + " " + annotationText;

            ParametrizedString pString = new ParametrizedString(adjustedAnnotationText);
            String complete = pString.complete(textBeforeCaret, hasPostParameter);
            if (StringUtil.isNotEmpty(complete)) {
                PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                consumer.consume(LookupElementBuilder.create(matchingAnnotation, complete));
            } else if (prefixMatcher.prefixMatches(adjustedAnnotationText)) {
                PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                consumer.consume(LookupElementBuilder.create(matchingAnnotation, adjustedAnnotationText));
            }

            return true;
        }

        @Override
        public boolean processStepDefinition(JavaStepDefinition stepDefinitionAnnotation) {
            StepType annotationStepType = stepDefinitionAnnotation.getAnnotationType();
            if (annotationStepType != getStepType()) {
                return true;
            }
            Set<String> annotationTexts = stepDefinitionAnnotation.getAnnotationTexts();
            for (String annotationText : annotationTexts) {
                String adjustedAnnotationText = actualStepPrefix + " " + annotationText;

                ParametrizedString pString = new ParametrizedString(adjustedAnnotationText);
                String complete = pString.complete(textBeforeCaret, hasPostParameter);

                if (StringUtil.isNotEmpty(complete)) {
                    PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                    if (matchingAnnotation != null) {
                        consumer.consume(LookupElementBuilder.create(matchingAnnotation, complete));
                    }
                } else if (prefixMatcher.prefixMatches(adjustedAnnotationText)) {
                    PsiAnnotation matchingAnnotation = stepDefinitionAnnotation.getAnnotation();
                    if (matchingAnnotation != null) {
                        consumer.consume(LookupElementBuilder.create(matchingAnnotation, adjustedAnnotationText));
                    }
                }

            }

            return true;
        }
    }
}
