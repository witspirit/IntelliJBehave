package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.psi.JBehaveTableCell;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTableCellEmpty;
import com.github.kumaraman21.intellijbehave.psi.JBehaveTableRow;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryTableBlock extends StoryIgnoreBlock {
    protected StoryTableBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        PsiElement psiElement = myNode.getPsi();
        Collection<JBehaveTableRow> rows = PsiTreeUtil.findChildrenOfType(psiElement, JBehaveTableRow.class);
        int[] columnWidths = new int[0];
        Iterator<JBehaveTableRow> rowIt = rows.iterator();
        if (rowIt.hasNext()) {
            JBehaveTableRow headerRow = rowIt.next();
            Collection<JBehaveTableCell> cells = PsiTreeUtil.findChildrenOfType(headerRow, JBehaveTableCell.class);
            columnWidths = new int[cells.size()];
            Arrays.fill(columnWidths, 0);
            for (JBehaveTableRow row : rows) {
                int i = 0;
                for (PsiElement cell : PsiTreeUtil.findChildrenOfAnyType(row, JBehaveTableCell.class,
                        JBehaveTableCellEmpty.class)) {
                    String cellText = cell.getText().trim();
                    int length = cellText.length();
                    if (length > columnWidths[i]) {
                        columnWidths[i] = length;
                    }
                    ++i;
                }
            }
        }
        for (JBehaveTableRow row : rows) {
            retVal.add(new StoryTableRowBlock(columnWidths, row.getNode(), null, null));
        }

        return retVal;
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
