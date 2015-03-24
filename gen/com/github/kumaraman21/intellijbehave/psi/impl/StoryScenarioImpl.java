// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.kumaraman21.intellijbehave.psi.*;

public class StoryScenarioImpl extends ASTWrapperPsiElement implements StoryScenario {

  public StoryScenarioImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitScenario(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryExamples getExamples() {
    return findChildByClass(StoryExamples.class);
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

  @Override
  @NotNull
  public List<StoryStepComment> getStepCommentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryStepComment.class);
  }

  @Override
  @Nullable
  public PsiElement getTokenNewline() {
    return findChildByType(STORY_TOKEN_NEWLINE);
  }

}
