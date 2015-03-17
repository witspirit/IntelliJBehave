// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryStepArgument;
import com.github.kumaraman21.intellijbehave.psi.StoryStepLine;
import com.github.kumaraman21.intellijbehave.psi.StoryStepPostParameter;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_NEWLINE;

public class StoryStepArgumentImpl extends ASTWrapperPsiElement implements StoryStepArgument {

  public StoryStepArgumentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStepArgument(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public StoryStepLine getStepLine() {
    return findNotNullChildByClass(StoryStepLine.class);
  }

  @Override
  @Nullable
  public StoryStepPostParameter getStepPostParameter() {
    return findChildByClass(StoryStepPostParameter.class);
  }

  @Override
  @Nullable
  public PsiElement getTokenNewline() {
    return findChildByType(STORY_TOKEN_NEWLINE);
  }

}
