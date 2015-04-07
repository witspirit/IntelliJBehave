// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.kumaraman21.intellijbehave.psi.*;

public class StoryStoryImpl extends ASTWrapperPsiElement implements StoryStory {

  public StoryStoryImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStory(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryDescription getDescription() {
    return findChildByClass(StoryDescription.class);
  }

  @Override
  @Nullable
  public StoryGivenStories getGivenStories() {
    return findChildByClass(StoryGivenStories.class);
  }

  @Override
  @Nullable
  public StoryLifecycle getLifecycle() {
    return findChildByClass(StoryLifecycle.class);
  }

  @Override
  @Nullable
  public StoryMetaStatement getMetaStatement() {
    return findChildByClass(StoryMetaStatement.class);
  }

  @Override
  @Nullable
  public StoryNarrative getNarrative() {
    return findChildByClass(StoryNarrative.class);
  }

  @Override
  @NotNull
  public List<StoryScenario> getScenarioList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryScenario.class);
  }

}
