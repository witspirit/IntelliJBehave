package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.codeStyle.JBehaveCodeStyleSettings;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryTableRowBlock extends IndentChildrenBlock {
    private int nrOfColumns = 0;
    private int[] columnWidths;

    public StoryTableRowBlock(int[] columnWidths, ASTNode node, @NotNull JBehaveCodeStyleSettings settings,
                              @NotNull SpacingBuilder spacingBuilder, @NotNull IndentingMappings indentingMappings) {
        super(node, settings, spacingBuilder, indentingMappings);
        this.columnWidths = columnWidths;
    }

    public StoryTableRowBlock(ASTNode node, @NotNull JBehaveCodeStyleSettings settings,
                              @NotNull SpacingBuilder spacingBuilder, @NotNull IndentingMappings indentingMappings) {
        super(node, settings, spacingBuilder, indentingMappings);
    }

    public void setColumnWidths(int[] columnWidths) {
        this.columnWidths = columnWidths;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        int columnNr = -1;
        ASTNode node = getNode();
        ASTNode[] children =
                node.getChildren(TokenSet.create(IJBehaveElementType.JB_TOKEN_PIPE, IJBehaveElementType.JB_TABLE_CELL/*,
                                IJBehaveElementType.JB_TABLE_CELL_EMPTY*/));
        for (ASTNode child : children) {
            IElementType elementType = child.getElementType();
          /*  if (elementType == IJBehaveElementType.JB_TABLE_CELL_EMPTY) {
                ++columnNr;
            } else*/
            if (elementType == IJBehaveElementType.JB_TABLE_CELL) {
                retVal.add(new StoryTableCellBlock(columnNr, child, settings, spacingBuilder, indentingMappings));
            } else if (elementType == IJBehaveElementType.JB_TOKEN_PIPE) {
                ++columnNr;
                retVal.add(new StoryTableDelimiterBlock(columnNr, child, settings, spacingBuilder, indentingMappings));
            }
        }
        nrOfColumns = columnNr;
        return retVal;
    }

    public int[] getTrimmedColumnWidths() {
        List<Block> subBlocks = getSubBlocks();
        int[] columns = new int[nrOfColumns];
        Arrays.fill(columns, 0);
        for (Block subBlock : subBlocks) {
            if (subBlock instanceof StoryTableCellBlock) {
                int columnNr = ((StoryTableCellBlock) subBlock).getColumnNr();
                columns[columnNr] = ((StoryTableCellBlock) subBlock).getNode().getText().trim().length();
            }
        }
        return columns;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, @NotNull Block child2) {
        //Spacing.createSpacing()
        if (child1 == null && child2 instanceof StoryTableDelimiterBlock) {
            return Spacing.createSpacing(0, 0, 0, true, 0);
        }
        if (child1 instanceof StoryTableDelimiterBlock && child2 instanceof StoryTableCellBlock) {
            return Spacing.createSpacing(1, 1, 0, true, 0);
        }
        if (child1 instanceof StoryTableDelimiterBlock && child2 instanceof StoryTableDelimiterBlock) {
            StoryTableDelimiterBlock cell = (StoryTableDelimiterBlock) child1;
            int columnNr = cell.getColumnNr();
            int width = columnWidths[columnNr] + 2;
            return Spacing.createSpacing(width, width, 0, true, 0);
        }
        if (child1 instanceof StoryTableCellBlock && child2 instanceof StoryTableDelimiterBlock) {
            StoryTableCellBlock cell = (StoryTableCellBlock) child1;
            String text = cell.getText();
            int length = text.length();
            int columnNr = cell.getColumnNr();
            int width = length;
            if (columnNr < columnWidths.length) {
                width = columnWidths[columnNr] + 1;
            }
            int diff = width - length;
            return Spacing.createSpacing(diff, diff, 0, true, 0);
        }
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Indent getIndent() {
        return Indent.getAbsoluteNoneIndent();
    }


}
