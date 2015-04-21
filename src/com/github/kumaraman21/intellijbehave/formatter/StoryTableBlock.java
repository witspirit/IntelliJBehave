package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.codeStyle.JBehaveCodeStyleSettings;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTable;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTableRow;
import com.intellij.formatting.Block;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryTableBlock extends IndentChildrenBlock {
    public StoryTableBlock(ASTNode node, @NotNull JBehaveCodeStyleSettings settings,
                           @NotNull SpacingBuilder spacingBuilder, @NotNull IndentingMappings indentingMappings) {
        super(node, settings, spacingBuilder, indentingMappings);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        JBehaveTable table = (JBehaveTable) myNode.getPsi();
        List<JBehaveTableRow> rows = table.getTableRowList();

        int[] columnWidths = null;
        Iterator<JBehaveTableRow> rowIt = rows.iterator();
        if (rowIt.hasNext()) {
            JBehaveTableRow headerRow = rowIt.next();
            StoryTableRowBlock firstBlock =
                    new StoryTableRowBlock(headerRow.getNode(), settings, spacingBuilder, indentingMappings);
            retVal.add(firstBlock);
            //List<JBehaveTableCell> cells = headerRow.getTableCellList();
            columnWidths = firstBlock.getTrimmedColumnWidths();
            while (rowIt.hasNext()) {
                StoryTableRowBlock block =
                        new StoryTableRowBlock(rowIt.next().getNode(), settings, spacingBuilder, indentingMappings);
                retVal.add(block);
                int[] trimmedColumnWidths = block.getTrimmedColumnWidths();
                for (int i = 0; i < columnWidths.length; ++i) {
                    columnWidths[i] = Math.max(columnWidths[i], trimmedColumnWidths[i]);
                }

            }
        }
        for (Block block : retVal) {
            ((StoryTableRowBlock) block).setColumnWidths(columnWidths);
        }

        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return Spacing.createSpacing(0, 0, 1, false, 0);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Indent getIndent() {
        return Indent.getSpaceIndent(settings.INDENT_LEAFS, true);
    }

}
