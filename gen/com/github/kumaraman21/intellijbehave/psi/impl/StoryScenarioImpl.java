// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.peg.PegStoryScenario;
import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StoryScenarioImpl extends PegStoryScenario implements StoryScenario {

  public StoryScenarioImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitScenario(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryExamples> getExamplesList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryExamples.class);
  }

  @Override
  @Nullable
  public StoryGivenStories getGivenStories() {
    return findChildByClass(StoryGivenStories.class);
  }

  @Override
  @Nullable
  public StoryMetaStatement getMetaStatement() {
    return findChildByClass(StoryMetaStatement.class);
  }

  @Override
  @Nullable
  public StoryScenarioTitle getScenarioTitle() {
    return findChildByClass(StoryScenarioTitle.class);
  }

  @Override
  @NotNull
  public List<StoryStep> getStepList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryStep.class);
  }

}
