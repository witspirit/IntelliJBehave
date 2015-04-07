// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveIpAddress;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_IP;

public class JBehaveIpAddressImpl extends ASTWrapperPsiElement implements JBehaveIpAddress {

    public JBehaveIpAddressImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitIpAddress(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getTokenIp() {
        return findChildByType(JB_TOKEN_IP);
    }

}
