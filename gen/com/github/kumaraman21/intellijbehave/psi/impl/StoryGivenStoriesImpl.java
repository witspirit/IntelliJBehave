// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryGivenStories;
import com.github.kumaraman21.intellijbehave.psi.StoryStoryPaths;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_NEWLINE;

public class StoryGivenStoriesImpl extends ASTWrapperPsiElement implements StoryGivenStories {

  public StoryGivenStoriesImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitGivenStories(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStoryPaths getStoryPaths() {
    return findChildByClass(StoryStoryPaths.class);
  }

  @Override
  @Nullable
  public PsiElement getTokenNewline() {
    return findChildByType(STORY_TOKEN_NEWLINE);
  }

}
