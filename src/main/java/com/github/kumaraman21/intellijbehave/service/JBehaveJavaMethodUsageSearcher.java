package com.github.kumaraman21.intellijbehave.service;

import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getAnnotationTexts;
import static com.github.kumaraman21.intellijbehave.service.JBehaveUtil.getTheBiggestWordToSearchByIndex;
import static com.intellij.openapi.application.ReadAction.computeBlocking;
import static com.intellij.openapi.util.text.StringUtil.isNotEmpty;
import static com.intellij.util.containers.ContainerUtil.exists;

import com.github.kumaraman21.intellijbehave.language.StoryFileType;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.MethodReferencesSearch.SearchParameters;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.Query;
import org.jetbrains.annotations.NotNull;

public class JBehaveJavaMethodUsageSearcher extends QueryExecutorBase<PsiReference, SearchParameters> {

    @Override
    public void processQuery(@NotNull SearchParameters queryParameters, @NotNull Processor<? super PsiReference> consumer) {
        if (queryParameters.getScopeDeterminedByUser() instanceof GlobalSearchScope scopeByUser) {
            final PsiMethod method = queryParameters.getMethod();

            boolean hasNonEmptyBiggestWord = exists(
                computeBlocking(() -> getAnnotationTexts(method)),
                stepText -> isNotEmpty(getTheBiggestWordToSearchByIndex(stepText)));

            //Originally, this was executed for each non-empty biggest word, but since it performed the same reference search query, it became deduplicated.
            if (hasNonEmptyBiggestWord) {
                var restrictedScope = GlobalSearchScope.getScopeRestrictedByFileTypes(scopeByUser, StoryFileType.STORY_FILE_TYPE);
                Query<PsiReference> query = ReferencesSearch.search(new ReferencesSearch.SearchParameters(method, restrictedScope, false, queryParameters.getOptimizer()));
                //This, behind the scenes, will also call into JBehaveJavaStepDefinitionSearch#execute
                query.forEach(consumer);
            }
        }
    }
}
