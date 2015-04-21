// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.github.kumaraman21.intellijbehave.psi.*;

public class JBehaveUriImpl extends ParserRule implements JBehaveUri {

  public JBehaveUriImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitUri(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JBehaveIpAddress> getIpAddressList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveIpAddress.class);
  }

  @Override
  @NotNull
  public List<JBehaveStoryPath> getStoryPathList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveStoryPath.class);
  }

  @Override
  @NotNull
  public List<JBehaveUri> getUriList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveUri.class);
  }

  @Override
  @NotNull
  public JBehaveUriIdentifier getUriIdentifier() {
    return findNotNullChildByClass(JBehaveUriIdentifier.class);
  }

}
