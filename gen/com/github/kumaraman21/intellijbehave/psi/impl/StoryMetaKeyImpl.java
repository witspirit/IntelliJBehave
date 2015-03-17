// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryMetaKey;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_WORD;

public class StoryMetaKeyImpl extends ASTWrapperPsiElement implements StoryMetaKey {

  public StoryMetaKeyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitMetaKey(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getTokenWord() {
    return findNotNullChildByType(STORY_TOKEN_WORD);
  }

}
