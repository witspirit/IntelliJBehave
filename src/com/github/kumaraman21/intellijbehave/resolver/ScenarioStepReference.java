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

import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStepLine;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinitionsIndex;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ScenarioStepReference implements PsiPolyVariantReference {
    private final ScenarioStep myStep;
    private TextRange myRange = null;
    private final Set<PsiAnnotation> theAnnotations = new HashSet<PsiAnnotation>();

    public ScenarioStepReference(@NotNull ScenarioStep element, @NotNull TextRange range) {
        myStep = element;
        ASTNode node = element.getNode();
        final int startOffset = node.getStartOffset();
        Iterator<JBehaveStepLine> it = PsiTreeUtil.findChildrenOfType(myStep, JBehaveStepLine.class).iterator();
        if (it.hasNext()) {
            JBehaveStepLine next = it.next();
            ASTNode nextNode = next.getNode();
            myRange = new TextRange(range.getStartOffset(), nextNode.getTextRange().getEndOffset() - startOffset);
        } else {
            myRange = new TextRange(range.getStartOffset(), node.getTextRange().getEndOffset() - startOffset);
        }
    }

    @Override
    public ScenarioStep getElement() {
        return myStep;
    }

    @Override
    public TextRange getRangeInElement() {
        return myRange;
    }

    @Override
    public PsiElement resolve() {
        ResolveResult[] result = multiResolve(true);
        return result.length == 1 ? result[0].getElement() : null;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return myStep.getText();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return myStep.setName(newElementName);
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return myStep;
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        ResolveResult[] resolvedResults = multiResolve(false);

        for (ResolveResult resolveResult : resolvedResults) {
            if (myStep.getManager().areElementsEquivalent(resolveResult.getElement(), element)) {
                return true;
            }
        }

        return false;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @Nullable
    public JavaStepDefinition resolveToDefinition() {
        Collection<JavaStepDefinition> definitions = resolveToDefinitions();
        return definitions.isEmpty() ? null : definitions.iterator().next();
    }

    @NotNull
    private Collection<JavaStepDefinition> resolveToDefinitions() {
        return JavaStepDefinitionsIndex.getInstance(myStep.getProject()).findStepDefinitions(myStep);
    }

    public boolean containsAnnotation(PsiAnnotation psiAnnotation) {
        return theAnnotations.contains(psiAnnotation);
    }

    public Set<PsiAnnotation> getAnnotations() {
        Set<PsiAnnotation> result = new HashSet<PsiAnnotation>();
        result.addAll(theAnnotations);
        return result;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<ResolveResult> result = new ArrayList<ResolveResult>();
        List<PsiMethod> resolvedElements = new ArrayList<PsiMethod>();
        theAnnotations.clear();

        Collection<JavaStepDefinition> resolvedStepDefinitions = resolveToDefinitions();

        for (JavaStepDefinition resolvedStepDefinition : resolvedStepDefinitions) {
            final PsiMethod method = resolvedStepDefinition.getAnnotatedMethod();
            if (method != null && !resolvedElements.contains(method)) {
                theAnnotations.add(resolvedStepDefinition.getAnnotation());
                resolvedElements.add(method);
                result.add(new MyResolveResult(method));
            }
        }

        return result.toArray(new ResolveResult[result.size()]);
    }

    private static class MyResolveResult implements ResolveResult {
        private final PsiMethod method;

        public MyResolveResult(PsiMethod method) {
            this.method = method;
        }

        public PsiElement getElement() {
            return method;
        }

        public boolean isValidResult() {
            return true;
        }
    }
}
