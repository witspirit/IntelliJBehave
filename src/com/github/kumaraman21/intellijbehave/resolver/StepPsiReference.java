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

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.psi.StoryStepLine;
import com.github.kumaraman21.intellijbehave.service.JBehaveStepsIndex;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class StepPsiReference implements PsiPolyVariantReference {
    private final JBehaveStep myStep;
    private TextRange myRange = null;

    public StepPsiReference(@NotNull JBehaveStep element, @NotNull TextRange range) {
        myStep = element;
        ASTNode node = element.getNode();
        final int startOffset = node.getStartOffset();
//        ASTNode lastChildNode = node.getLastChildNode();
        Iterator<StoryStepLine> it = PsiTreeUtil.findChildrenOfType(myStep, StoryStepLine.class).iterator();
        if (it.hasNext()) {
            StoryStepLine next = it.next();
            ASTNode nextNode = next.getNode();
            myRange = new TextRange(range.getStartOffset(), nextNode.getTextRange().getEndOffset() - startOffset);
        }
        else{
            myRange = new TextRange(range.getStartOffset(), node.getTextRange().getEndOffset() - startOffset);
        }

//        if (lastChildNode != null) {
//            ASTNode firstChildNode = lastChildNode.getFirstChildNode();
//            if (firstChildNode != null) {
//                myRange = new TextRange(range.getStartOffset(),
//                        firstChildNode.getTextRange().getEndOffset() - startOffset);
//            }
//            else{
//                String r="";
//            }
//        }
//        else{
//            String r="";
//        }
    }

    @Override
    public JBehaveStep getElement() {
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
            if (getElement().getManager().areElementsEquivalent(resolveResult.getElement(), element)) {
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
    public Collection<JavaStepDefinition> resolveToDefinitions() {
        return JBehaveStepsIndex.getInstance(myStep.getProject()).findStepDefinitions(myStep);
    }

    private Set<PsiAnnotation> theAnnotations = new HashSet<PsiAnnotation>();

    public boolean containsAnnotation(PsiAnnotation psiAnnotation) {
        return theAnnotations.contains(psiAnnotation);
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
                result.add(new ResolveResult() {
                    public PsiElement getElement() {
                        return method;
                    }

                    public boolean isValidResult() {
                        return true;
                    }
                });
            }
        }

        return result.toArray(new ResolveResult[result.size()]);
    }
}
