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

public class JBehaveLifecycleImpl extends ParserRule implements JBehaveLifecycle {

  public JBehaveLifecycleImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitLifecycle(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JBehaveLifecycleAfter getLifecycleAfter() {
    return findChildByClass(JBehaveLifecycleAfter.class);
  }

  @Override
  @Nullable
  public JBehaveLifecycleBefore getLifecycleBefore() {
    return findChildByClass(JBehaveLifecycleBefore.class);
  }

}
