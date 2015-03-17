// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryWordImpl extends ASTWrapperPsiElement implements StoryWord {

  public StoryWordImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitWord(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryAlnum getAlnum() {
    return findChildByClass(StoryAlnum.class);
  }

  @Override
  @Nullable
  public StoryInject getInject() {
    return findChildByClass(StoryInject.class);
  }

  @Override
  @Nullable
  public StoryIpAddress getIpAddress() {
    return findChildByClass(StoryIpAddress.class);
  }

  @Override
  @Nullable
  public StoryUri getUri() {
    return findChildByClass(StoryUri.class);
  }

  @Override
  @Nullable
  public StoryUserInject getUserInject() {
    return findChildByClass(StoryUserInject.class);
  }

}
