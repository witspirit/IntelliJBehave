// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JBehaveStory extends PsiElement {

  @Nullable
  JBehaveDescription getDescription();

  @Nullable
  JBehaveGivenStories getGivenStories();

  @Nullable
  JBehaveLifecycle getLifecycle();

  @Nullable
  JBehaveMetaStatement getMetaStatement();

  @Nullable
  JBehaveNarrative getNarrative();

  @NotNull
  List<JBehaveScenario> getScenarioList();

}
