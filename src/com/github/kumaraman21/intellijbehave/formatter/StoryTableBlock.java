package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.psi.StoryTableCell;
import com.github.kumaraman21.intellijbehave.psi.StoryTableCellEmpty;
import com.github.kumaraman21.intellijbehave.psi.StoryTableRow;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryTableBlock extends AbstractBlock {
    protected StoryTableBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        PsiElement psiElement = myNode.getPsi();
        Collection<StoryTableRow> rows = PsiTreeUtil.findChildrenOfType(psiElement, StoryTableRow.class);
        int[] columnWidths = new int[0];
        Iterator<StoryTableRow> rowIt = rows.iterator();
        if (rowIt.hasNext()) {
            StoryTableRow headerRow = rowIt.next();
            Collection<StoryTableCell> cells = PsiTreeUtil.findChildrenOfType(headerRow, StoryTableCell.class);
            columnWidths = new int[cells.size()];
            Arrays.fill(columnWidths, 0);
            for (StoryTableRow row : rows) {
                int i = 0;
                for (PsiElement cell : PsiTreeUtil.findChildrenOfAnyType(row, StoryTableCell.class,
                        StoryTableCellEmpty.class)) {
                    String cellText = cell.getText().trim();
                    int length = cellText.length();
                    if (length > columnWidths[i]) {
                        columnWidths[i] = length;
                    }
                    ++i;
                }
            }
        }
        for (StoryTableRow row : rows) {
            retVal.add(new StoryTableRowBlock(columnWidths, row.getNode(), null, null));
        }

        return retVal;
    }

    @Override
    public Indent getIndent() {
        return Indent.getNoneIndent();
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return Spacing.createSpacing(0, 0, 0, true, 1);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
