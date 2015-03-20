package com.github.kumaraman21.intellijbehave.formatter;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.formatter.common.AbstractBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryBlock extends AbstractBlock {

    protected StoryBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        ASTNode firstChildNode = myNode.getFirstChildNode();
        ASTNode node = firstChildNode.getFirstChildNode();
        while (node != null) {
            retVal.add(new StoryIgnoreBlock(node, null, null));
            node = node.getTreeNext();
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        if(child1==null){
            return Spacing.createSpacing(0, 0, 0, true, 0);
        }
        return  Spacing.createSpacing(0, 0, 0, true, 1);
    }
    @Override
    public Indent getIndent() {
        return Indent.getNoneIndent();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
