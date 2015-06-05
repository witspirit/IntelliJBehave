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

public class JBehaveLifecycleBeforeImpl extends ASTWrapperPsiElement implements JBehaveLifecycleBefore {

  public JBehaveLifecycleBeforeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitLifecycleBefore(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<JBehaveStep> getStepList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveStep.class);
  }

}
