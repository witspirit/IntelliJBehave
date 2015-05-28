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

import com.github.kumaraman21.intellijbehave.highlighter.JBehaveSyntaxHighlighter;
import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.psi.*;
import com.github.kumaraman21.intellijbehave.resolver.ScenarioStepReference;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.utility.ParametrizedString;
import com.intellij.lang.ASTNode;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.trim;

public class ScenarioStep extends ParserRule implements PsiNamedElement {
    public ScenarioStep(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    public StepType getStepType() {
        ASTNode stepType = getStepTypeAsNode();
        ASTNode firstChildNode = stepType.getFirstChildNode();
        IElementType elementType = firstChildNode.getElementType();
        String key = elementType.toString();
        try {
            StepType type = StepType.valueOf(key.toUpperCase());
            if (type == StepType.AND) {
                PsiElement prevSibling = getPrevSibling();
                while (!(prevSibling instanceof ScenarioStep) && prevSibling != null) {
                    prevSibling = prevSibling.getPrevSibling();
                }
                if (prevSibling != null) {
                    return ((ScenarioStep) prevSibling).getStepType();
                }
            }
            return type;
        } catch (IllegalArgumentException ignored) {

        }
        return StepType.IGNORABLE;
    }

    private ASTNode getStepTypeAsNode() {
        return getNode().getFirstChildNode();
    }

    @Nullable
    private ASTNode getKeyword() {
        //return getNode().findChildByType(StoryTokenType.STEP_TYPES);
        return getNode().findChildByType(IJBehaveElementType.JB_STEP_PAR);
    }

    private String getStepText() {
        int offset = getStepTextOffset();
        PsiElement firstChild = PsiTreeUtil.getChildOfType(this, JBehaveStepPar.class);
        PsiElement firstChild1 = PsiTreeUtil.getChildOfType(this, JBehaveStepArgument.class);
        if (firstChild != null && firstChild1 != null) {
            final String text = String.format("%s %s", firstChild.getText(), firstChild1.getText());
            if (offset <= 0 || offset >= text.length()) {
                return trim(text);
            } else {
                return trim(text.substring(offset));
            }
        }
        return "";
    }

    public String getAnnotatedStoryLine() {
        StepType stepType = getStepType();
        String lowerCase = stepType.toString().toLowerCase();

        JBehaveStepLine storyStepLine = getStoryStepLine();
        if (storyStepLine != null) {
            lowerCase = Character.toUpperCase(lowerCase.charAt(0)) + lowerCase.substring(1);
            final String format = hasStoryStepPostParameters() ? "%s %s $" : "%s %s";
            return String.format(format, lowerCase, storyStepLine.getText());
        }
        return lowerCase;
    }


    public String getStoryLine() {
        ASTNode stepTypeAsNode = getStepTypeAsNode();
        JBehaveStepLine storyStepLine = getStoryStepLine();
        if (stepTypeAsNode != null && storyStepLine != null)
            return String.format("%s %s", stepTypeAsNode.getText(), storyStepLine.getText());
        else {
            if (stepTypeAsNode != null) {
                return String.format("%s", stepTypeAsNode.getText());
            }
        }
        return "";
    }

    @Nullable
    public String getActualStepPrefix() {
        ASTNode keyword = getKeyword();
        if (keyword == null) { // that's weird!
            return null;
        }
        return keyword.getText();
    }

    private int getStepTextOffset() {
        String stepPrefix = getActualStepPrefix();
        return stepPrefix != null ? stepPrefix.length() + 1 : 0;
    }

    @Nullable
    private String getReferencedText(final PsiAnnotation annotation) {
        final Iterator<PsiLiteralExpression> refLiteralIt =
                PsiTreeUtil.findChildrenOfType(annotation, PsiLiteralExpression.class).iterator();
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
                final PsiAnnotation[] refAnnotations = referencedMethod.getModifierList().getAnnotations();
                for (PsiAnnotation refAnnotation : refAnnotations) {
                    //check if this is my myAnnotation
                    String refText = getReferencedText(refAnnotation);
                    if (refText != null) {
                        final boolean havePostParameters = hasStoryStepPostParameters();
                        final String oldPlainText = havePostParameters ? getStepText() + " TABLE" : getStepText();
                        final ParametrizedString pNewText = new ParametrizedString(name);
                        final ParametrizedString pRefText = new ParametrizedString(refText);
                        List<Pair<ParametrizedString.ContentToken, String>> oldTextTokens =
                                pRefText.getTokensOf(oldPlainText);
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
                                printWriter.print(getStepTypeAsNode().getText());
                                printWriter.print(" ");
                                printWriter.print(newText);
                                if (havePostParameters) {
                                    JBehaveStepArgument stepArgument = ((JBehaveStep) this).getStepArgument();
                                    if (stepArgument != null) {
                                        JBehaveStoryPaths storyPaths1 = stepArgument.getStoryPaths();
                                        JBehaveTable table1 = stepArgument.getTable();
                                        if (storyPaths1 != null) {
                                            printWriter.print(" ");
                                            printWriter.print("dummy/story/story.story");
                                        } else {
                                            if (table1 != null) {
                                                printWriter.println();
                                                printWriter.print("|dummy|story|story|story|");
                                            }
                                        }
                                    }
                                } else printWriter.println();
                                printWriter.flush();
                                printWriter.close();
                                String newNodeAsText = stringWriter.toString();
                                PsiFile psiFile = PsiFileFactory.getInstance(getProject())
                                                                .createFileFromText("dummy.story",
                                                                                    JBehaveFileType.JBEHAVE_FILE_TYPE,
                                                                                    newNodeAsText);
                                JBehaveStepLine newStepLine = getStoryStepLine(psiFile);
                                JBehaveStepLine oldStepLine = getStoryStepLine();
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

    public String getStepLineText() {
        return getNode().getText();
    }

    private ScenarioStep getStoryStep(PsiElement psiElement) {
        Collection<ScenarioStep> stepLines1 = PsiTreeUtil.findChildrenOfType(psiElement, ScenarioStep.class);
        Iterator<ScenarioStep> iterator = stepLines1.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    public ScenarioStep getStoryStep() {
        return getStoryStep(this);
    }

    private JBehaveStepLine getStoryStepLine(PsiElement psiElement) {
        Collection<JBehaveStepLine> stepLines1 = PsiTreeUtil.findChildrenOfType(psiElement, JBehaveStepLine.class);
        Iterator<JBehaveStepLine> iterator = stepLines1.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    public JBehaveStepLine getStoryStepLine() {
        return getStoryStepLine(this);
    }

    //    private Collection<JBehaveStepPostParameter> getStoryStepPostParameters() {
    //        return PsiTreeUtil.findChildrenOfType(this, JBehaveStepPostParameter.class);
    //    }

    public boolean hasStoryStepPostParameters() {
        JBehaveStepArgument stepArgument = ((JBehaveStep) this).getStepArgument();
        if (stepArgument != null) {
            JBehaveStoryPaths storyPaths1 = stepArgument.getStoryPaths();
            JBehaveTable table1 = stepArgument.getTable();
            return storyPaths1 != null || table1 != null;
        }
        return false;
        //        return !(PsiTreeUtil.findChildrenOfType(this, JBehaveTable.class).isEmpty() &&
        //                PsiTreeUtil.findChildrenOfType(this, JBehaveStoryPath.class).isEmpty());
        //return !getStoryStepPostParameters().isEmpty();
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return getAnnotatedStoryLine();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                switch (getStepType()) {
                    case THEN:
                        return JBehaveIcons.THEN;
                    case AND:
                        return JBehaveIcons.AND;
                    case WHEN:
                        return JBehaveIcons.WHEN;
                    case GIVEN:
                        return JBehaveIcons.GIVEN;
                }
                return JBehaveIcons.IGNORABLE;
            }
        };
    }

    @Override
    public void annotate(AnnotationHolder annotationHolder) {
        //        Iterator<JavaStepDefinition> it =
        //                JavaStepDefinitionsIndex.getInstance(getProject()).findStepDefinitions(this).iterator();
        PsiReference[] references = getReferences();
        if (references.length > 0) {
            JavaStepDefinition javaStepDefinition = ((ScenarioStepReference) references[0]).resolveToDefinition();
            if (!(javaStepDefinition != null && annotateParameters(javaStepDefinition, annotationHolder))) {
                annotateStepError(annotationHolder);
            }
        }
    }

    private void annotateStepError(@NotNull AnnotationHolder annotationHolder) {
        Annotation errorAnnotation =
                annotationHolder.createErrorAnnotation(getStoryStepLine(), "No definition found for the step");
        errorAnnotation.setTextAttributes(JBehaveSyntaxHighlighter.JB_ERROR_NO_DEF_FOUND);
    }

    private boolean annotateParameters(JavaStepDefinition javaStepDefinition, AnnotationHolder annotationHolder) {
        String storyStepText = getStepText();
        String javaStepText = javaStepDefinition.getAnnotationTextFor(storyStepText);
        if (javaStepText == null) {
            storyStepText += " dummy";
            javaStepText = javaStepDefinition.getAnnotationTextFor(storyStepText);
            if (javaStepText == null) return false;
        }
        ParametrizedString pJavaStepText = new ParametrizedString(javaStepText);

        Map<String, PsiType> mapNameToType = javaStepDefinition.mapNameToType();

        final int offset = getTextOffset() + getStepTextOffset();
        final List<Pair<ParametrizedString.ContentToken, String>> tokensOf = pJavaStepText.getTokensOf(storyStepText);
        if (tokensOf != null) {
            final int tokensOfSize = hasStoryStepPostParameters() ? tokensOf.size() - 1 : tokensOf.size();

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
                                getContainingFile().findElementAt(offset + contentToken.getStart() + s.getStart());
                        if (elementAt != null &&
                                elementAt.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_WORD) {
                            elementAt.putUserData(ParserRule.isStepParameter, true);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public String toString() {
        return getAnnotatedStoryLine();
    }
}
