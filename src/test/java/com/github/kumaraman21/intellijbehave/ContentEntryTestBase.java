package com.github.kumaraman21.intellijbehave;

import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for tests using content entries.
 */
public abstract class ContentEntryTestBase extends JBehaveSupportTestBase {

    public ContentEntryTestBase() {
        super(new ContentEntryProjectDescriptor());
    }

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        getFixture().copyDirectoryToProject("src", "");
    }
}
