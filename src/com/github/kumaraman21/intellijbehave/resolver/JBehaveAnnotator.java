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

import com.github.kumaraman21.intellijbehave.highlighter.JBehaveSyntaxHighlighter;
import com.github.kumaraman21.intellijbehave.parser.*;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinitionsIndex;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JBehaveAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement psiElement, @NotNull AnnotationHolder annotationHolder) {
        if (psiElement instanceof ScenarioStep) {
            ScenarioStep step = (ScenarioStep) psiElement;
            Iterator<JavaStepDefinition> it =
                    JavaStepDefinitionsIndex.getInstance(step.getProject()).findStepDefinitions(step).iterator();
            if (it.hasNext()) {
                try {
                    annotateParameters(step, it.next(), annotationHolder);
                } catch (IllegalArgumentException e) {
                    annotateStepError(annotationHolder, step);
                }
            } else {
                annotateStepError(annotationHolder, step);
            }

        } else if (psiElement instanceof StoryPath) {
            StoryPath storyPath = (StoryPath) psiElement;
            PsiReference[] references = storyPath.getReferences();
            if (references.length != 1 || !(references[0] instanceof StoryPathPsiReference)) {
                return;
            }
            StoryPathPsiReference reference = (StoryPathPsiReference) references[0];

            if (reference.multiResolve(false).length == 0) {
                PsiElement parent1 = psiElement.getParent();
                if (parent1 != null) {
                    PsiElement parent = parent1.getParent();
                    if (parent != null && parent.getNode().getElementType() == IJBehaveElementType.JB_GIVEN_STORIES) {
                        Annotation errorAnnotation =
                                annotationHolder.createErrorAnnotation(psiElement, "File not found");
                        errorAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_ERROR_FILE_NOT_FOUND);
                    }
                }
            } else {
                Annotation infoAnnotation = annotationHolder.createInfoAnnotation(psiElement, null);
                infoAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_STORY_PATH);
            }
        } else if (psiElement instanceof ParserRule) {
            annotateElement(psiElement, annotationHolder);
        }

    }

    private void annotateStepError(@NotNull AnnotationHolder annotationHolder, ScenarioStep step) {
        Annotation errorAnnotation =
                annotationHolder.createErrorAnnotation(step.getStoryStepLine(), "No definition found for the step");
        errorAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_ERROR_NO_DEF_FOUND);
    }

    private void annotateElement(PsiElement psiElement, AnnotationHolder annotationHolder) {
        TextAttributesKey textAttribute =
                JBehaveSyntaxHighlighter.getTextAttribute(psiElement.getNode().getElementType());
        if (textAttribute != null) {
            annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(textAttribute);
        }
    }

    private void annotateParameters(ScenarioStep step, JavaStepDefinition javaStepDefinition,
                                    AnnotationHolder annotationHolder) throws IllegalArgumentException {
        String storyStepText = step.getStepText();
        String javaStepText = javaStepDefinition.getAnnotationTextFor(storyStepText);
        if (javaStepText == null) {
            storyStepText = storyStepText + " dummy";
            javaStepText = javaStepDefinition.getAnnotationTextFor(storyStepText);
            if (javaStepText == null) return;
        }
        ParametrizedString pJavaStepText = new ParametrizedString(javaStepText);

        Map<String, PsiType> mapNameToType = javaStepDefinition.mapNameToType();

        final int offset = step.getTextOffset() + step.getStepTextOffset();
        final List<Pair<ParametrizedString.ContentToken, String>> tokensOf = pJavaStepText.getTokensOf(storyStepText);
        if (tokensOf != null) {
            final int tokensOfSize = step.hasStoryStepPostParameters() ? tokensOf.size() - 1 : tokensOf.size();

            for (int i = 0; i < tokensOfSize; i++) {
                final Pair<ParametrizedString.ContentToken, String> pair = tokensOf.get(i);
                final ParametrizedString.ContentToken contentToken = pair.first;
                if (pair.second != null) {
                    ParametrizedString.Token pToken = pJavaStepText.getToken(i);
                    PsiType parameterType = mapNameToType.get(pToken.value());
                    final String format = parameterType != null ?
                            String.format("Parameter: <%s> %s", parameterType.getCanonicalText(), pToken.value()) :
                            String.format("Parameter: %s", pToken.value());
                    final TextRange textRange =
                            TextRange.from(offset + contentToken.getStart(), contentToken.getLength());
                    //
                    annotationHolder.createInfoAnnotation(textRange, format)
                                    .setTextAttributes(JBehaveSyntaxHighlighter.STEP_PARAMETER);
                    //
                    for (ParametrizedString.ContentToken s : ParametrizedString.split(contentToken.value())) {
                        final PsiElement elementAt =
                                step.getContainingFile().findElementAt(offset + contentToken.getStart() + s.getStart());
                        if (elementAt != null &&
                                elementAt.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_WORD) {
                            elementAt.putUserData(ParserRule.isStepParameter, true);
                        }
                    }
                }
            }
        }
    }
}
