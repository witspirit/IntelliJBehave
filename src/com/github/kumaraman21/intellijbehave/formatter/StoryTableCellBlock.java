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
public class StoryTableCellBlock extends IndentChildrenBlock {
    private int columnNr;

    public StoryTableCellBlock(int columnNr, ASTNode node, @NotNull JBehaveCodeStyleSettings settings,
                               @NotNull SpacingBuilder spacingBuilder, @NotNull IndentingMappings indentingMappings) {
        super(node, settings, spacingBuilder, indentingMappings);
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

    @Override
    public Indent getIndent() {
        return Indent.getSpaceIndent(settings.INDENT_LEAFS, true);
    }

    //    @NotNull
    //    @Override
    //    public TextRange getTextRange() {
    //        if (!isEmptyCell()) return ((JBehaveTableCell) myNode.getPsi()).getTableCellValue().getTextRange();
    //        TextRange textRange = super.getTextRange();
    //        if (textRange.getLength() == 0) {
    //            //            textRange = new TextRange(textRange.getStartOffset()-1, textRange.getEndOffset() + 1);
    //            String f = "";
    //        }
    //        return textRange;
    //    }
    //
    //    public boolean isEmptyCell() {
    //        return ((JBehaveTableCell) myNode.getPsi()).getTableCellEmpty() != null;
    //    }
}
