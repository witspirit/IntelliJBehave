// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.StoryMetaElement;
import com.github.kumaraman21.intellijbehave.psi.StoryMetaStatement;
import com.github.kumaraman21.intellijbehave.psi.StoryVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.STORY_TOKEN_SPACE;

public class StoryMetaStatementImpl extends ASTWrapperPsiElement implements StoryMetaStatement {

  public StoryMetaStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitMetaStatement(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryMetaElement> getMetaElementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryMetaElement.class);
  }

  @Override
  @Nullable
  public PsiElement getTokenSpace() {
    return findChildByType(STORY_TOKEN_SPACE);
  }

}
