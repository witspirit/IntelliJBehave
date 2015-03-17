// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryExamples;
import com.github.kumaraman21.intellijbehave.psi.StoryStoryPath;
import com.github.kumaraman21.intellijbehave.psi.StoryTable;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryExamplesImpl extends ASTWrapperPsiElement implements StoryExamples {

  public StoryExamplesImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitExamples(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStoryPath getStoryPath() {
    return findChildByClass(StoryStoryPath.class);
  }

  @Override
  @Nullable
  public StoryTable getTable() {
    return findChildByClass(StoryTable.class);
  }

}
