// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface StoryTableRow extends PsiElement {

  @NotNull
  List<StoryTableCell> getTableCellList();

  @NotNull
  List<StoryTableCellEmpty> getTableCellEmptyList();

  @Nullable
  PsiElement getTokenNewline();

}
