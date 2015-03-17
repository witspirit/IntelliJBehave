// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryIpAddress;
import com.github.kumaraman21.intellijbehave.psi.StoryUri;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

public class StoryUriImpl extends ASTWrapperPsiElement implements StoryUri {

  public StoryUriImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitUri(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public StoryIpAddress getIpAddress() {
    return findNotNullChildByClass(StoryIpAddress.class);
  }

}
