package com.github.kumaraman21.intellijbehave.folding;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser.JBehaveFile;
import com.github.kumaraman21.intellijbehave.psi.*;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.CustomFoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by DeBritoD on 02.04.2015.
 */
public class JBehaveFoldingBuilder extends CustomFoldingBuilder {
    @Override
    protected void buildLanguageFoldRegions(@NotNull List<FoldingDescriptor> descriptors, @NotNull PsiElement root,
                                            @NotNull Document document, boolean quick) {
        JBehaveFile file = (JBehaveFile) root;
        JBehaveStory story = (JBehaveStory) file.getStory();
        foldWholeRegion(descriptors, story.getDescription());
        foldAfterToken(descriptors, story.getMetaStatement());
        foldAfterToken(descriptors, story.getNarrative());
        foldAfterToken(descriptors, story.getLifecycle());
        for (JBehaveScenario scenario : story.getScenarioList()) {
            foldScenario(descriptors, scenario);
            for (JBehaveStep step : scenario.getStepList()) {
                JBehaveStepArgument stepArgument = step.getStepArgument();
                if (stepArgument != null) {
                    JBehaveTable table = stepArgument.getTable();
                    if (table != null) {
                        foldWholeRegion(descriptors, table);
                    }
                }
            }
        }
    }

    private void foldAfterToken(List<FoldingDescriptor> descriptors, @Nullable PsiElement element) {
        if (element != null) {
            ASTNode firstChildNode = element.getNode().getFirstChildNode();
            if (firstChildNode != null) {
                TextRange textRange = firstChildNode.getTextRange();
                TextRange elementTextRange = element.getTextRange();
                descriptors.add(new FoldingDescriptor(element, new TextRange(textRange.getEndOffset(),
                                                                             elementTextRange.getEndOffset())));
            }
        }
    }

    private void foldWholeRegion(List<FoldingDescriptor> descriptors, @Nullable PsiElement element) {
        if (element != null) descriptors.add(new FoldingDescriptor(element, element.getTextRange()));
    }

    private void foldScenario(List<FoldingDescriptor> descriptors, @Nullable JBehaveScenario scenario) {
        //        Iterator<JBehaveScenarioTitle> itTitle = PsiTreeUtil.findChildrenOfType(scenario,
        //                JBehaveScenarioTitle.class).iterator();
        if (scenario != null) {
            JBehaveScenarioTitle scenarioTitle = scenario.getScenarioTitle();
            if (scenarioTitle != null) {
                int endOffset = scenarioTitle.getTextRange().getEndOffset();
                int endOffset1 = scenario.getTextRange().getEndOffset();
                descriptors.add(new FoldingDescriptor(scenario, new TextRange(endOffset, endOffset1)));
            }
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
