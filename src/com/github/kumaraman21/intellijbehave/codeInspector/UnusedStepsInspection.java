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

import com.github.kumaraman21.intellijbehave.parser.StepPsiElement;
import com.github.kumaraman21.intellijbehave.parser.StoryFileImpl;
import com.intellij.codeInspection.BaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jbehave.core.annotations.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class UnusedStepsInspection extends BaseJavaLocalInspectionTool {

    private static final List<String> JBEHAVE_ANNOTATIONS = newArrayList(
            Given.class.getName(),
            When.class.getName(),
            Then.class.getName(),
            Alias.class.getName(),
            Aliases.class.getName());

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "JBehave";
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Unused step declaration";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UnusedStepDeclaration";
    }

    @Override
    public String getStaticDescription() {
        return super.getStaticDescription();
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitAnnotation(PsiAnnotation annotation) {

                if (!JBEHAVE_ANNOTATIONS.contains(annotation.getQualifiedName())) {
                    return;
                }

                Project project = annotation.getProject();
                StepUsageFinder stepUsageFinder = new StepUsageFinder(project);
                ProjectRootManager.getInstance(project).getFileIndex().iterateContent(stepUsageFinder);
                Set<StepPsiElement> stepUsages = stepUsageFinder.getStepUsages();

                for (StepPsiElement stepUsage : stepUsages) {
                    if (stepUsage.getReference().resolve() == annotation) {
                        return;
                    }
                }

                holder.registerProblem(annotation, "Step <code>#ref</code> is never used");
            }
        };
    }

    private static class StepUsageFinder implements ContentIterator {
        private Project project;
        private Set<StepPsiElement> stepUsages = newHashSet();

        private StepUsageFinder(Project project) {
            this.project = project;
        }

        @Override
        public boolean processFile(VirtualFile virtualFile) {

            if (virtualFile.isDirectory() || !virtualFile.getFileType().getDefaultExtension().equals("story")) {
                return true;
            }

            PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
            if (psiFile instanceof StoryFileImpl) {
                List<StepPsiElement> stepPsiElements = ((StoryFileImpl) psiFile).getSteps();
                stepUsages.addAll(stepPsiElements);
            }
            return true;
        }

        public Set<StepPsiElement> getStepUsages() {
            return stepUsages;
        }
    }
}
