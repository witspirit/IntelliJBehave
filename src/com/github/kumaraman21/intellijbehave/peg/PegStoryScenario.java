package com.github.kumaraman21.intellijbehave.peg;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.psi.StoryScenario;
import com.github.kumaraman21.intellijbehave.psi.StoryScenarioTitle;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/**
 * Created by DeBritoD on 03.04.2015.
 */
public class PegStoryScenario extends ASTWrapperPsiElement {
    public PegStoryScenario(ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public String getName() {
        return getText();
    }

    public String getTitle() {
        StoryScenario scen = (StoryScenario) getOriginalElement();
        StoryScenarioTitle scenarioTitle = scen.getScenarioTitle();
        if (scenarioTitle != null) {
            String text = scenarioTitle.getText();
            return "Scenario: " + text;
        }
        return "Scenario";
    }

    public Collection<? extends PsiElement> getSteps() {
        return ((StoryScenario) getOriginalElement()).getStepList();
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return getTitle();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return JBehaveIcons.SCENARIO;
            }
        };
    }
}
