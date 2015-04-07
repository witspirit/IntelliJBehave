// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaElement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaKey;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaValue;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_NEWLINE;

public class JBehaveMetaElementImpl extends ASTWrapperPsiElement implements JBehaveMetaElement {

    public JBehaveMetaElementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitMetaElement(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public JBehaveMetaKey getMetaKey() {
        return findNotNullChildByClass(JBehaveMetaKey.class);
    }

    @Override
    @Nullable
    public JBehaveMetaValue getMetaValue() {
        return findChildByClass(JBehaveMetaValue.class);
    }

    @Override
    @Nullable
    public PsiElement getTokenNewline() {
        return findChildByType(JB_TOKEN_NEWLINE);
    }

}
