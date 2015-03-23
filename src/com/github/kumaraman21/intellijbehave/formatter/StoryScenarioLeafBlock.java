package com.github.kumaraman21.intellijbehave.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by DeBritoD on 23.03.2015.
 */
public class StoryScenarioLeafBlock extends StoryIgnoreBlock {
    protected StoryScenarioLeafBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
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
        }
        return Spacing.createSpacing(1, 1, 0, false, 0);
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
