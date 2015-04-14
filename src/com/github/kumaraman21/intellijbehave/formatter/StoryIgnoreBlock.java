package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryIgnoreBlock extends AbstractBlock {
    protected static TokenSet ignore =
            TokenSet.create(IJBehaveElementType.JB_TOKEN_NEWLINE, IJBehaveElementType.JB_TOKEN_SPACE,
                            TokenType.WHITE_SPACE);

    protected StoryIgnoreBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        return EMPTY;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        if (child1 == null) {
            return Spacing.createSpacing(0, 0, 0, true, 1);
        } else {
            return Spacing.createSpacing(1, 1, 0, true, 1);
        }
    }

    @Override
    public Indent getIndent() {
        return Indent.getAbsoluteNoneIndent();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
