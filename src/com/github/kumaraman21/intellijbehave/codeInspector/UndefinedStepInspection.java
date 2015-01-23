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
package com.github.kumaraman21.intellijbehave.codeInspector;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import org.jetbrains.annotations.NotNull;

public class UndefinedStepInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public String getShortName() {
        return "UndefinedStep";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new PsiElementVisitor() {

            @Override
            public void visitElement(PsiElement psiElement) {
                super.visitElement(psiElement);

                if (!(psiElement instanceof JBehaveStep)) {
                    return;
                }

                JBehaveStep step = (JBehaveStep) psiElement;
                PsiReference[] references = step.getReferences();

                if (references.length != 1 || !(references[0] instanceof StepPsiReference)) {
                    return;
                }

                StepPsiReference reference = (StepPsiReference) references[0];
                JavaStepDefinition definition = reference.resolveToDefinition();

                if (definition == null) {
                    holder.registerProblem(step, "Step <code>#ref</code> is not defined");
                }
            }
        };
    }
}

