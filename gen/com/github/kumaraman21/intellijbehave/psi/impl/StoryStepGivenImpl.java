// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.psi.StoryStepArgument;
import com.github.kumaraman21.intellijbehave.psi.StoryStepGiven;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryStepGivenImpl extends JBehaveStep implements StoryStepGiven {

  public StoryStepGivenImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStepGiven(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStepArgument getStepArgument() {
    return findChildByClass(StoryStepArgument.class);
  }

}
