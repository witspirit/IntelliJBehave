package com.github.kumaraman21.intellijbehave.formatter;

import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryTableCellBlock extends StoryIgnoreBlock {
    private int columnNr;

    protected StoryTableCellBlock(int columnNr, ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
        this.columnNr = columnNr;
    }

    @Override
    protected List<Block> buildChildren() {
        return EMPTY;
    }

    public int getColumnNr() {
        return columnNr;
    }

    public String getText() {
        return myNode.getText().trim();
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
