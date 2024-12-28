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
package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.highlighter.StoryTokenType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.apache.commons.lang3.StringUtils.trim;

public class JBehaveStep extends ASTWrapperPsiElement {
    private final StepType stepType;

    public JBehaveStep(@NotNull ASTNode node, StepType stepType) {
        super(node);
        this.stepType = stepType;
    }

    @Override
    public PsiReference @NotNull [] getReferences() {
        return ReadAction.compute(() -> ReferenceProvidersRegistry.getReferencesFromProviders(this));
    }

    public StepType getStepType() {
        return stepType;
    }

    @Nullable
    public ASTNode getKeyword() {
        return getNode().findChildByType(StoryTokenType.STEP_TYPES);
    }

    /**
     * Returns the step text without the step type. For example:
     * for the step {@code Given I opened the homepage} it returns {@code I opened the homepage}.
     */
    public String getStepText() {
        int offset = getStepTextOffset();
        String text = getText();

        if (offset <= 0 || offset >= text.length()) {
            return trim(text);
        } else {
            return trim(text.substring(offset));
        }
    }

    /**
     * Returns the step type keyword, for example {@code Given} for the step
     * {@code Given I opened the homepage}.
     */
    @Nullable("When the keyword is null.")
    public String getActualStepPrefix() {
        ASTNode keyword = getKeyword();
        if (keyword == null) { // that's weird!
            return null;
        }
        return keyword.getText();
    }

    /**
     * Returns the off set the step text after the step type keyword. For example:
     * for {@code Given I opened the homepage}, it would return 6.
     * <p>
     * Returns 0 when the step type prefix is null.
     */
    public int getStepTextOffset() {
        String stepPrefix = getActualStepPrefix();
        return stepPrefix != null ? stepPrefix.length() + 1 : 0;
    }
}
