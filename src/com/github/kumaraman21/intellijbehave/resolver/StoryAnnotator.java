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
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.utility.ParametrizedString.StringToken;

public class StoryAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (!(psiElement instanceof StepPsiElement)) {
            return;
        }

        StepPsiElement stepPsiElement = (StepPsiElement) psiElement;
        StepDefinitionAnnotation annotationDef = stepPsiElement.getReference().stepDefinitionAnnotation();
        if (annotationDef == null) {
            annotationHolder.createErrorAnnotation(psiElement, "No definition found for the step");
        } else {
            annotateParameters(stepPsiElement, annotationDef, annotationHolder);
        }
    }

    private void annotateParameters(StepPsiElement stepPsiElement,
                                    StepDefinitionAnnotation annotation,
                                    AnnotationHolder annotationHolder) {
        String stepText = stepPsiElement.getStepText();
        String annotationText = annotation.getAnnotationText();
        ParametrizedString pString = new ParametrizedString(annotationText);

        int offset = stepPsiElement.getTextOffset();
        for (StringToken token : pString.tokenize(stepText)) {
            int length = token.getValue().length();
            if (token.isIdentifier()) {
                annotationHolder.createInfoAnnotation(
                        TextRange.from(offset, length), "Parameter");
            }
            offset += length;
        }
    }
}
