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

import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class StepDefinitionIterator implements ContentIterator {

    private final StepDefinitionAnnotationConverter stepDefinitionAnnotationConverter = new StepDefinitionAnnotationConverter();
    private StepType stepType;
  private PsiElement storyRef;

  public StepDefinitionIterator(@Nullable StepType stepType, PsiElement storyRef) {
        this.stepType = stepType;
        this.storyRef = storyRef;
    }

    @Override
    public boolean processFile(VirtualFile virtualFile) {

        PsiFile psiFile = PsiManager.getInstance(storyRef.getProject()).findFile(virtualFile);
        if (psiFile instanceof PsiClassOwner) {
            // System.out.println("Virtual File that is a PsiClassOwner: "+virtualFile);

            PsiClass[] psiClasses = ((PsiClassOwner) psiFile).getClasses();

            for (PsiClass psiClass : psiClasses) {
                PsiMethod[] methods = psiClass.getMethods();

                for (PsiMethod method : methods) {
                    PsiAnnotation[] annotations = method.getModifierList().getApplicableAnnotations();
                    Set<StepDefinitionAnnotation> stepDefinitionAnnotations = stepDefinitionAnnotationConverter.convertFrom(annotations);

                    for (StepDefinitionAnnotation stepDefinitionAnnotation : stepDefinitionAnnotations) {
                        if (stepType == null || stepDefinitionAnnotation.getStepType().equals(stepType)) {

                            boolean shouldContinue = processStepDefinition(stepDefinitionAnnotation);
                            if (!shouldContinue) {
                                return shouldContinue;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public abstract boolean processStepDefinition(StepDefinitionAnnotation stepDefinitionAnnotation);

}
