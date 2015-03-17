// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface StoryStep extends PsiElement {

  @Nullable
  StoryStepAnd getStepAnd();

  @Nullable
  StoryStepComment getStepComment();

  @Nullable
  StoryStepGiven getStepGiven();

  @Nullable
  StoryStepThen getStepThen();

  @Nullable
  StoryStepWhen getStepWhen();

}
