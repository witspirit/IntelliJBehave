// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface StoryStepArgument extends PsiElement {

  @NotNull
  StoryStepLine getStepLine();

  @Nullable
  StoryStepPostParameter getStepPostParameter();

  @Nullable
  PsiElement getTokenNewline();

}
