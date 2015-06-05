package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.language.JBehaveIcons;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by DeBritoD on 19.03.2015.
 */
public class AnnotationSuggestionHolder extends SuggestionHolder {

    public AnnotationSuggestionHolder(PsiElement value) {
        super(value);
    }

    @Nullable
    @Override
    public Icon getIcon(boolean open) {
        return JBehaveIcons.JB;
    }

}
