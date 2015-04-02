package com.github.kumaraman21.intellijbehave.folding;

import com.github.kumaraman21.intellijbehave.psi.StoryScenario;
import com.github.kumaraman21.intellijbehave.psi.StoryScenarioTitle;
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
        Collection<StoryScenario> scenarios = PsiTreeUtil.findChildrenOfType(root, StoryScenario.class);
        for (final StoryScenario scenario : scenarios) {
            Iterator<StoryScenarioTitle> itTitle = PsiTreeUtil.findChildrenOfType(scenario,
                    StoryScenarioTitle.class).iterator();
            if(itTitle.hasNext()) {
                StoryScenarioTitle title = itTitle.next();
                descriptors.add(new FoldingDescriptor(scenario, new TextRange(title.getTextRange().getEndOffset(),
                        scenario.getTextRange().getEndOffset() - 1)));
            }
        }
    }

    @Override
    protected String getLanguagePlaceholderText(ASTNode node, TextRange range) {
        return "...";
    }

    @Override
    protected boolean isRegionCollapsedByDefault(ASTNode node) {
        return false;
    }
}
