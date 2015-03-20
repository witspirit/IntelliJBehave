// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

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
