// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveLifecycle;
import com.github.kumaraman21.intellijbehave.psi.JBehaveLifecycleAfter;
import com.github.kumaraman21.intellijbehave.psi.JBehaveLifecycleBefore;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JBehaveLifecycleImpl extends ASTWrapperPsiElement implements JBehaveLifecycle {

    public JBehaveLifecycleImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitLifecycle(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public JBehaveLifecycleAfter getLifecycleAfter() {
        return findChildByClass(JBehaveLifecycleAfter.class);
    }

    @Override
    @Nullable
    public JBehaveLifecycleBefore getLifecycleBefore() {
        return findChildByClass(JBehaveLifecycleBefore.class);
    }

}
