// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryStoryPath;
import com.github.kumaraman21.intellijbehave.psi.StoryStoryPaths;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoryStoryPathsImpl extends ASTWrapperPsiElement implements StoryStoryPaths {

  public StoryStoryPathsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitStoryPaths(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryStoryPath> getStoryPathList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryStoryPath.class);
  }

}
