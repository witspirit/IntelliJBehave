// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JBehaveScenario extends PsiElement {

  @NotNull
  List<JBehaveExamples> getExamplesList();

  @Nullable
  JBehaveGivenStories getGivenStories();

  @Nullable
  JBehaveMetaStatement getMetaStatement();

  @Nullable
  JBehaveScenarioTitle getScenarioTitle();

  @NotNull
  List<JBehaveStep> getStepList();

}
