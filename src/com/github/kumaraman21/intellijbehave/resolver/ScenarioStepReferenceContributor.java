package com.github.kumaraman21.intellijbehave.resolver;

import com.github.kumaraman21.intellijbehave.parser.GivenStories;
import com.github.kumaraman21.intellijbehave.parser.ScenarioStep;
import com.github.kumaraman21.intellijbehave.parser.StoryPath;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class ScenarioStepReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        ScenarioStepReferenceProvider provider = new ScenarioStepReferenceProvider();
        registrar.registerReferenceProvider(psiElement(ScenarioStep.class), provider);
        registrar.registerReferenceProvider(psiElement(GivenStories.class), provider);
        registrar.registerReferenceProvider(psiElement(StoryPath.class), provider);
    }
}
