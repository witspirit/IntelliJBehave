package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.psi.ElementDescriptionLocation;
import com.intellij.psi.ElementDescriptionProvider;
import com.intellij.psi.PsiElement;
import com.intellij.usageView.UsageViewTypeLocation;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 19.03.2015.
 */
public class JBehaveElementDescriptionProvider implements ElementDescriptionProvider {
    @Nullable
    @Override
    public String getElementDescription(PsiElement element, ElementDescriptionLocation location) {
        if (element instanceof PsiSuggestionHolder && location instanceof UsageViewTypeLocation) {
            return "Step implementation";
        }
        return null;
    }
}
