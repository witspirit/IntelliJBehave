package com.github.kumaraman21.intellijbehave.service;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch.SearchParameters;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.*;

public class JavaStepDefinitionsSearch implements QueryExecutor<PsiReference, SearchParameters> {
    public boolean execute(@NotNull final SearchParameters queryParameters, @NotNull Processor<PsiReference> consumer) {
        PsiElement myElement = queryParameters.getElementToSearch();

        if (!(myElement instanceof PsiMethod)) {
            return true;
        }

        final PsiMethod method = (PsiMethod) myElement;

        Boolean isStepDefinition = ApplicationManager.getApplication().runReadAction(new Computable<Boolean>() {
            public Boolean compute() {
                return isStepDefinition(method);
            }
        });

        if (!isStepDefinition) {
            return true;
        }

        List<String> stepTexts = ApplicationManager.getApplication().runReadAction(new Computable<List<String>>() {
            public List<String> compute() {
                return getAnnotationTexts(method);
            }
        });

        SearchScope searchScope = ApplicationManager.getApplication().runReadAction(new Computable<SearchScope>() {
            public SearchScope compute() {
                return queryParameters.getEffectiveSearchScope();
            }
        });

        boolean result = true;

        for (String stepText : stepTexts) {
            if (stepText == null) {
                return true;
            }

            result &= findJBehaveReferencesToElement(myElement, stepText, consumer, searchScope);
        }

        return result;
    }
}
