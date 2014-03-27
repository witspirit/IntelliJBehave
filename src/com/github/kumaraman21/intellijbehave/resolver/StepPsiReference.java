/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.utility.ScanUtils;
import com.intellij.openapi.util.Comparing;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.parsers.StepMatcher;
import org.jbehave.core.parsers.StepPatternParser;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.StringUtils.trim;

public class StepPsiReference implements PsiReference {

    private StepPsiElement stepPsiElement;

    public StepPsiReference(StepPsiElement stepPsiElement) {
        this.stepPsiElement = stepPsiElement;
    }

    @Override
    public PsiElement getElement() {
        return stepPsiElement;
    }

    @Override
    public TextRange getRangeInElement() {
        return TextRange.from(0, stepPsiElement.getTextLength());
    }

    public StepDefinitionAnnotation stepDefinitionAnnotation() {
        StepType stepType = stepPsiElement.getStepType();
        String stepText = stepPsiElement.getStepText();

        StepAnnotationFinder stepAnnotationFinder = new StepAnnotationFinder(stepType, stepText, stepPsiElement);
        ScanUtils.iterateInContextOf(stepPsiElement, stepAnnotationFinder);

        return stepAnnotationFinder.getMatchingAnnotation();
    }

    @Override
    public PsiElement resolve() {
        StepDefinitionAnnotation stepDefinitionAnnotation = stepDefinitionAnnotation();
        if (stepDefinitionAnnotation == null)
            return null;
        return stepDefinitionAnnotation.getAnnotation();
    }

    private static final boolean useVariants = false;

    @NotNull
    @Override
    public Object[] getVariants() {

        if (useVariants) {
            StepType stepType = stepPsiElement.getStepType();
            String actualStepPrefix = stepPsiElement.getActualStepPrefix();

            StepSuggester stepSuggester = new StepSuggester(stepType, actualStepPrefix, stepPsiElement);
            ScanUtils.iterateInContextOf(stepPsiElement, stepSuggester);

            return stepSuggester.getSuggestions().toArray();
        } else {
            return new Object[0];
        }
    }

    private static class StepSuggester extends StepDefinitionIterator {

        private final List<String> suggestions = newArrayList();
        private final String actualStepPrefix;

        public StepSuggester(StepType stepType, String actualStepPrefix, PsiElement storyRef) {
            super(stepType, storyRef);
            this.actualStepPrefix = actualStepPrefix;
        }

        @Override
        public boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation) {
            suggestions.add(actualStepPrefix + " " + stepDefinitionAnnotation.getAnnotationText());
            return true;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }
    }

    private static class StepAnnotationFinder extends StepDefinitionIterator {

        private StepType stepType;
        private String stepText;
        private StepDefinitionAnnotation matchingAnnotation;
        private StepPatternParser stepPatternParser = new RegexPrefixCapturingPatternParser();

        private StepAnnotationFinder(StepType stepType, String stepText, PsiElement storyRef) {
            super(stepType, storyRef);
            this.stepType = stepType;
            this.stepText = stepText;
        }

        @Override
        public boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation) {
            StepMatcher stepMatcher = stepPatternParser.parseStep(stepType, stepDefinitionAnnotation.getAnnotationText());

            if (stepMatcher.matches(stepText)) {
                matchingAnnotation = stepDefinitionAnnotation;

                return false;
            }
            return true;
        }

        public StepDefinitionAnnotation getMatchingAnnotation() {
            return matchingAnnotation;
        }
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return trim(stepPsiElement.getText());
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return psiElement instanceof StepPsiElement && Comparing.equal(resolve(), psiElement);
    }

    @Override
    public boolean isSoft() {
        return false;
    }
}
