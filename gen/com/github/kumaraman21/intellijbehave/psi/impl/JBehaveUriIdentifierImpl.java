// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveIpAddress;
import com.github.kumaraman21.intellijbehave.psi.JBehaveUriIdentifier;
import com.github.kumaraman21.intellijbehave.psi.JBehaveUriWord;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JBehaveUriIdentifierImpl extends ASTWrapperPsiElement implements JBehaveUriIdentifier {

    public JBehaveUriIdentifierImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitUriIdentifier(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public JBehaveIpAddress getIpAddress() {
        return findChildByClass(JBehaveIpAddress.class);
    }

    @Override
    @NotNull
    public List<JBehaveUriWord> getUriWordList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveUriWord.class);
    }

}
