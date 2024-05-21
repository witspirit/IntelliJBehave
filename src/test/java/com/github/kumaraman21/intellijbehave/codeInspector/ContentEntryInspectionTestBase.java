package com.github.kumaraman21.intellijbehave.codeInspector;

import com.github.kumaraman21.intellijbehave.ContentEntryProjectDescriptor;
import com.github.kumaraman21.intellijbehave.JBehaveSupportTestBase;
import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.testFramework.TestDataFile;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base test class for inspections using content entries.
 */
abstract class ContentEntryInspectionTestBase extends JBehaveSupportTestBase {

    public ContentEntryInspectionTestBase() {
        super(new ContentEntryProjectDescriptor());
    }

    /**
     * Configures the inspection to be tested.
     */
    protected abstract InspectionProfileEntry getInspection();

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        getFixture().copyDirectoryToProject("src", "");
    }

    /**
     * Tests inspection highlighting in the file located at the specified path.
     *
     * @param filePath the path to the file relative to the path returned by {@link #getTestDataPath()}
     */
    protected void doTest(@TestDataFile String filePath) {
        getFixture().configureByFile(filePath);
        getFixture().enableInspections(getInspection());
        getFixture().testHighlighting(true, false, true);
    }
}
