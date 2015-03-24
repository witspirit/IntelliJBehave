// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface StoryScenario extends PsiElement {

  @Nullable
  StoryExamples getExamples();

  @Nullable
  StoryGivenStories getGivenStories();

  @Nullable
  StoryMetaStatement getMetaStatement();

  @Nullable
  StoryScenarioTitle getScenarioTitle();

  @NotNull
  List<StoryStep> getStepList();

  @NotNull
  List<StoryStepComment> getStepCommentList();

  @Nullable
  PsiElement getTokenNewline();

}
