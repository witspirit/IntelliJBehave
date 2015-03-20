// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.kumaraman21.intellijbehave.psi.*;

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
