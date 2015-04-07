// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface JBehaveStory extends PsiElement {

    @Nullable
    JBehaveDescription getDescription();

    @Nullable
    JBehaveGivenStories getGivenStories();

    @Nullable
    JBehaveLifecycle getLifecycle();

    @Nullable
    JBehaveMetaStatement getMetaStatement();

    @Nullable
    JBehaveNarrative getNarrative();

    @NotNull
    List<JBehaveScenario> getScenarioList();

}
