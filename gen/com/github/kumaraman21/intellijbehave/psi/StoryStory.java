// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface StoryStory extends PsiElement {

  @Nullable
  StoryDescription getDescription();

  @Nullable
  StoryGivenStories getGivenStories();

  @Nullable
  StoryLifecycle getLifecycle();

  @Nullable
  StoryMetaStatement getMetaStatement();

  @Nullable
  StoryNarrative getNarrative();

  @NotNull
  List<StoryScenario> getScenarioList();

}
