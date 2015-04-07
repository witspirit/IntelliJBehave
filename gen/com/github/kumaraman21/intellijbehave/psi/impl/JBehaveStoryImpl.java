// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JBehaveStoryImpl extends ASTWrapperPsiElement implements JBehaveStory {

    public JBehaveStoryImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitStory(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public JBehaveDescription getDescription() {
        return findChildByClass(JBehaveDescription.class);
    }

    @Override
    @Nullable
    public JBehaveGivenStories getGivenStories() {
        return findChildByClass(JBehaveGivenStories.class);
    }

    @Override
    @Nullable
    public JBehaveLifecycle getLifecycle() {
        return findChildByClass(JBehaveLifecycle.class);
    }

    @Override
    @Nullable
    public JBehaveMetaStatement getMetaStatement() {
        return findChildByClass(JBehaveMetaStatement.class);
    }

    @Override
    @Nullable
    public JBehaveNarrative getNarrative() {
        return findChildByClass(JBehaveNarrative.class);
    }

    @Override
    @NotNull
    public List<JBehaveScenario> getScenarioList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveScenario.class);
    }

}
