package com.github.kumaraman21.intellijbehave.refactor;

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
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
        if (element instanceof AnnotationSuggestionHolder && location instanceof UsageViewTypeLocation) {
            return "Step implementation";
        }
        if (element instanceof InjectSuggestionHolder || (element.getNode() != null &&
                element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_USER_INJECT &&
                location instanceof UsageViewTypeLocation)) {
            return "User inject";
        }
        if (element instanceof StepParameterSuggestionHolder || (element.getNode() != null &&
                element.getNode().getElementType() == IJBehaveElementType.JB_TOKEN_WORD &&
                location instanceof UsageViewTypeLocation)) {
            return "Step parameter";
        }
        return null;
    }
}
