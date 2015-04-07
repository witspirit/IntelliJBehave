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

import com.github.kumaraman21.intellijbehave.parser.JBehaveFile;
import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.resolver.ScenarioStepReference;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.isStepDefinition;
import static com.google.common.collect.Sets.newHashSet;

public class UnusedStepDeclarationInspection extends BaseJavaLocalInspectionTool {
    @NotNull
    @Override
    public String getShortName() {
        return "UnusedStepDeclaration";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethod(final PsiMethod method) {
                Boolean isStepDefinition = (Boolean) ApplicationManager.getApplication().runReadAction(
                        new Computable() {
                            public Boolean compute() {
                                return isStepDefinition(method);
                            }
                        });

                if (!isStepDefinition) {
                    return;
                }

                Project project = method.getProject();
                StepUsageFinder stepUsageFinder = new StepUsageFinder(project);
                ProjectRootManager.getInstance(project).getFileIndex().iterateContent(stepUsageFinder);
                Set<ScenarioStep> stepUsages = stepUsageFinder.getStepUsages();

                for (ScenarioStep step : stepUsages) {
                    PsiReference[] references = step.getReferences();

                    if (references.length != 1 || !(references[0] instanceof ScenarioStepReference)) {
                        return;
                    }

                    ScenarioStepReference reference = (ScenarioStepReference) references[0];
                    JavaStepDefinition definition = reference.resolveToDefinition();

                    if (definition != null && definition.getAnnotatedMethod() != null && definition.getAnnotatedMethod().isEquivalentTo(
                            method)) {
                        return;
                    }
                }

                holder.registerProblem(method, "Step <code>#ref</code> is never used");
            }
        };
    }

    private static class StepUsageFinder implements ContentIterator {
        private Project project;
        private Set<ScenarioStep> stepUsages = newHashSet();

        private StepUsageFinder(Project project) {
            this.project = project;
        }

        @Override
        public boolean processFile(VirtualFile virtualFile) {
            if (virtualFile.isDirectory() || !virtualFile.getFileType().getDefaultExtension().equals("story")) {
                return true;
            }

            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (psiFile instanceof JBehaveFile) {
                List<ScenarioStep> steps = ((JBehaveFile) psiFile).getSteps();
                stepUsages.addAll(steps);
            }
            return true;
        }

        public Set<ScenarioStep> getStepUsages() {
            return stepUsages;
        }
    }
}
