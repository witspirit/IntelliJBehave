// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.JBehaveTableCell;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTableCellEmpty;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTableRow;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_NEWLINE;

public class JBehaveTableRowImpl extends ASTWrapperPsiElement implements JBehaveTableRow {

    public JBehaveTableRowImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitTableRow(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<JBehaveTableCell> getTableCellList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveTableCell.class);
    }

    @Override
    @NotNull
    public List<JBehaveTableCellEmpty> getTableCellEmptyList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveTableCellEmpty.class);
    }

    @Override
    @NotNull
    public PsiElement getTokenNewline() {
        return findNotNullChildByType(JB_TOKEN_NEWLINE);
    }

}
