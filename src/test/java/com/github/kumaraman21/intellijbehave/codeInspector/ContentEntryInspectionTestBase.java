package com.github.kumaraman21.intellijbehave.codeInspector;

import static com.intellij.openapi.application.ApplicationManager.getApplication;

import com.github.kumaraman21.intellijbehave.ContentEntryProjectDescriptor;
import com.github.kumaraman21.intellijbehave.JBehaveSupportTestBase;
import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.openapi.application.ModalityState;
import com.intellij.testFramework.TestDataFile;

/**
 * Base test class for inspections using content entries.
 */
abstract class ContentEntryInspectionTestBase extends JBehaveSupportTestBase {

    public ContentEntryInspectionTestBase() {
        super(new ContentEntryProjectDescriptor().withRepositoryLibrary("org.jbehave:jbehave-core:5.2.0"));
    }

    /**
     * Configures the inspection to be tested.
     */
    protected abstract InspectionProfileEntry getInspection();

    /**
     * The directory has to be copied in each related test method, instead of in a before hooks,
     * so that it is called properly on EDT in write-safe context.
     */
    protected void copySrcDirectoryToProject() {
        getApplication().invokeAndWait(() -> getFixture().copyDirectoryToProject("src", ""), ModalityState.nonModal());
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
