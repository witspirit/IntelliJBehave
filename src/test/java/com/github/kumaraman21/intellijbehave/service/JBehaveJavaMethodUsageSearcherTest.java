package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.MethodReferencesSearch;
import com.intellij.testFramework.junit5.RunInEdt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link JBehaveJavaMethodUsageSearcher}.
 */
@RunInEdt
class JBehaveJavaMethodUsageSearcherTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/service/javamethodusagesearch";
    }

    @Test
    void shouldReturnTrueIfThereIsNoStepText() {
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var method = (PsiMethod) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        var queryParameters = new MethodReferencesSearch.SearchParameters(method, GlobalSearchScope.projectScope(getFixture().getProject()), false);

        var ref = new Ref<Boolean>();
        new JBehaveJavaMethodUsageSearcher().processQuery(queryParameters, reference -> {
            ref.set(true);
            return true;
        });

        assertThat(ref.get()).isNull();
    }

    @Test
    void shouldNotProcessQueryIfStepTextIsEmpty() {
        var stepDefFile = getFixture().configureByFile("main/java/MoreStepDefs.java");
        var method = (PsiMethod) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        var queryParameters = new MethodReferencesSearch.SearchParameters(method, GlobalSearchScope.projectScope(getFixture().getProject()), false);

        var ref = new Ref<Boolean>();
        new JBehaveJavaMethodUsageSearcher().processQuery(queryParameters, reference -> {
            ref.set(true);
            return true;
        });

        assertThat(ref.get()).isNull();
    }

    @Test
    void shouldNotProcessQueryIfSearchScopeIsNotGlobal() {
        var stepDefFile = getFixture().configureByFile("main/java/OtherStepDefs.java");
        var method = (PsiMethod) stepDefFile.findElementAt(getFixture().getCaretOffset()).getParent();
        var queryParameters = new MethodReferencesSearch.SearchParameters(method, new DummyScope(), false);

        var ref = new Ref<Boolean>();
        new JBehaveJavaMethodUsageSearcher().processQuery(queryParameters, reference -> {
            ref.set(true);
            return true;
        });

        assertThat(ref.get()).isNull();
    }

//    @Test
//    void shouldProcessQuery() {
//    }

    private static final class DummyScope extends SearchScope {
        @Override
        public @NotNull SearchScope intersectWith(@NotNull SearchScope scope2) {
            return scope2;
        }

        @Override
        public @NotNull SearchScope union(@NotNull SearchScope scope) {
            return scope;
        }

        @Override
        public boolean contains(@NotNull VirtualFile file) {
            return false;
        }
    }
}
