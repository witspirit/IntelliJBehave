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
        if (story != null) {
            foldWholeRegion(descriptors, story.getDescription());
            JBehaveMetaStatement metaStatement = story.getMetaStatement();
            if (metaStatement != null && !metaStatement.getMetaElementList().isEmpty()) {
                foldAfterToken(descriptors, metaStatement.getFirstChild(), metaStatement);
            }
            JBehaveNarrative narrative = story.getNarrative();
            if (narrative != null && narrative.getNarrativeText() != null) {
                foldAfterToken(descriptors, narrative.getFirstChild(), narrative);
            }
            JBehaveLifecycle lifecycle = story.getLifecycle();
            if (lifecycle != null &&
                    (lifecycle.getLifecycleAfter() != null || lifecycle.getLifecycleBefore() != null)) {
                foldAfterToken(descriptors, lifecycle.getFirstChild(), lifecycle);
            }
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
    }

    private void foldAfterToken(List<FoldingDescriptor> descriptors, @NotNull PsiElement afterToken,
                                @NotNull PsiElement foldRegion) {
        TextRange afterTextRange = afterToken.getTextRange();
        TextRange textRange = foldRegion.getTextRange();
        descriptors.add(new FoldingDescriptor(afterToken,
                                              new TextRange(afterTextRange.getEndOffset(), textRange.getEndOffset())));
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
