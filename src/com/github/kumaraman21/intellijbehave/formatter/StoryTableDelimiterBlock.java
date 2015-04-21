package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.codeStyle.JBehaveCodeStyleSettings;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryTableDelimiterBlock extends IndentChildrenBlock {
    private int columnNr;

    public StoryTableDelimiterBlock(int columnNr, ASTNode node, @NotNull JBehaveCodeStyleSettings settings,
                                    @NotNull SpacingBuilder spacingBuilder,
                                    @NotNull IndentingMappings indentingMappings) {
        super(node, settings, spacingBuilder, indentingMappings);
        this.columnNr = columnNr;
    }

    public int getColumnNr() {
        return columnNr;
    }

    @Override
    protected List<Block> buildChildren() {
        return EMPTY;
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

    @Override
    public Indent getIndent() {
        return Indent.getSpaceIndent(settings.INDENT_LEAFS, true);
    }

}
