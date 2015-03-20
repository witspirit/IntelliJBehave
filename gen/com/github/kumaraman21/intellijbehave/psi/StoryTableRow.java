// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface StoryTableRow extends PsiElement {

  @NotNull
  List<StoryTableCell> getTableCellList();

  @NotNull
  List<StoryTableCellEmpty> getTableCellEmptyList();

  @Nullable
  PsiElement getTokenNewline();

}
