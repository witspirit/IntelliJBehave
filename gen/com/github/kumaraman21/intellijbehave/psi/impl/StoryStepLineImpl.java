// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryLine;
import com.github.kumaraman21.intellijbehave.psi.StoryStepLine;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public class StoryStepLineImpl extends ASTWrapperPsiElement implements StoryStepLine {

  public StoryStepLineImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStepLine(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public StoryLine getLine() {
    return findNotNullChildByClass(StoryLine.class);
  }

}
