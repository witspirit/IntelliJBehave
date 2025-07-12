package com.github.kumaraman21.intellijbehave.service;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.findJBehaveReferencesToElement;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getAnnotationTexts;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.isStepDefinition;
import static com.intellij.openapi.application.ReadAction.compute;

import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch.SearchParameters;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * Provides Story step references for JBehave Java step definition methods.
 */
public class JBehaveJavaStepDefinitionSearch implements QueryExecutor<PsiReference, SearchParameters> {

    @Override
    public boolean execute(@NotNull SearchParameters queryParameters, @NotNull Processor<? super PsiReference> consumer) {
        if (!(queryParameters.getElementToSearch() instanceof PsiMethod method) || !compute(() -> isStepDefinition(method))) {
            return true;
        }

        SearchScope searchScope = null;
        boolean result = true;

        for (String stepText : compute(() -> getAnnotationTexts(method))) {
            if (stepText == null) {
                return true;
            }

            //Lazy-initializing the search scope in case the first step text is null
            if (searchScope == null) {
                searchScope = JBehaveUtil.restrictScopeToJBehaveFiles(compute(queryParameters::getEffectiveSearchScope));
            }

            result &= findJBehaveReferencesToElement(method, stepText, consumer, searchScope);
        }

        return result;
    }
}
