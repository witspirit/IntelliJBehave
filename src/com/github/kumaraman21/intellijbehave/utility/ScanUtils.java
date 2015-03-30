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
package com.github.kumaraman21.intellijbehave.utility;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.resolver.StepDefinitionIterator;
import com.github.kumaraman21.intellijbehave.service.JBehaveStepsIndex;
import com.github.kumaraman21.intellijbehave.service.JavaStepDefinition;
import com.intellij.psi.PsiElement;

import java.util.Collection;

public class ScanUtils {

    public static boolean iterateInContextOf(PsiElement storyRef, StepDefinitionIterator iterator) {
        TokenMap allStepDefinitionsByType = JBehaveStepsIndex.getInstance(
                storyRef.getProject()).findAllStepDefinitionsByType((JBehaveStep) storyRef);
        String text = storyRef.getText();
        Collection<JavaStepDefinition> stepDefinitions = allStepDefinitionsByType.getConcerned(text);
        for (JavaStepDefinition stepDefinition : stepDefinitions) {
            iterator.processStepDefinition(stepDefinition);
        }
        return true;
    }


}
