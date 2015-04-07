package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryBlock extends StoryIgnoreBlock {

    protected StoryBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, null, null);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        ASTNode firstChildNode = myNode.getFirstChildNode();
        if (firstChildNode.getElementType() == IJBehaveElementType.JB_TABLE) {
            retVal.add(new StoryTableBlock(firstChildNode, null, null));
        } else {
            ASTNode node = firstChildNode.getFirstChildNode();
            while (node != null) {
                IElementType elementType = node.getElementType();
                if (elementType != IJBehaveElementType.JB_TOKEN_NEWLINE && elementType != IJBehaveElementType.JB_TOKEN_SPACE) {
                    if (elementType == IJBehaveElementType.JB_SCENARIO) {
                        retVal.add(new StoryScenarioBlock(node, null, null));
                    } else if (elementType == IJBehaveElementType.JB_TABLE) {
                        retVal.add(new StoryTableBlock(node, null, null));
                    } else retVal.add(new StoryBlockBlock(node, null, null));
                }
                node = node.getTreeNext();
            }
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        if (child1 == null) {
            return Spacing.createSpacing(0, 0, 0, true, 1);
        }
        if (child1 instanceof StoryScenarioBlock && child2 instanceof StoryScenarioBlock) {
            return Spacing.createSpacing(0, 0, 2, true, 1);
        }
        return Spacing.createSpacing(0, 0, 0, true, 1);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
