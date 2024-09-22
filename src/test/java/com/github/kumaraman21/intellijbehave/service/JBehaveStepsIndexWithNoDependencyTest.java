package com.github.kumaraman21.intellijbehave.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.kumaraman21.intellijbehave.ContentEntryProjectDescriptor;
import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.github.kumaraman21.intellijbehave.parser.JBehaveStep;
import org.junit.jupiter.api.Test;

/**
 * Functional test for {@link JBehaveStepsIndex}.
 */
class JBehaveStepsIndexWithNoDependencyTest extends ContentEntryTestBase {

    public JBehaveStepsIndexWithNoDependencyTest() {
        //Doesn't load jbehave-core to emulate the missing JBehave
        super(new ContentEntryProjectDescriptor());
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData/stepsindex";
    }

    @Test
    void shouldFindNoStepDefinitionDueToNoJBehaveAnnotationsAvailable() {
        getFixture().copyFileToProject("src/main/java/StepDefs.java");
        getFixture().copyFileToProject("src/main/java/OtherStepDefs.java");
        getFixture().configureByFile("src/test/resources/has_java_step_def.story");

        JBehaveStep step = (JBehaveStep) getParentOfElementAtCaretIn(getFixture().getFile());
        var stepDefinitions = JBehaveStepsIndex.getInstance(getFixture().getProject()).findStepDefinitions(step);

        assertThat(stepDefinitions).isEmpty();
    }
}
