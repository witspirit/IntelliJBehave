// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryLine;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.github.kumaraman21.intellijbehave.psi.StoryWord;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoryLineImpl extends ASTWrapperPsiElement implements StoryLine {

  public StoryLineImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitLine(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryWord> getWordList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryWord.class);
  }

}
