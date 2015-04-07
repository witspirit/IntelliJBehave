// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveStepPostParameter;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStoryPaths;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTable;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JBehaveStepPostParameterImpl extends ASTWrapperPsiElement implements JBehaveStepPostParameter {

    public JBehaveStepPostParameterImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitStepPostParameter(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public JBehaveStoryPaths getStoryPaths() {
        return findChildByClass(JBehaveStoryPaths.class);
    }

    @Override
    @Nullable
    public JBehaveTable getTable() {
        return findChildByClass(JBehaveTable.class);
    }

}
