// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveTable;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTableRow;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JBehaveTableImpl extends ASTWrapperPsiElement implements JBehaveTable {

    public JBehaveTableImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitTable(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<JBehaveTableRow> getTableRowList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveTableRow.class);
    }

}
