// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface JBehaveScenario extends PsiElement {

    @NotNull
    List<JBehaveExamples> getExamplesList();

    @Nullable
    JBehaveGivenStories getGivenStories();

    @Nullable
    JBehaveMetaStatement getMetaStatement();

    @Nullable
    JBehaveScenarioTitle getScenarioTitle();

    @NotNull
    List<JBehaveStep> getStepList();

}
