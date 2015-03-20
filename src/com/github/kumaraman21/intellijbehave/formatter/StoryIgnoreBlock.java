package com.github.kumaraman21.intellijbehave.formatter;

import com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType;
import com.github.kumaraman21.intellijbehave.psi.StoryTable;
import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DeBritoD on 20.03.2015.
 */
public class StoryIgnoreBlock extends AbstractBlock {
    protected StoryIgnoreBlock(ASTNode node, @Nullable Wrap wrap, @Nullable Alignment alignment) {
        super(node, wrap, alignment);
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> retVal = new ArrayList<Block>();
        PsiElement psiElement = myNode.getPsi();
        Collection<StoryTable> storyTables = PsiTreeUtil.findChildrenOfType(psiElement, StoryTable.class);
        if (!storyTables.isEmpty()) {
            //ASTNode firstChildNode = myNode.getFirstChildNode();
            ASTNode node = myNode.getFirstChildNode();
            while (node != null) {
                IElementType elementType = node.getElementType();
                if (elementType == IStoryPegElementType.STORY_TABLE) {
                    retVal.add(new StoryTableBlock(node, null, null));

                } else {
                    if (elementType != IStoryPegElementType.STORY_TOKEN_SPACE && elementType != IStoryPegElementType.STORY_TOKEN_NEWLINE)
                        retVal.add(new StoryIgnoreBlock(node, null, null));
                }
                node = node.getTreeNext();
            }

//        for (StoryTable storyTable : storyTables) {
//            retVal.add(new StoryTableBlock(storyTable.getNode(), null, null));
//        }
        }
        return retVal;
    }

    @Nullable
    @Override
    public Spacing getSpacing(Block child1, Block child2) {
        return Spacing.createSpacing(0, 0, 0, true, 1);
    }

    @Override
    public Indent getIndent() {
        return Indent.getNoneIndent();
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
}
