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

        Boolean isStepDefinition = ApplicationManager.getApplication().runReadAction(new BooleanComputable(method));

        if (!isStepDefinition) {
            return true;
        }

        List<String> stepTexts = ApplicationManager.getApplication().runReadAction(new ListComputable(method));

        SearchScope searchScope =
                ApplicationManager.getApplication().runReadAction(new SearchScopeComputable(queryParameters));

        boolean result = true;

        for (String stepText : stepTexts) {
            if (stepText == null) {
                return true;
            }

            result &= findJBehaveReferencesToElement(myElement, stepText, consumer, searchScope);
        }

        return result;
    }

    private static class SearchScopeComputable implements Computable<SearchScope> {
        private final SearchParameters queryParameters;

        public SearchScopeComputable(SearchParameters queryParameters) {
            this.queryParameters = queryParameters;
        }

        public SearchScope compute() {
            return queryParameters.getEffectiveSearchScope();
        }
    }

    private static class ListComputable implements Computable<List<String>> {
        private final PsiMethod method;

        public ListComputable(PsiMethod method) {
            this.method = method;
        }

        public List<String> compute() {
            return getAnnotationTexts(method);
        }
    }

    private static class BooleanComputable implements Computable<Boolean> {
        private final PsiMethod method;

        public BooleanComputable(PsiMethod method) {
            this.method = method;
        }

        public Boolean compute() {
            return isStepDefinition(method);
        }
    }
}
