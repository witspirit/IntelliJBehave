// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.github.kumaraman21.intellijbehave.psi.JBehaveIpAddress;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStepLine;
import com.github.kumaraman21.intellijbehave.psi.JBehaveUri;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JBehaveStepLineImpl extends ParserRule implements JBehaveStepLine {

  public JBehaveStepLineImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitStepLine(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JBehaveIpAddress> getIpAddressList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveIpAddress.class);
  }

  @Override
  @NotNull
  public List<JBehaveUri> getUriList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveUri.class);
  }

}
