// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryLifecycle;
import com.github.kumaraman21.intellijbehave.psi.StoryLifecycleAfter;
import com.github.kumaraman21.intellijbehave.psi.StoryLifecycleBefore;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoryLifecycleImpl extends ASTWrapperPsiElement implements StoryLifecycle {

  public StoryLifecycleImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitLifecycle(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryLifecycleAfter getLifecycleAfter() {
    return findChildByClass(StoryLifecycleAfter.class);
  }

  @Override
  @Nullable
  public StoryLifecycleBefore getLifecycleBefore() {
    return findChildByClass(StoryLifecycleBefore.class);
  }

}
