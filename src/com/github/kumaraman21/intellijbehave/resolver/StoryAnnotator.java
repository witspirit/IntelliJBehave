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
import com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.peg.JBehaveRule;
import com.github.kumaraman21.intellijbehave.peg.PegStoryPath;
import com.github.kumaraman21.intellijbehave.peg.StoryPathPsiReference;
import com.github.kumaraman21.intellijbehave.psi.StoryScenarioTitle;
import com.github.kumaraman21.intellijbehave.psi.StoryStoryPath;
import com.github.kumaraman21.intellijbehave.service.JBehaveStepsIndex;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;

import static com.github.kumaraman21.intellijbehave.utility.ParametrizedString.StringToken;

public class StoryAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof JBehaveStep) {
            JBehaveStep step = (JBehaveStep) psiElement;
            Iterator<JavaStepDefinition> it = JBehaveStepsIndex.getInstance(step.getProject()).findStepDefinitions(step).iterator();
            if (it.hasNext()) {
                annotateParameters(step, it.next(), annotationHolder);
            } else {
                Annotation errorAnnotation = annotationHolder.createErrorAnnotation(step.getStoryStepLine(),
                        "No definition found for the step");
                errorAnnotation.setTextAttributes(StorySyntaxHighlighter.STORY_ERROR_NO_DEF_FOUND);
            }

        } else if (psiElement instanceof PegStoryPath) {
            PsiElement parent = psiElement.getParent();
            if (parent != null && !(parent instanceof StoryScenarioTitle)) {
                StoryStoryPath storyPath = (StoryStoryPath) psiElement;
                PsiReference[] references = storyPath.getReferences();
                if (references.length != 1 || !(references[0] instanceof StoryPathPsiReference)) {
                    return;
                }
                StoryPathPsiReference reference = (StoryPathPsiReference) references[0];

                if (reference.multiResolve(false).length == 0) {
                    Annotation errorAnnotation = annotationHolder.createErrorAnnotation(psiElement, "File not found");
                    errorAnnotation.setTextAttributes(StorySyntaxHighlighter.STORY_ERROR_FILE_NOT_FOUND);
                }
            }
        } else if (psiElement instanceof JBehaveRule) {
            ASTNode node = psiElement.getNode();
            IElementType elementType = node.getElementType();
            if (elementType != null) {
                TextAttributesKey textAttribute = StorySyntaxHighlighter.getTextAttribute(elementType);
                if (textAttribute != null) {
                    annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(textAttribute);
                }
            }
        }

    }

    private void annotateParameters(JBehaveStep step, JavaStepDefinition javaStepDefinition,
                                    AnnotationHolder annotationHolder) {
        String stepText = step.getStepText();
        String annotationText = javaStepDefinition.getAnnotationTextFor(stepText);
        if (annotationText == null) {
            stepText = stepText + " dummy";
            annotationText = javaStepDefinition.getAnnotationTextFor(stepText);
            if (annotationText == null) return;
        }
        ParametrizedString pString = new ParametrizedString(annotationText);

        Map<String, PsiType> mapNameToType = javaStepDefinition.mapNameToType();

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
                PsiElement elementAt = step.getContainingFile().findElementAt(offset);
                if (elementAt != null) {
                    ASTNode node = elementAt.getNode();
                    if (node != null) {
                        IElementType elementType = node.getElementType();
                        if (elementType == IStoryPegElementType.STORY_TOKEN_WORD) {
                            infoAnnotation.setTextAttributes(StorySyntaxHighlighter.STEP_PARAMETER);
                        }
                    }
                }
            }
            ++i;
            offset += length;
        }
    }

}
