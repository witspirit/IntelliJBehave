// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public interface StoryMetaElement extends PsiElement {

  @NotNull
  StoryMetaKey getMetaKey();

  @NotNull
  StoryMetaValue getMetaValue();

  @NotNull
  PsiElement getTokenSpace();

}
