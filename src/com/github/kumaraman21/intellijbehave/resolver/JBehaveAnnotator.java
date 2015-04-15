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
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiType;
import com.intellij.psi.tree.IElementType;
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
                annotateParameters(step, it.next(), annotationHolder);
            } else {
                Annotation errorAnnotation = annotationHolder
                        .createErrorAnnotation(step.getStoryStepLine(), "No definition found for the step");
                errorAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_ERROR_NO_DEF_FOUND);
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
            ASTNode node = psiElement.getNode();
            IElementType elementType = node.getElementType();
            if (elementType != null) {
                annotateElement(psiElement, annotationHolder, elementType);
            }
        }

    }


    private void annotateElement(PsiElement psiElement, AnnotationHolder annotationHolder, IElementType elementType) {
        TextAttributesKey textAttribute = JBehaveSyntaxHighlighter.getTextAttribute(elementType);
        if (textAttribute != null) {
            annotationHolder.createInfoAnnotation(psiElement, null).setTextAttributes(textAttribute);
        }
        if (elementType == IJBehaveElementType.JB_TABLE_CELL || elementType == IJBehaveElementType.JB_TABLE_ROW) {
            PsiElement parent = psiElement.getParent();
            if (parent != null) {
                PsiElement[] children = parent.getChildren();
                if (children.length > 0 && children[0] == psiElement) {
                    annotateElement(parent, annotationHolder, parent.getNode().getElementType());
                }
            }
        }
    }

    private void annotateParameters(ScenarioStep step, JavaStepDefinition javaStepDefinition,
                                    AnnotationHolder annotationHolder) {
        String storyStepText = step.getStepText();
        String javaStepText = javaStepDefinition.getAnnotationTextFor(storyStepText);
        if (javaStepText == null) {
            storyStepText = storyStepText + " dummy";
            javaStepText = javaStepDefinition.getAnnotationTextFor(storyStepText);
            if (javaStepText == null) return;
        }
        ParametrizedString pString = new ParametrizedString(javaStepText);

        Map<String, PsiType> mapNameToType = javaStepDefinition.mapNameToType();

        int offset = step.getTextOffset() + step.getStepTextOffset();
        List<Pair<ParametrizedString.ContentToken, String>> tokensOf = pString.getTokensOf(storyStepText);
        final int lastToken = tokensOf.size() - 1;
        int i = 0;
        final boolean hasStoryStepPostParameters = step.hasStoryStepPostParameters();

        for (Pair<ParametrizedString.ContentToken, String> pair : tokensOf) {
            ParametrizedString.ContentToken contentToken = pair.first;
            String value = contentToken.value();
            if (pair.second != null) {
                ParametrizedString.Token token1 = pString.getToken(i);
                PsiType parType = mapNameToType.get(token1.value());
                Annotation infoAnnotation;
                if (parType != null) {
                    infoAnnotation = annotationHolder.createInfoAnnotation(
                            TextRange.from(offset + contentToken.getStart(), contentToken.getLength()),
                            String.format("Parameter: <%s> %s", parType.getCanonicalText(), token1.value()));
                } else {
                    infoAnnotation = annotationHolder.createInfoAnnotation(
                            TextRange.from(offset + contentToken.getStart(), contentToken.getLength()),
                            String.format("Parameter: %s", token1.value()));

                }
                if (!(hasStoryStepPostParameters && i == lastToken))
                    infoAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.STEP_PARAMETER);
                else {
                    infoAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_DEFAULT_TEXT);
                }
                List<ParametrizedString.ContentToken> stringList = ParametrizedString.split(value);
                PsiElement elementAt;
                for (ParametrizedString.ContentToken s : stringList) {
                    elementAt = step.getContainingFile().findElementAt(offset + s.getStart());
                    if (elementAt != null &&
                            elementAt.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_WORD) {
                        elementAt.putUserData(ParserRule.isStepParameter, true);
                    }
                }
            }
            ++i;
        }
    }

}
