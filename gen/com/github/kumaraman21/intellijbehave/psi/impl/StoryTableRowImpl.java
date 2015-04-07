// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.kumaraman21.intellijbehave.psi.*;

public class StoryTableRowImpl extends ASTWrapperPsiElement implements StoryTableRow {

  public StoryTableRowImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitTableRow(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryTableCell> getTableCellList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryTableCell.class);
  }

  @Override
  @NotNull
  public List<StoryTableCellEmpty> getTableCellEmptyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryTableCellEmpty.class);
  }

  @Override
  @NotNull
  public PsiElement getTokenNewline() {
    return findNotNullChildByType(JB_TOKEN_NEWLINE);
  }

}
