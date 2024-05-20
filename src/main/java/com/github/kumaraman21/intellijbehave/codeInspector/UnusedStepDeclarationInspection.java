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
import com.github.kumaraman21.intellijbehave.parser.StoryFile;
import com.github.kumaraman21.intellijbehave.resolver.StepPsiReference;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.isStepDefinition;

public class UnusedStepDeclarationInspection extends AbstractBaseJavaLocalInspectionTool {
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
                Boolean isStepDefinition = ReadAction.compute(() -> isStepDefinition(method));

                if (!isStepDefinition) {
                    return;
                }

                Project project = method.getProject();
                StepUsageFinder stepUsageFinder = new StepUsageFinder(project);
                ProjectRootManager.getInstance(project).getFileIndex().iterateContent(stepUsageFinder);
                Set<JBehaveStep> stepUsages = stepUsageFinder.getStepUsages();

                for (JBehaveStep step : stepUsages) {
                    PsiReference[] references = step.getReferences();

                    if (references.length != 1 || !(references[0] instanceof StepPsiReference)) {
                        return;
                    }

                    StepPsiReference reference = (StepPsiReference) references[0];
                    JavaStepDefinition definition = reference.resolveToDefinition();

                    if (definition != null && definition.getAnnotatedMethod() != null && definition.getAnnotatedMethod().isEquivalentTo(method)) {
                        return;
                    }
                }

                holder.registerProblem(method, "Step <code>#ref</code> is never used");
            }
        };
    }

    private static class StepUsageFinder implements ContentIterator {
        private final Project project;
        private final Set<JBehaveStep> stepUsages = new HashSet<>();

        private StepUsageFinder(Project project) {
            this.project = project;
        }

        @Override
        public boolean processFile(VirtualFile virtualFile) {
            if (virtualFile.isDirectory() || !virtualFile.getFileType().getDefaultExtension().equals("story")) {
                return true;
            }

            if (PsiManager.getInstance(project).findFile(virtualFile) instanceof StoryFile storyFile) {
                stepUsages.addAll(storyFile.getSteps());
            }
            return true;
        }

        public Set<JBehaveStep> getStepUsages() {
            return stepUsages;
        }
    }
}
