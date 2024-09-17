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

import com.github.kumaraman21.intellijbehave.kotlin.KotlinConfigKt;
import com.github.kumaraman21.intellijbehave.kotlin.support.services.KotlinPsiClassesHandler;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassOwner;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Processes VirtualFiles to see if step annotations match the pre-configured step type.
 */
public abstract class StepDefinitionIterator implements ContentIterator {
    private final StepType stepType;
    private final Project project;

    public StepDefinitionIterator(@Nullable StepType stepType, Project project) {
        this.stepType = stepType;
        this.project = project;
    }

    public StepType getStepType() {
        return stepType;
    }

    @Override
    public boolean processFile(@NotNull VirtualFile virtualFile) {
        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);

        if (psiFile instanceof PsiClassOwner psiClassOwner) {
            for (PsiClass psiClass : getPsiClasses(psiFile, psiClassOwner)) {
                for (PsiMethod method : psiClass.getMethods()) {
                    PsiAnnotation[] annotations = method.getModifierList().getApplicableAnnotations();

                    for (StepDefinitionAnnotation stepDefinitionAnnotation : StepDefinitionAnnotationConverter.convertFrom(annotations)) {
                        if ((stepType == null || Objects.equals(stepType, stepDefinitionAnnotation.stepType()))
                            && !processStepDefinition(stepDefinitionAnnotation)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Returns the PSI classes from the provided file and class owner.
     *
     * @param psiFile       the Java or Kotlin step definitions file
     * @param psiClassOwner {@code psiFile} as a {@link PsiClassOwner}
     * @return the classes contained by the file
     */
    private static PsiClass[] getPsiClasses(PsiFile psiFile, PsiClassOwner psiClassOwner) {
        PsiClass[] psiClasses = null;
        if (KotlinConfigKt.getPluginIsEnabled()) {
            psiClasses = KotlinPsiClassesHandler.getPsiClasses(psiFile);
        }

        return psiClasses != null ? psiClasses : psiClassOwner.getClasses();
    }

    public abstract boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation);

}
