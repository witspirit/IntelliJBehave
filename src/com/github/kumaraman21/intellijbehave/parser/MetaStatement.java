package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaElement;
import com.github.kumaraman21.intellijbehave.psi.JBehaveMetaStatement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * Created by DeBritoD on 08.04.2015.
 */
public class MetaStatement extends ParserRule {
    public MetaStatement(@NotNull ASTNode node) {
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
                    List<JBehaveMetaElement> elements =
                            ((JBehaveMetaStatement) getOriginalElement()).getMetaElementList();
                    if (!elements.isEmpty()) {
                        text = elements.get(0).getText();
                    } else text = "";
                    text = text.replace("\n", " ");
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
                return JBehaveIcons.META;
            }
        };
    }
}
