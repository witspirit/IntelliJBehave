package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.codeStyle.JBehaveCodeStyleSettings;
import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.Block;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 23.03.2015.
 */
public class StoryScenarioBlock extends IndentChildrenBlock {
    //    private static TokenSet compounds =
    //            TokenSet.create(IJBehaveElementType.JB_STEP, IJBehaveElementType.JB_STEP_ARGUMENT,
    //                            IJBehaveElementType.JB_STEP_POST_PARAMETER, IJBehaveElementType.JB_EXAMPLES);
    //

    public StoryScenarioBlock(ASTNode node, @NotNull JBehaveCodeStyleSettings settings,
                              @NotNull SpacingBuilder spacingBuilder, @NotNull IndentingMappings indentingMappings) {
        super(node, settings, spacingBuilder, indentingMappings);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        ASTNode node = myNode.getFirstChildNode();
        while (node != null) {
            IElementType elementType = node.getElementType();
            if (!ignore.contains(elementType)) {
                if (elementType == IJBehaveElementType.JB_TABLE) {
                    retVal.add(new StoryTableBlock(node, null, null));
                } else retVal.add(new StoryScenarioBlock(node, settings, spacingBuilder, indentingMappings));
            }
            node = node.getTreeNext();
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, @NotNull Block child2) {
        if (child1 != null) {
            AbstractBlock block1 = (AbstractBlock) child1;
            AbstractBlock block2 = (AbstractBlock) child2;
            ASTNode node1 = block1.getNode();
            IElementType elementType1 = node1.getElementType();
            ASTNode node2 = block2.getNode();
            IElementType elementType2 = node2.getElementType();
            if (elementType2 == IJBehaveElementType.JB_STEP) {
                if (elementType1 == IJBehaveElementType.JB_STEP) {
                    ASTNode childNode2 = node2.getFirstChildNode().getFirstChildNode();
                    IElementType elementType = childNode2.getElementType();
                    if (elementType == IJBehaveElementType.JB_TOKEN_GIVEN) {
                        return Spacing
                                .createSpacing(0, 1, settings.GIVEN_STEP_LINEFEED, settings.GIVEN_STEP_KEEPLINEBREAKS,
                                               settings.ALL_KEEPEMPTYLINES);
                    } else if (elementType == IJBehaveElementType.JB_TOKEN_AND) {
                        return Spacing.createSpacing(0, 1, settings.AND_STEP_LINEFEED, settings.AND_STEP_KEEPLINEBREAKS,
                                                     settings.ALL_KEEPEMPTYLINES);
                    } else if (elementType == IJBehaveElementType.JB_TOKEN_WHEN) {
                        return Spacing
                                .createSpacing(0, 1, settings.WHEN_STEP_LINEFEED, settings.WHEN_STEP_KEEPLINEBREAKS,
                                               settings.ALL_KEEPEMPTYLINES);
                    } else if (elementType == IJBehaveElementType.JB_TOKEN_THEN) {
                        return Spacing
                                .createSpacing(0, 1, settings.THEN_STEP_LINEFEED, settings.THEN_STEP_KEEPLINEBREAKS,
                                               settings.ALL_KEEPEMPTYLINES);
                    }
                } else {
                    return Spacing.createSpacing(0, 1, settings.FIRST_STEP_LINEFEED, settings.FIRST_STEP_KEEPLINEBREAKS,
                                                 settings.ALL_KEEPEMPTYLINES);
                }
            }
        }
        return spacingBuilder.getSpacing(this, child1, child2);
    }
    //
    //    @Override
    //    public boolean isLeaf() {
    //        return false;
    //    }
}
