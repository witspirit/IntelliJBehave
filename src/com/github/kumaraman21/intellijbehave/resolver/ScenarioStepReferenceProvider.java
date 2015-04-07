package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.parser.StoryPath;
import com.github.kumaraman21.intellijbehave.parser.StoryPathPsiReference;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ScenarioStepReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof ScenarioStep) {
            final ScenarioStep step = (ScenarioStep) element;
            return new PsiReference[]{new ScenarioStepReference(step, TextRange.from(0, step.getStoryLine().length()))};
        }
        if (element instanceof StoryPath) {
            final StoryPath step = (StoryPath) element;
            return new PsiReference[]{new StoryPathPsiReference(step, TextRange.from(0, element.getTextLength()))};
        }

        return PsiReference.EMPTY_ARRAY;
    }
}
