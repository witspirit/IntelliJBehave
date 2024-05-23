package com.github.kumaraman21.intellijbehave.codeInspector;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.testFramework.junit5.RunInEdt;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

/**
 * Integration test for {@link UnusedStepDeclarationInspection}.
 */
@RunInEdt
class UnusedStepDeclarationInspectionTest extends ContentEntryInspectionTestBase {

    @Nullable
    @Override
    protected String getTestDataPath() {
        return "src/test/testData/codeinspector/unusedstepdeclarations";
    }

    @Override
    protected InspectionProfileEntry getInspection() {
        return new UnusedStepDeclarationInspection();
    }

    @Test
    void highlightingUnusedStepDeclaration() {
        getFixture().copyFileToProject("main/java/OtherStepDefs.java");
        getFixture().copyFileToProject("test/resources/unused_step_declarations.story");
        doTest("main/java/StepDefs.java");
    }
}
