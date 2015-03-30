package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 23.03.2015.
 */
public class StoryScenarioBlock extends StoryIgnoreBlock {
    protected StoryScenarioBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    private static TokenSet ignore = TokenSet.create(IStoryPegElementType.STORY_TOKEN_NEWLINE,
            IStoryPegElementType.STORY_TOKEN_SPACE, TokenType.WHITE_SPACE);
    private static TokenSet compounds = TokenSet.create(IStoryPegElementType.STORY_STEP,
            IStoryPegElementType.STORY_STEP_ARGUMENT, IStoryPegElementType.STORY_STEP_POST_PARAMETER);

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        ASTNode node = myNode.getFirstChildNode();
        while (node != null) {
            IElementType elementType = node.getElementType();
            if (!ignore.contains(elementType)) {
                if (compounds.contains(elementType)) {
                    retVal.add(new StoryScenarioBlock(node, null, null));
                } else if (elementType == IStoryPegElementType.STORY_TABLE) {
                    retVal.add(new StoryTableBlock(node, null, null));
                } else retVal.add(new StoryScenarioLeafBlock(node, null, null));
            }
            node = node.getTreeNext();
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        if (child1 == null && child2 instanceof StoryScenarioLeafBlock) {
            return Spacing.createSpacing(0, 0, 0, false, 0);
        }
        if (child1 instanceof StoryScenarioLeafBlock && child2 instanceof StoryScenarioLeafBlock) {
            return Spacing.createSpacing(1, 1, 0, false, 0);
        }
        return Spacing.createSpacing(0, 1, 0, true, 1);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
