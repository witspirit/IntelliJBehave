// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryMultiTextLine;
import com.github.kumaraman21.intellijbehave.psi.StoryNarrativeText;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public class StoryNarrativeTextImpl extends ASTWrapperPsiElement implements StoryNarrativeText {

  public StoryNarrativeTextImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitNarrativeText(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public StoryMultiTextLine getMultiTextLine() {
    return findNotNullChildByClass(StoryMultiTextLine.class);
  }

}
