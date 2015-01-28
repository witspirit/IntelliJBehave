package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.JBehaveGivenStories;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class StepPsiReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        JBehaveStepReferenceProvider provider = new JBehaveStepReferenceProvider();
        registrar.registerReferenceProvider(psiElement(JBehaveStep.class), provider);
        registrar.registerReferenceProvider(psiElement(JBehaveGivenStories.class), provider);    }
}
