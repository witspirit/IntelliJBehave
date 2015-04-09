// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.Narrative;
import com.github.kumaraman21.intellijbehave.psi.JBehaveNarrative;
import com.github.kumaraman21.intellijbehave.psi.JBehaveNarrativeText;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_NEWLINE;

public class JBehaveNarrativeImpl extends Narrative implements JBehaveNarrative {

    public JBehaveNarrativeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitNarrative(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public JBehaveNarrativeText getNarrativeText() {
        return findChildByClass(JBehaveNarrativeText.class);
    }

    @Override
    @Nullable
    public PsiElement getTokenNewline() {
        return findChildByType(JB_TOKEN_NEWLINE);
    }

}
