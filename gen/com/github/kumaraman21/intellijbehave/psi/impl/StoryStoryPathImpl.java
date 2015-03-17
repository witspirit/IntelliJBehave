// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.peg.PegStoryPath;
import com.github.kumaraman21.intellijbehave.psi.StoryStoryPath;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_PATH;

public class StoryStoryPathImpl extends PegStoryPath implements StoryStoryPath {

  public StoryStoryPathImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStoryPath(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getTokenPath() {
    return findNotNullChildByType(STORY_TOKEN_PATH);
  }

}
