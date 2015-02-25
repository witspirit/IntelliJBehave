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

import com.github.kumaraman21.intellijbehave.highlighter.StorySyntaxHighlighter;
import com.github.kumaraman21.intellijbehave.parser.JBehaveGivenStories;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.github.kumaraman21.intellijbehave.utility.ParametrizedString.StringToken;

public class StoryAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof JBehaveStep) {
            JBehaveStep step = (JBehaveStep) psiElement;
            PsiReference[] references = step.getReferences();

            if (references.length != 1 || !(references[0] instanceof StepPsiReference)) {
                return;
            }

            StepPsiReference reference = (StepPsiReference) references[0];
            JavaStepDefinition definition = reference.resolveToDefinition();

            if (definition == null) {
                annotationHolder.createErrorAnnotation(psiElement, "No definition found for the step");
            } else {
                annotateParameters(step, definition, annotationHolder);
            }
        } else if (psiElement instanceof JBehaveGivenStories) {
            JBehaveGivenStories givenStories = (JBehaveGivenStories) psiElement;
            PsiReference[] references = givenStories.getReferences();
            if (references.length != 1 || !(references[0] instanceof GivenStoriesPsiReference)) {
                return;
            }
            GivenStoriesPsiReference reference = (GivenStoriesPsiReference) references[0];

            if (reference.multiResolve(false).length == 0) {
                annotationHolder.createErrorAnnotation(psiElement, "File not found");
            }
        }
    }

    private void annotateParameters(JBehaveStep step, JavaStepDefinition javaStepDefinition,
                                    AnnotationHolder annotationHolder) {
        String stepText = step.getStepText();
        String annotationText = javaStepDefinition.getAnnotationTextFor(stepText);
        ParametrizedString pString = new ParametrizedString(annotationText);

        Map<String, PsiType> mapNameToType = new HashMap<String, PsiType>();

        PsiReference[] references = step.getReferences();
        for (PsiReference reference : references) {
            PsiElement resolve = reference.resolve();
            if (resolve instanceof PsiMethod) {
                PsiMethod method = (PsiMethod) resolve;
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
        }

        int offset = step.getTextOffset() + step.getStepTextOffset();
        int i = 0;
        for (StringToken token : pString.tokenize(stepText)) {
            int length = token.getValue().length();
            if (token.isIdentifier()) {
                ParametrizedString.Token token1 = pString.getToken(i);
                PsiType parType = mapNameToType.get(token1.value());
                Annotation infoAnnotation;
                if (parType != null) {
                    infoAnnotation = annotationHolder.createInfoAnnotation(TextRange.from(offset, length),
                            String.format("Parameter: <%s> %s", parType.getCanonicalText(), token1.value()));
                } else {
                    infoAnnotation = annotationHolder.createInfoAnnotation(TextRange.from(offset, length),
                            String.format("Parameter: %s", token1.value()));

                }
                infoAnnotation.setTextAttributes(StorySyntaxHighlighter.STEP_PARAMETER);
            }
            ++i;
            offset += length;
        }
    }
}
