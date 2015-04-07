// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.Scenario;
import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JBehaveScenarioImpl extends Scenario implements JBehaveScenario {

    public JBehaveScenarioImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitScenario(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<JBehaveExamples> getExamplesList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveExamples.class);
    }

    @Override
    @Nullable
    public JBehaveGivenStories getGivenStories() {
        return findChildByClass(JBehaveGivenStories.class);
    }

    @Override
    @Nullable
    public JBehaveMetaStatement getMetaStatement() {
        return findChildByClass(JBehaveMetaStatement.class);
    }

    @Override
    @Nullable
    public JBehaveScenarioTitle getScenarioTitle() {
        return findChildByClass(JBehaveScenarioTitle.class);
    }

    @Override
    @NotNull
    public List<JBehaveStep> getStepList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveStep.class);
    }

}
