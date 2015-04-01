package com.github.kumaraman21.intellijbehave.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 01.04.2015.
 */
public class StoryCommentBlock extends StoryScenarioLeafBlock {
    protected StoryCommentBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }
}
