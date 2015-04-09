package com.github.kumaraman21.intellijbehave.refactor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.codeStyle.SuggestedNameInfo;
import com.intellij.refactoring.rename.NameSuggestionProvider;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * Created by DeBritoD on 19.03.2015.
 */
public class JBehaveNameSuggestionProvider implements NameSuggestionProvider {
    @Nullable
    @Override
    public SuggestedNameInfo getSuggestedNames(PsiElement element, PsiElement nameSuggestionContext,
                                               Set<String> result) {
        assert result != null;
        if (nameSuggestionContext != null) {
            if (nameSuggestionContext instanceof AnnotationSuggestionHolder) {
                String text = nameSuggestionContext.getText();
                if (text != null) {
                    result.clear();
                    if (text.contains("\"")) {
                        text = text.replace("\"", "");
                    }
                    result.add(text);
                    return SuggestedNameInfo.NULL_INFO;
                }
            } else {
                if (nameSuggestionContext instanceof InjectSuggestionHolder ||
                        nameSuggestionContext instanceof StepParameterSuggestionHolder) {
                    String text = nameSuggestionContext.getText();
                    if (text != null) {
                        result.clear();
                        result.add(text);
                        return SuggestedNameInfo.NULL_INFO;
                    }
                }
            }
        }
        return null;

    }
}
