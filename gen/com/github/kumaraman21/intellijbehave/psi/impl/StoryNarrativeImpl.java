// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryNarrative;
import com.github.kumaraman21.intellijbehave.psi.StoryNarrativeText;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_NEWLINE;

public class StoryNarrativeImpl extends ASTWrapperPsiElement implements StoryNarrative {

  public StoryNarrativeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitNarrative(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryNarrativeText getNarrativeText() {
    return findChildByClass(StoryNarrativeText.class);
  }

  @Override
  @Nullable
  public PsiElement getTokenNewline() {
    return findChildByType(STORY_TOKEN_NEWLINE);
  }

}
