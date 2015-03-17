// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryTable;
import com.github.kumaraman21.intellijbehave.psi.StoryTableRow;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoryTableImpl extends ASTWrapperPsiElement implements StoryTable {

  public StoryTableImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitTable(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryTableRow> getTableRowList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryTableRow.class);
  }

}
