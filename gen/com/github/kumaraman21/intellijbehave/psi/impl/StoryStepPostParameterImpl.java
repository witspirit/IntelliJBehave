// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.kumaraman21.intellijbehave.psi.*;

public class StoryStepPostParameterImpl extends ASTWrapperPsiElement implements StoryStepPostParameter {

  public StoryStepPostParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStepPostParameter(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public StoryStoryPaths getStoryPaths() {
    return findChildByClass(StoryStoryPaths.class);
  }

  @Override
  @Nullable
  public StoryTable getTable() {
    return findChildByClass(StoryTable.class);
  }

}
