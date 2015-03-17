// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryTableCell;
import com.github.kumaraman21.intellijbehave.psi.StoryTableCellEmpty;
import com.github.kumaraman21.intellijbehave.psi.StoryTableRow;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_NEWLINE;

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
  @Nullable
  public PsiElement getTokenNewline() {
    return findChildByType(STORY_TOKEN_NEWLINE);
  }

}
