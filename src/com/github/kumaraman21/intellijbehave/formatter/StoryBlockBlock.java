package com.github.kumaraman21.intellijbehave.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 23.03.2015.
 */
public class StoryBlockBlock extends StoryIgnoreBlock {
    protected StoryBlockBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return Spacing.createSpacing(0, 0, 2, false, 0);
    }
}
