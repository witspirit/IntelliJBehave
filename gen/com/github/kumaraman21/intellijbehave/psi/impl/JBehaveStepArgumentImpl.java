// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveStepArgument;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStepLine;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStepPostParameter;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JBehaveStepArgumentImpl extends ASTWrapperPsiElement implements JBehaveStepArgument {

    public JBehaveStepArgumentImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitStepArgument(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public JBehaveStepLine getStepLine() {
        return findNotNullChildByClass(JBehaveStepLine.class);
    }

    @Override
    @Nullable
    public JBehaveStepPostParameter getStepPostParameter() {
        return findChildByClass(JBehaveStepPostParameter.class);
    }

}
