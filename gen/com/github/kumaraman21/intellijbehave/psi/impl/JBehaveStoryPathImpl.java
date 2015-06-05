// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.github.kumaraman21.intellijbehave.parser.StoryPath;
import com.github.kumaraman21.intellijbehave.psi.*;

public class JBehaveStoryPathImpl extends StoryPath implements JBehaveStoryPath {

  public JBehaveStoryPathImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitStoryPath(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getTokenPath() {
    return findNotNullChildByType(JB_TOKEN_PATH);
  }

}
