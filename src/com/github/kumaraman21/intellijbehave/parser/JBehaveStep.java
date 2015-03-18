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

import com.github.kumaraman21.intellijbehave.peg.JBehaveRule;
import com.github.kumaraman21.intellijbehave.peg.StoryPegParserDefinition;
import com.github.kumaraman21.intellijbehave.psi.StoryStepAnd;
import com.github.kumaraman21.intellijbehave.psi.StoryStepGiven;
import com.github.kumaraman21.intellijbehave.psi.StoryStepThen;
import com.github.kumaraman21.intellijbehave.psi.StoryStepWhen;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.util.IncorrectOperationException;
import org.jbehave.core.steps.StepType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.apache.commons.lang.StringUtils.trim;

public class JBehaveStep extends JBehaveRule implements PsiNamedElement {
    public JBehaveStep(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @NotNull
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    public StepType getStepType() {
        if (this instanceof StoryStepWhen) return StepType.WHEN;
        if (this instanceof StoryStepThen) return StepType.THEN;
        if (this instanceof StoryStepGiven) return StepType.GIVEN;
        if (this instanceof StoryStepAnd) {
            PsiElement prevSibling = getParent().getPrevSibling().getFirstChild();
            if(prevSibling instanceof JBehaveStep){
                JBehaveStep sibling= (JBehaveStep) prevSibling;
                StepType stepType = sibling.getStepType();
                return stepType;
            }
            return StepType.AND;
        }
        //return stepType;
        return StepType.IGNORABLE;
    }

    @Nullable
    public ASTNode getKeyword() {
        //return getNode().findChildByType(StoryTokenType.STEP_TYPES);
        return getNode().findChildByType(StoryPegParserDefinition.STEP_TYPES);
    }

    public String getStepText() {
        int offset = getStepTextOffset();
        final String text = String.format("%s%s", getFirstChild().getText(), getLastChild().getFirstChild().getText());

        if (offset <= 0 || offset >= text.length()) {
            return trim(text);
        } else {
            return trim(text.substring(offset));
        }
    }

    @Nullable
    public String getActualStepPrefix() {
        ASTNode keyword = getKeyword();
        if (keyword == null) { // that's weird!
            return null;
        }
        return keyword.getText();
    }

    public int getStepTextOffset() {
        String stepPrefix = getActualStepPrefix();
        return stepPrefix != null ? stepPrefix.length() + 1 : 0;
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return this;
    }
}
