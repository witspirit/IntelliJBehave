// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi.impl;

import com.github.kumaraman21.intellijbehave.parser.MetaStatement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaElement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaStatement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveVisitor;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_NEWLINE;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.JB_TOKEN_SPACE;

public class JBehaveMetaStatementImpl extends MetaStatement implements JBehaveMetaStatement {

    public JBehaveMetaStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof JBehaveVisitor) ((JBehaveVisitor) visitor).visitMetaStatement(this);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<JBehaveMetaElement> getMetaElementList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, JBehaveMetaElement.class);
    }

    @Override
    @Nullable
    public PsiElement getTokenNewline() {
        return findChildByType(JB_TOKEN_NEWLINE);
    }

    @Override
    @Nullable
    public PsiElement getTokenSpace() {
        return findChildByType(JB_TOKEN_SPACE);
    }

}
