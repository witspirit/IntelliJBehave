package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.psi.JBehaveNarrative;
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
                String text = "";

                try {
                    text = ((JBehaveNarrative) getOriginalElement()).getNarrativeText().getText();
                    text = text.replaceAll("\\s+", " ");
                } catch (NullPointerException e) {
                    text = "";
                }
                return text;
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
