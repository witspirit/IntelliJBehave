// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.*;
import com.github.kumaraman21.intellijbehave.peg.JBehaveRule;
import com.github.kumaraman21.intellijbehave.psi.*;

public class StoryScenarioTitleImpl extends JBehaveRule implements StoryScenarioTitle {

  public StoryScenarioTitleImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof StoryVisitor) ((StoryVisitor)visitor).visitScenarioTitle(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<StoryIpAddress> getIpAddressList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryIpAddress.class);
  }

  @Override
  @NotNull
  public List<StoryStoryPath> getStoryPathList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryStoryPath.class);
  }

  @Override
  @NotNull
  public List<StoryUri> getUriList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, StoryUri.class);
  }

}
