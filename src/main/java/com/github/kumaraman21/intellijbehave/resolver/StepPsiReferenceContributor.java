package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * Provides references for JBehave steps in Story files.
 *
 * @see StepPsiReference
 */
public final class StepPsiReferenceContributor extends PsiReferenceContributor {

    private static final PsiReferenceProvider REFERENCE_PROVIDER = new PsiReferenceProvider() {
        @Override
        public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
            return element instanceof JBehaveStep step
                   ? new PsiReference[]{new StepPsiReference(step, TextRange.from(0, element.getTextLength()))}
                   : PsiReference.EMPTY_ARRAY;
        }
    };

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(psiElement(JBehaveStep.class), REFERENCE_PROVIDER);
    }
}
