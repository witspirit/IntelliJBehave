package com.github.kumaraman21.intellijbehave.codeInspector;

import com.intellij.codeInspection.InspectionProfileEntry;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link UndefinedStepInspection}.
 */
class UndefinedStepInspectionTest extends ContentEntryInspectionTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/codeinspector/undefinedstep";
    }

    @Override
    protected InspectionProfileEntry getInspection() {
        return new UndefinedStepInspection();
    }

    @Test
    void highlightingUndefinedSteps() {
        copySrcDirectoryToProject();
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("main/java/StepDefs.java");
        doTest("src/test/resources/undefined_steps.story");
    }
}
