// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.StoryPath;
import com.github.kumaraman21.intellijbehave.psi.JBehaveStoryPath;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_PATH;

public class StoryPathImpl extends StoryPath implements JBehaveStoryPath {

    public StoryPathImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitStoryPath(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public PsiElement getTokenPath() {
        return findNotNullChildByType(JB_TOKEN_PATH);
    }

}
