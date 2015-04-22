package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.psi.JBehaveNarrative;
import com.github.kumaraman21.intellijbehave.psi.JBehaveNarrativeText;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by DeBritoD on 08.04.2015.
 */
public class Narrative extends ParserRule {
    public Narrative(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                JBehaveNarrativeText narrativeText = ((JBehaveNarrative) getOriginalElement()).getNarrativeText();
                if (narrativeText != null) {
                    return narrativeText.getText().replaceAll("\\s+", " ");
                }
                return "";
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return JBehaveIcons.NARRATIVE;
            }
        };
    }
}
