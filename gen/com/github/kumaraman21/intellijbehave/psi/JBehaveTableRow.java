// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface JBehaveTableRow extends PsiElement {

    @NotNull
    List<JBehaveTableCell> getTableCellList();

    @NotNull
    List<JBehaveTableCellEmpty> getTableCellEmptyList();

}
