// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.psi.*;

public class JBehaveStepImpl extends ScenarioStep implements JBehaveStep {

  public JBehaveStepImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor)visitor).visitStep(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JBehaveStepArgument getStepArgument() {
    return findChildByClass(JBehaveStepArgument.class);
  }

  @Override
  @NotNull
  public JBehaveStepPar getStepPar() {
    return findNotNullChildByClass(JBehaveStepPar.class);
  }

}
