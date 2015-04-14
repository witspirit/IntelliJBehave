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

public class JBehaveUriIdentifierImpl extends ASTWrapperPsiElement implements JBehaveUriIdentifier {

  public JBehaveUriIdentifierImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitUriIdentifier(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JBehaveIpAddress getIpAddress() {
    return findChildByClass(JBehaveIpAddress.class);
  }

  @Override
  @NotNull
  public List<JBehaveUriWord> getUriWordList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveUriWord.class);
  }

}
