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
import com.github.kumaraman21.intellijbehave.service.JBehaveStepsIndex;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class StepPsiReference implements PsiPolyVariantReference {
    private final JBehaveStep myStep;
    private final TextRange myRange;

    public StepPsiReference(JBehaveStep element, TextRange range) {
        myStep = element;
        myRange = range;
    }

    @Override
    public @NotNull JBehaveStep getElement() {
        return myStep;
    }

    @Override
    public @NotNull TextRange getRangeInElement() {
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
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        return myStep;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return myStep;
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element) {
        PsiManagerEx manager = null;

        //This part is a simplified version of 'multiResolve()', and using it instead of 'multiResolve()',
        // so that unnecessary calls to 'getAnnotatedMethod()' and instantiation of ResolveResults can be avoided.
        var resolvedElements = new ArrayList<PsiMethod>(4);

        for (JavaStepDefinition resolvedStepDefinition : resolveToDefinitions()) {
            final PsiMethod method = resolvedStepDefinition.getAnnotatedMethod();
            if (method != null && !resolvedElements.contains(method)) {
                if (manager == null) manager = getElement().getManager();
                if (manager.areElementsEquivalent(method, element)) {
                    return true;
                }
                resolvedElements.add(method);
            }
        }

        return false;
    }

    @Override
    public Object @NotNull [] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    /**
     * Returns the first Java step definition found for this reference.
     */
    @Nullable("When no definition could be resolved.")
    public JavaStepDefinition resolveToDefinition() {
        Collection<JavaStepDefinition> definitions = resolveToDefinitions();
        return definitions.isEmpty() ? null : definitions.iterator().next();
    }

    @NotNull
    private Collection<JavaStepDefinition> resolveToDefinitions() {
        return JBehaveStepsIndex.getInstance(myStep.getProject()).findStepDefinitions(myStep);
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        var result = new ArrayList<ResolveResult>(4);
        var resolvedElements = new ArrayList<PsiMethod>(4);

        for (JavaStepDefinition resolvedStepDefinition : resolveToDefinitions()) {
            final PsiMethod method = resolvedStepDefinition.getAnnotatedMethod();
            if (method != null && !resolvedElements.contains(method)) {
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

        return result.toArray(ResolveResult.EMPTY_ARRAY);
    }
}
