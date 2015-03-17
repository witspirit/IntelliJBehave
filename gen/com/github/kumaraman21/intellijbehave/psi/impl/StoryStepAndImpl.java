// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.psi.StoryStepAnd;
import com.github.kumaraman21.intellijbehave.psi.StoryStepArgument;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryStepAndImpl extends JBehaveStep implements StoryStepAnd {

  public StoryStepAndImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStepAnd(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStepArgument getStepArgument() {
    return findChildByClass(StoryStepArgument.class);
  }

}
