package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.github.kumaraman21.intellijbehave.peg.PegStoryPath;
import com.github.kumaraman21.intellijbehave.peg.StoryPathPsiReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class JBehaveStepReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof JBehaveStep) {
            final JBehaveStep step = (JBehaveStep) element;
            String text = step.getStepText();
            String stepLineText = step.getStepLineText();
            int stepTextOffset = step.getStepTextOffset();
            String storyLine = step.getStoryLine();
            return new PsiReference[]{new StepPsiReference(step, TextRange.from(0, storyLine.length()))};
        }
////        if (element instanceof JBehaveGivenStories) {
////            final JBehaveGivenStories step = (JBehaveGivenStories) element;
////
////            return new PsiReference[]{new GivenStoriesPsiReference(step, TextRange.from(0, element.getTextLength()))};
////        }
        if (element instanceof PegStoryPath) {
            final PegStoryPath step = (PegStoryPath) element;

            int textLength = element.getTextLength();
            String text = element.getText();
            TextRange textRange = element.getTextRange();
            int startOffsetInParent = element.getStartOffsetInParent();
            return new PsiReference[]{new StoryPathPsiReference(step, TextRange.from(0, textLength))};
        }

        return PsiReference.EMPTY_ARRAY;
    }
}
