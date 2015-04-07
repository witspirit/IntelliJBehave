package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.psi.JBehaveScenario;
import com.github.kumaraman21.intellijbehave.psi.JBehaveScenarioTitle;
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
public class Scenario extends ASTWrapperPsiElement {
    public Scenario(ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public String getName() {
        return getText();
    }

    public String getTitle() {
        final JBehaveScenarioTitle scenarioTitle = ((JBehaveScenario) getOriginalElement()).getScenarioTitle();
        return scenarioTitle != null ? scenarioTitle.getText() : "";
    }

    public Collection<? extends PsiElement> getSteps() {
        return ((JBehaveScenario) getOriginalElement()).getStepList();
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
