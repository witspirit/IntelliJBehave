// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryStepImpl extends ASTWrapperPsiElement implements StoryStep {

  public StoryStepImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStep(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStepAnd getStepAnd() {
    return findChildByClass(StoryStepAnd.class);
  }

  @Override
  @Nullable
  public StoryStepComment getStepComment() {
    return findChildByClass(StoryStepComment.class);
  }

  @Override
  @Nullable
  public StoryStepGiven getStepGiven() {
    return findChildByClass(StoryStepGiven.class);
  }

  @Override
  @Nullable
  public StoryStepThen getStepThen() {
    return findChildByClass(StoryStepThen.class);
  }

  @Override
  @Nullable
  public StoryStepWhen getStepWhen() {
    return findChildByClass(StoryStepWhen.class);
  }

}
