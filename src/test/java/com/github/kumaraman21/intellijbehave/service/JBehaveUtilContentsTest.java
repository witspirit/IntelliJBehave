package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link JBehaveUtil}.
 */
class JBehaveUtilContentsTest extends ContentEntryTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/service/jbehaveutil";
    }

    //findJBehaveReferencesToElement

    @Test
    void shouldContinueReferenceSearchForEmptyBiggestWord() {
        copySrcDirectoryToProject();
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");

        var stepDefMethod = getParentOfElementAtCaretIn(stepDefFile);

        boolean findRef = JBehaveUtil.findJBehaveReferencesToElement(
            stepDefMethod,
            "",
            __ -> true,
            GlobalSearchScope.projectScope(getFixture().getProject()));
        assertThat(findRef).isTrue();
    }

    @Test
    void shouldContinueReferenceSearchForValidMethod() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("src/test/resources/reference.story");
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");

        var stepDefMethod = getParentOfElementAtCaretIn(stepDefFile);

        boolean findRef = JBehaveUtil.findJBehaveReferencesToElement(
            stepDefMethod,
            "search for $string",
            __ -> true,
            GlobalSearchScope.projectScope(getFixture().getProject()));
        assertThat(findRef).isTrue();
    }

    @Test
    void shouldNotContinueReferenceSearchForValidMethodWithFalseConsumer() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("src/test/resources/reference.story");
        var stepDefFile = getFixture().configureByFile("main/java/StepDefs.java");

        var stepDefMethod = getParentOfElementAtCaretIn(stepDefFile);

        boolean findRef = JBehaveUtil.findJBehaveReferencesToElement(
            stepDefMethod,
            "search for $string",
            __ -> false,
            GlobalSearchScope.projectScope(getFixture().getProject()));
        assertThat(findRef).isFalse();
    }

    @Test
    void shouldContinueReferenceSearchForMethodWithNoReference() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("src/test/resources/reference.story");
        var stepDefFile = getFixture().configureByFile("main/java/OtherStepDefs.java");

        var stepDefMethod = getParentOfElementAtCaretIn(stepDefFile);

        boolean findRef = JBehaveUtil.findJBehaveReferencesToElement(
            stepDefMethod,
            "result list size is $size",
            __ -> true,
            GlobalSearchScope.projectScope(getFixture().getProject()));
        assertThat(findRef).isTrue();
    }
}
