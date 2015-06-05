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

public class JBehaveUriWordImpl extends ASTWrapperPsiElement implements JBehaveUriWord {

  public JBehaveUriWordImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitUriWord(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getTokenInject() {
    return findChildByType(JB_TOKEN_INJECT);
  }

  @Override
  @Nullable
  public PsiElement getTokenUserInject() {
    return findChildByType(JB_TOKEN_USER_INJECT);
  }

  @Override
  @Nullable
  public PsiElement getTokenWord() {
    return findChildByType(JB_TOKEN_WORD);
  }

}
