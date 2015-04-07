package com.github.kumaraman21.intellijbehave.folding;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.CustomFoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DeBritoD on 02.04.2015.
 */
public class JBehaveFoldingBuilder extends CustomFoldingBuilder {
    @Override
    protected void buildLanguageFoldRegions(List<FoldingDescriptor> descriptors, PsiElement root, Document document,
                                            boolean quick) {
        Collection<PsiElement> elementCollection = PsiTreeUtil.findChildrenOfAnyType(root, StoryDescription.class,
                StoryScenario.class, StoryTable.class, StoryMetaStatement.class, StoryNarrative.class);
        for (PsiElement element : elementCollection) {
            if (element instanceof StoryScenario) {
                foldScenario(descriptors, (StoryScenario) element);
            } else {
                if (element instanceof StoryDescription) {
                    foldWholeRegion(descriptors, element);
                } else {
                    if (element instanceof StoryTable) {
                        foldWholeRegion(descriptors, element);
                    } else {
                        foldAfterToken(descriptors, element);
                    }

                }
            }
        }
    }

    private void foldAfterToken(List<FoldingDescriptor> descriptors, PsiElement element) {
        ASTNode firstChildNode = element.getNode().getFirstChildNode();
        if (firstChildNode != null) {
            TextRange textRange = firstChildNode.getTextRange();
            TextRange elementTextRange = element.getTextRange();
            descriptors.add(new FoldingDescriptor(element,
                    new TextRange(textRange.getEndOffset(), elementTextRange.getEndOffset())));
        }
    }

    private void foldWholeRegion(List<FoldingDescriptor> descriptors, PsiElement element) {
        descriptors.add(new FoldingDescriptor(element, element.getTextRange()));
    }

    private void foldScenario(List<FoldingDescriptor> descriptors, StoryScenario scenario) {
        Iterator<StoryScenarioTitle> itTitle = PsiTreeUtil.findChildrenOfType(scenario,
                StoryScenarioTitle.class).iterator();
        if (itTitle.hasNext()) {
            StoryScenarioTitle title = itTitle.next();
            descriptors.add(new FoldingDescriptor(scenario,
                    new TextRange(title.getTextRange().getEndOffset(), scenario.getTextRange().getEndOffset() - 1)));
        }
    }

    @Override
    protected String getLanguagePlaceholderText(ASTNode node, TextRange range) {
        if (node.getElementType() == IJBehaveElementType.JB_TABLE) return "Table";
        if (node.getElementType() == IJBehaveElementType.JB_DESCRIPTION) return "Description";
        return "...";
    }

    @Override
    protected boolean isRegionCollapsedByDefault(ASTNode node) {
        return false;
    }
}
