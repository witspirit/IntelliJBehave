// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStoryScenario;
import com.github.kumaraman21.intellijbehave.psi.*;

public class StoryScenarioImpl extends JBehaveStoryScenario implements StoryScenario {

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
