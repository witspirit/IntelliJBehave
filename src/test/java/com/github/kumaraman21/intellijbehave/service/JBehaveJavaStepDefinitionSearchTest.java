package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link JBehaveJavaStepDefinitionSearch}.
 */
class JBehaveJavaStepDefinitionSearchTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/service/stepdefsearch";
    }

    @Test
    void shouldReturnTrueForNonPsiMethodElement() {
        copySrcDirectoryToProject();
        var stepDefFile = getFixture().configureByFile("main/java/OtherStepDefs.java");
        var element = stepDefFile.findElementAt(getCaretOffset());
        var queryParameters = new ReferencesSearch.SearchParameters(element, GlobalSearchScope.projectScope(getFixture().getProject()), false);

        boolean shouldContinue = new JBehaveJavaStepDefinitionSearch().execute(queryParameters, __ -> true);

        assertThat(shouldContinue).isTrue();
    }

    @Test
    void shouldReturnTrueForNonStepDefinitionMethod() {
        copySrcDirectoryToProject();
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");
        var method = (PsiMethod) getParentOfElementAtCaretIn(stepDefFile);
        var queryParameters = new ReferencesSearch.SearchParameters(method, GlobalSearchScope.projectScope(getFixture().getProject()), false);

        boolean shouldContinue = new JBehaveJavaStepDefinitionSearch().execute(queryParameters, __ -> true);

        assertThat(shouldContinue).isTrue();
    }

//    @Test
//    void shouldReturnTrueIfThereIsNoStepText() { }

//    @Test
//    void shouldReturnTrueIfThereIsANullStepTextForNonStepDefinitionMethod() { }

    @Test
    void shouldReturnTrueForValidStepDefinitionMethod() {
        copySrcDirectoryToProject();
        var stepDefFile = getFixture().configureByFile("main/java/MoreStepDefs.java");
        var method = (PsiMethod) getParentOfElementAtCaretIn(stepDefFile);
        var queryParameters = new ReferencesSearch.SearchParameters(method, GlobalSearchScope.projectScope(getFixture().getProject()), false);

        boolean shouldContinue = new JBehaveJavaStepDefinitionSearch().execute(queryParameters, ref -> true);

        assertThat(shouldContinue).isTrue();
    }

    @Test
    void shouldReturnFalseForValidStepDefinitionMethodAndFalseConsumer() {
        copySrcDirectoryToProject();
        var stepDefFile = getFixture().configureByFile("main/java/MoreStepDefs.java");
        var method = (PsiMethod) getParentOfElementAtCaretIn(stepDefFile);
        var queryParameters = new ReferencesSearch.SearchParameters(method, GlobalSearchScope.projectScope(getFixture().getProject()), false);

        boolean shouldContinue = new JBehaveJavaStepDefinitionSearch().execute(queryParameters, ref -> false);

        assertThat(shouldContinue).isFalse();
    }
}
