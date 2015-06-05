package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by DeBritoD on 08.04.2015.
 */
public class InjectSuggestionHolder extends SuggestionHolder {
    public InjectSuggestionHolder(PsiElement value) {
        super(value);
    }

    @Nullable
    @Override
    public String getText() {
        String text = super.getText();
        if (text != null) text = text.replaceAll("[<>]+", "");
        return text;
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return null;
    }
}
