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
package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.github.kumaraman21.intellijbehave.peg.JBehaveRule;
import com.github.kumaraman21.intellijbehave.peg.StoryPegParserDefinition;
import com.github.kumaraman21.intellijbehave.psi.*;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.apache.commons.lang.StringUtils.trim;

public class JBehaveStep extends JBehaveRule implements PsiNamedElement {
    public JBehaveStep(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    public StepType getStepType() {
        if (this instanceof StoryStepWhen) return StepType.WHEN;
        if (this instanceof StoryStepThen) return StepType.THEN;
        if (this instanceof StoryStepGiven) return StepType.GIVEN;
        if (this instanceof StoryStepAnd) {
            PsiElement prevSibling = getParent().getPrevSibling().getFirstChild();
            if (prevSibling instanceof JBehaveStep) {
                return ((JBehaveStep) prevSibling).getStepType();
            }
            return StepType.AND;
        }
        //return stepType;
        return StepType.IGNORABLE;
    }

    @Nullable
    public ASTNode getKeyword() {
        //return getNode().findChildByType(StoryTokenType.STEP_TYPES);
        return getNode().findChildByType(StoryPegParserDefinition.STEP_TYPES);
    }

    public String getStepText() {
        int offset = getStepTextOffset();
        final String text = String.format("%s%s", getFirstChild().getText(), getLastChild().getFirstChild().getText());

        if (offset <= 0 || offset >= text.length()) {
            return trim(text);
        } else {
            return trim(text.substring(offset));
        }
    }

    @Nullable
    public String getActualStepPrefix() {
        ASTNode keyword = getKeyword();
        if (keyword == null) { // that's weird!
            return null;
        }
        return keyword.getText();
    }

    public int getStepTextOffset() {
        String stepPrefix = getActualStepPrefix();
        return stepPrefix != null ? stepPrefix.length() + 1 : 0;
    }

    @Nullable
    private String getReferencedText(final PsiAnnotation annotation) {
        final Iterator<PsiLiteralExpression> refLiteralIt = PsiTreeUtil.findChildrenOfType(annotation,
                PsiLiteralExpression.class).iterator();
        if (refLiteralIt.hasNext()) {
            String text = refLiteralIt.next().getText();
            if (text.contains("\"")) {
                text = text.replace("\"", "");
            }
            return text;
        }
        return null;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        final PsiReference[] references = getReferences();
        if (references.length == 1) {
            final PsiReference myReference = references[0];
            final PsiMethod referencedMethod = (PsiMethod) myReference.resolve();
            if (referencedMethod != null) {
                final PsiModifierList refModifierList = referencedMethod.getModifierList();
                final PsiAnnotation[] refAnnotations = refModifierList.getAnnotations();
                for (PsiAnnotation refAnnotation : refAnnotations) {
                    //check if this is my myAnnotation
                    String refText = getReferencedText(refAnnotation);
                    if (refText != null) {
                        final boolean havePostParameters = hasStoryStepPostParameters();
                        final String oldPlainText = havePostParameters ? getStepText() + " TABLE" : getStepText();
                        final ParametrizedString pNewText = new ParametrizedString(name);
                        final ParametrizedString pRefText = new ParametrizedString(refText);
                        List<Pair<String, String>> oldTextTokens = pRefText.getTokensOf(oldPlainText);
                        if (oldTextTokens != null) {
                            List<String> tokensNewText = pNewText.textAccordingTo(oldTextTokens);
                            if (!tokensNewText.isEmpty()) {
                                if (havePostParameters) {
                                    tokensNewText.remove(tokensNewText.size() - 1);
                                }
                                String newText = StringUtils.join(tokensNewText, " ");
                                StringWriter stringWriter = new StringWriter();
                                PrintWriter printWriter = new PrintWriter(stringWriter);
                                printWriter.println("Scenario: dummy");
                                printWriter.print(getStepLineText());
                                printWriter.println(newText);
                                printWriter.flush();
                                printWriter.close();
                                PsiFile psiFile = PsiFileFactory.getInstance(getProject()).createFileFromText(
                                        "dummy.story", StoryFileType.STORY_FILE_TYPE, stringWriter.toString());
                                StoryStepLine newStepLine = getStoryStepLine(psiFile);
                                StoryStepLine oldStepLine = getStoryStepLine();
                                if (newStepLine != null && oldStepLine != null) {
                                    oldStepLine.replace(newStepLine);
                                }
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    private String getStepLineText() {
        return getNode().getFirstChildNode().getText();
    }

    private StoryStepLine getStoryStepLine(PsiElement psiElement) {
        Collection<StoryStepLine> stepLines1 = PsiTreeUtil.findChildrenOfType(psiElement, StoryStepLine.class);
        Iterator<StoryStepLine> iterator = stepLines1.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    public StoryStepLine getStoryStepLine() {
        return getStoryStepLine(this);
    }

    private Collection<StoryStepPostParameter> getStoryStepPostParameters() {
        return PsiTreeUtil.findChildrenOfType(this, StoryStepPostParameter.class);
    }

    private boolean hasStoryStepPostParameters() {
        return !getStoryStepPostParameters().isEmpty();
    }
}
