package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 13.04.2015.
 */
public class IndentChildrenBlock extends AbstractBlock {
    protected static TokenSet ignore =
            TokenSet.create(IJBehaveElementType.JB_TOKEN_NEWLINE, IJBehaveElementType.JB_TOKEN_SPACE,
                            TokenType.WHITE_SPACE);


    protected IndentingMappings indentingMappings;
    protected SpacingBuilder spacingBuilder;

    public IndentChildrenBlock(ASTNode node, @NotNull SpacingBuilder spacingBuilder,
                               @NotNull IndentingMappings indentingMappings) {
        super(node, null, null);
        this.indentingMappings = indentingMappings;
        this.spacingBuilder = spacingBuilder;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        ASTNode node = myNode.getFirstChildNode();
        while (node != null) {
            IElementType elementType = node.getElementType();
            if (!ignore.contains(elementType)) {
                retVal.add(new IndentChildrenBlock(node, spacingBuilder, indentingMappings));
            }
            node = node.getTreeNext();
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public Indent getIndent() {
        return Indent.getSpaceIndent(indentingMappings.getIndent(getNode().getElementType()));
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }
}
