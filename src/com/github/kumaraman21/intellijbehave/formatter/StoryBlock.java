package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.codeStyle.JBehaveCodeStyleSettings;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryBlock extends IndentChildrenBlock {
    public StoryBlock(ASTNode node, @NotNull JBehaveCodeStyleSettings settings, @NotNull SpacingBuilder spacingBuilder,
                      @NotNull IndentingMappings indentingMappings) {

        super(node, settings, spacingBuilder, indentingMappings);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        ASTNode firstChildNode = myNode.getFirstChildNode();
        if (firstChildNode.getElementType() == IJBehaveElementType.JB_TABLE) {
            retVal.add(new StoryTableBlock(firstChildNode, null, null));
        } else {
            ASTNode node = firstChildNode.getFirstChildNode();
            while (node != null) {
                IElementType elementType = node.getElementType();
                if (elementType != IJBehaveElementType.JB_TOKEN_NEWLINE &&
                        elementType != IJBehaveElementType.JB_TOKEN_SPACE) {
                    if (elementType == IJBehaveElementType.JB_SCENARIO) {
                        retVal.add(new StoryScenarioBlock(node, settings, spacingBuilder, indentingMappings));
                    } else if (elementType == IJBehaveElementType.JB_TABLE) {
                        retVal.add(new StoryTableBlock(node, null, null));
                    } else retVal.add(new IndentChildrenBlock(node, settings, spacingBuilder, indentingMappings));
                }
                node = node.getTreeNext();
            }
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, @NotNull Block child2) {
        return spacingBuilder.getSpacing(this, child1, child2);
    }

    @Override
    public boolean isLeaf() {
        return false;
    }
}
