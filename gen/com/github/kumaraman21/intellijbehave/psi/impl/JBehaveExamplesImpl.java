// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveExamples;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStoryPath;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTable;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JBehaveExamplesImpl extends ASTWrapperPsiElement implements JBehaveExamples {

    public JBehaveExamplesImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitExamples(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public JBehaveStoryPath getStoryPath() {
        return findChildByClass(JBehaveStoryPath.class);
    }

    @Override
    @Nullable
    public JBehaveTable getTable() {
        return findChildByClass(JBehaveTable.class);
    }

}
