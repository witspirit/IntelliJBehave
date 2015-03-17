// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.psi.StoryStepArgument;
import com.github.kumaraman21.intellijbehave.psi.StoryStepThen;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryStepThenImpl extends JBehaveStep implements StoryStepThen {

  public StoryStepThenImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStepThen(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStepArgument getStepArgument() {
    return findChildByClass(StoryStepArgument.class);
  }

}
