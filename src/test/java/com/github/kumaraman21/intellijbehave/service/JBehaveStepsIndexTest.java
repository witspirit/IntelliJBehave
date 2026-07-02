package com.github.kumaraman21.intellijbehave.service;

import static com.intellij.openapi.application.ReadAction.computeBlocking;
import static com.intellij.util.containers.ContainerUtil.map2Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.impl.java.stubs.index.JavaFullClassNameIndex;
import org.junit.jupiter.api.Test;

/**
 * Functional test for {@link JBehaveStepsIndex}.
 */
class JBehaveStepsIndexTest extends ContentEntryTestBase {

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/stepsindex";
    }

    // findStepDefinitions

    @Test
    void shouldFindsSingleStepDefinition() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/StepDefs.java");
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");

        getFixture().configureByFile("test/resources/has_java_step_def.story");

        var stepDefinitions = JBehaveStepsIndex.getInstance(getFixture().getProject()).findStepDefinitions(getStep());

        assertThat(stepDefinitions).hasSize(1);
        assertThat(stepDefinitions.iterator().next().getAnnotatedMethod().getContainingClass().getQualifiedName()).isEqualTo("StepDefs");
        assertThat(computeBlocking(() -> stepDefinitions.iterator().next().getAnnotatedMethod().getSignature(PsiSubstitutor.EMPTY).toString()))
            .isEqualTo("MethodSignatureBackedByPsiMethod: openAUrl([PsiType:String])");
    }

    //NOTE: at the moment, this only returns the first found step definition, regardless of the step pattern
    // existing for multiple different step types, e.g. for a @Given and a @Then step def, and regardless of step priority.
    //Supporting returning multiple matching step definitions should be revisited.
    @Test
    void shouldFindsMultipleStepDefinitions() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/StepDefs.java");
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().configureByFile("test/resources/has_multiple_java_step_def.story");

        var stepDefinitions = JBehaveStepsIndex.getInstance(getFixture().getProject()).findStepDefinitions(getStep());

        assertThat(stepDefinitions).hasSize(1);
        assertThat(stepDefinitions.iterator().next().getAnnotatedMethod().getContainingClass().getQualifiedName()).isEqualTo("OtherStepDefs");
        assertThat(computeBlocking(() -> stepDefinitions.iterator().next().getAnnotatedMethod().getSignature(PsiSubstitutor.EMPTY)))
            .hasToString("MethodSignatureBackedByPsiMethod: checkResultListSize([PsiType:int])");
    }

    @Test
    void shouldFindsNoStepDefinition() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/StepDefs.java");
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().configureByFile("test/resources/has_no_java_step_def.story");

        var stepDefinitions = JBehaveStepsIndex.getInstance(getFixture().getProject()).findStepDefinitions(getStep());

        assertThat(stepDefinitions).isEmpty();
    }

    //getAllStepAnnotations

    @Test
    void shouldFindAllAnnotations() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/StepDefs.java");
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("main/kotlin/AnotherStepDefs.kt");

        getFixture().configureByFile("test/resources/has_java_step_def.story");

        var scope = getFixture().getModule().getModuleWithDependenciesAndLibrariesScope(true);
        var thenAnnotations = computeBlocking(() -> JavaFullClassNameIndex.getInstance().getClasses("org.jbehave.core.annotations.Then", getFixture().getProject(), scope));
        if (thenAnnotations.isEmpty()) fail("The @Then step def annotation was not found.");

        var stepDefinitions = JBehaveStepsIndex.getAllStepAnnotations(thenAnnotations.iterator().next(), scope);

        assertThat(stepDefinitions).hasSize(3);
        var stepTexts = map2Set(stepDefinitions, annotation -> computeBlocking(() -> AnnotationUtil.getStringAttributeValue(annotation, "value")));

        assertThat(stepTexts).containsExactlyInAnyOrder(
            "result ends with $text",
            "check result size is $size",
            "result list size is $size");
    }

    private JBehaveStep getStep() {
        return (JBehaveStep) getParentOfElementAtCaretIn(getFixture().getFile());
    }
}
