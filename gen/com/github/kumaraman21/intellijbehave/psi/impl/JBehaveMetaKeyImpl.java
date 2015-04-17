// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.ParserRule;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaKey;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_MKEY;

public class JBehaveMetaKeyImpl extends ParserRule implements JBehaveMetaKey {

  public JBehaveMetaKeyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitMetaKey(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getTokenMkey() {
    return findNotNullChildByType(JB_TOKEN_MKEY);
  }

}
