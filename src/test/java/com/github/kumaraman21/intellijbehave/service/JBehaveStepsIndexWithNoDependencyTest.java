package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import com.intellij.testFramework.junit5.RunInEdt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Functional test for {@link JBehaveStepsIndex}.
 */
@RunInEdt
class JBehaveStepsIndexWithNoDependencyTest extends ContentEntryTestBase {

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/stepsindex";
    }

    @Override
    protected void loadLibraries() {
        //Doesn't load anything to emulate the missing JBehave
    }

    @Test
    void shouldFindNoStepDefinitionDueToNoJBehaveAnnotationsAvailable() {
        getFixture().copyFileToProject("main/java/StepDefs.java");
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().configureByFile("test/resources/has_java_step_def.story");

        JBehaveStep step = (JBehaveStep) getFixture().getFile().findElementAt(getFixture().getCaretOffset()).getParent();
        var stepDefinitions = JBehaveStepsIndex.getInstance(getFixture().getProject()).findStepDefinitions(step);

        assertThat(stepDefinitions).isEmpty();
    }
}
