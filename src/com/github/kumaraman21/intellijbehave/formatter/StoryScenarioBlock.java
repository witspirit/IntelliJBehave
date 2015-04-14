package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.intellij.formatting.Block;
import com.intellij.formatting.SpacingBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

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
    protected StoryScenarioBlock(ASTNode node, @NotNull SpacingBuilder spacingBuilder,
                                 @NotNull IndentingMappings indentingMappings) {
        super(node, spacingBuilder, indentingMappings);
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
                } else retVal.add(new StoryScenarioBlock(node, spacingBuilder, indentingMappings));
            }
            node = node.getTreeNext();
        }
        return retVal;
    }
    //    @Nullable
    //    @Override
    //    public Spacing getSpacing(Block child1, @NotNull Block child2) {
    //        return spacingBuilder.getSpacing(this, child1, child2);
    //    }
    //
    //    @Override
    //    public boolean isLeaf() {
    //        return false;
    //    }
}
