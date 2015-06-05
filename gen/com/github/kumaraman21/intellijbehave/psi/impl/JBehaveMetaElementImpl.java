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

public class JBehaveMetaElementImpl extends ASTWrapperPsiElement implements JBehaveMetaElement {

  public JBehaveMetaElementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitMetaElement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JBehaveMetaKey getMetaKey() {
    return findChildByClass(JBehaveMetaKey.class);
  }

  @Override
  @Nullable
  public JBehaveMetaValue getMetaValue() {
    return findChildByClass(JBehaveMetaValue.class);
  }

  @Override
  @Nullable
  public PsiElement getTokenSpace() {
    return findChildByType(JB_TOKEN_SPACE);
  }

}
