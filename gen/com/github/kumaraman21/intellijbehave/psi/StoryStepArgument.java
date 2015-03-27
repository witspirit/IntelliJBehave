// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StoryStepArgument extends PsiElement {

  @NotNull
  StoryStepLine getStepLine();

  @Nullable
  StoryStepPostParameter getStepPostParameter();

  @Nullable
  PsiElement getTokenSpace();

}
