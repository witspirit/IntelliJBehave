package com.github.kumaraman21.intellijbehave.service;

import com.github.kumaraman21.intellijbehave.language.JBehaveFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.MethodReferencesSearch.SearchParameters;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getAnnotationTexts;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getTheBiggestWordToSearchByIndex;
import static com.intellij.openapi.util.text.StringUtil.isNotEmpty;

public class JavaMethodUsageSearcher extends QueryExecutorBase<PsiReference, SearchParameters> {
    public void processQuery(@NotNull SearchParameters searchParameters, @NotNull Processor<PsiReference> consumer) {
        final PsiMethod method = searchParameters.getMethod();

        List<String> stepTexts = ApplicationManager.getApplication().runReadAction(new Computable<List<String>>() {
            public List<String> compute() {
                return getAnnotationTexts(method);
            }
        });

        for (String stepText : stepTexts) {
            String word = getTheBiggestWordToSearchByIndex(stepText);
            if (isNotEmpty(word)) {
                if (searchParameters.getScopeDeterminedByUser() instanceof GlobalSearchScope) {
                    GlobalSearchScope restrictedScope = GlobalSearchScope.getScopeRestrictedByFileTypes(
                            (GlobalSearchScope) searchParameters.getScopeDeterminedByUser(),
                            JBehaveFileType.JBEHAVE_FILE_TYPE);
                    Query<PsiReference> query = ReferencesSearch.search(
                            new ReferencesSearch.SearchParameters(method, restrictedScope, false,
                                    searchParameters.getOptimizer()));
                    query.forEach(consumer);
                }
            }
        }
    }
}
