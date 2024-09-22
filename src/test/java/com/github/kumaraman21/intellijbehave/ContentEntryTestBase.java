package com.github.kumaraman21.intellijbehave;

import static com.intellij.openapi.application.ApplicationManager.getApplication;

import com.intellij.openapi.application.ModalityState;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;

/**
 * Base class for tests using content entries.
 */
public abstract class ContentEntryTestBase extends JBehaveSupportTestBase {

    public ContentEntryTestBase() {
        super(new ContentEntryProjectDescriptor().withRepositoryLibrary("org.jbehave:jbehave-core:5.2.0"));
    }

    public ContentEntryTestBase(DefaultLightProjectDescriptor projectDescriptor) {
        super(projectDescriptor);
    }

    /**
     * The directory has to be copied in each related test method, instead of in a before hooks,
     * so that it is called properly on EDT in write-safe context.
     */
    protected void copySrcDirectoryToProject() {
        getApplication().invokeAndWait(() -> getFixture().copyDirectoryToProject("src", ""), ModalityState.nonModal());
    }
}
