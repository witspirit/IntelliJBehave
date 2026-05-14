package com.github.kumaraman21.intellijbehave.runner;

import com.github.kumaraman21.intellijbehave.ContentEntryTestBase;
import com.github.kumaraman21.intellijbehave.execution.JBehaveConfigurationOptions;
import com.intellij.openapi.roots.OrderEnumerator;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Paths;

public class JBehaveStoryRunnerTest extends ContentEntryTestBase {

    private static ThrowingCallable runStoryRunner(String[] args) {
        return () -> {
            var arguments = Arguments.parse(args);
            try (var runner = JBehaveStoryRunner.create(arguments)) {
                runner.run();
            }
        };
    }

    @Override
    protected @Nullable String getTestDataPath() {
        return "src/test/testData/runner/runconfiguration";
    }

    @BeforeEach
    public void setup() {
        copySrcDirectoryToProject();
        getFixture().configureByFile("main/java/StepDefs.java");
        getFixture().copyFileToProject("test/resources/Stories/test.story");
    }

    @ParameterizedTest
    @MethodSource("com.github.kumaraman21.intellijbehave.runner.EmbedderSamples#provideDefaultEmbedder")
    @MethodSource("com.github.kumaraman21.intellijbehave.runner.EmbedderSamples#provideCustomEmbedders")
    public void testRunnerShouldWorkWithDefaultEmbedder(String embedderClassName) {
        var storyFinderClassName = JBehaveConfigurationOptions.DEFAULT_STORY_FINDER_CLASS_NAME;
        var storyPath = getStoryPath();
        var classPathElement = getClassPathElements();

        var arguments = new String[]{
            Arguments.CLASS_PATH_ELEMENTS_PARAMETER_NAME, classPathElement,
            Arguments.EMBEDDER_CLASS_PARAMETER_NAME, embedderClassName,
            Arguments.STORY_FINDER_CLASS_PARAMETER_NAME, storyFinderClassName,
            Arguments.STORY_PATH_PARAMETER_NAME, storyPath
        };

        Assertions.assertThatCode(runStoryRunner(arguments)).doesNotThrowAnyException();
    }

    @Test
    public void testRunnerShouldThrowExceptionForInvalidStoryPath() {
        var embedderClassName = JBehaveConfigurationOptions.DEFAULT_EMBEDDER_CLASS_NAME;
        var storyFinderClassName = JBehaveConfigurationOptions.DEFAULT_STORY_FINDER_CLASS_NAME;
        var storyPath = "invalid/path/to/story.story";

        var arguments = new String[]{
            Arguments.EMBEDDER_CLASS_PARAMETER_NAME, embedderClassName,
            Arguments.STORY_FINDER_CLASS_PARAMETER_NAME, storyFinderClassName,
            Arguments.STORY_PATH_PARAMETER_NAME, storyPath
        };

        Assertions.assertThatThrownBy(runStoryRunner(arguments))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Story path does not exist " + storyPath);
    }

    @Test
    public void testRunnerShouldThrowExceptionForNonExistentEmbedderClass() {
        var embedderClassName = "com.example.NonExistentEmbedder";
        var storyFinderClassName = JBehaveConfigurationOptions.DEFAULT_STORY_FINDER_CLASS_NAME;
        var storyPath = getStoryPath();

        var arguments = new String[]{
            Arguments.EMBEDDER_CLASS_PARAMETER_NAME, embedderClassName,
            Arguments.STORY_FINDER_CLASS_PARAMETER_NAME, storyFinderClassName,
            Arguments.STORY_PATH_PARAMETER_NAME, storyPath
        };

        Assertions.assertThatThrownBy(runStoryRunner(arguments))
            .isInstanceOf(JBehaveRunException.class)
            .hasMessage("Failed to load embedder class: " + embedderClassName)
            .hasRootCauseInstanceOf(ClassNotFoundException.class)
            .hasRootCauseMessage(embedderClassName);
    }

    @Test
    public void testRunnerShouldThrowExceptionForInvalidEmbedderClass() {
        var embedderClassName = Object.class.getName();
        var storyFinderClassName = JBehaveConfigurationOptions.DEFAULT_STORY_FINDER_CLASS_NAME;
        var storyPath = getStoryPath();

        var arguments = new String[]{
            Arguments.EMBEDDER_CLASS_PARAMETER_NAME, embedderClassName,
            Arguments.STORY_FINDER_CLASS_PARAMETER_NAME, storyFinderClassName,
            Arguments.STORY_PATH_PARAMETER_NAME, storyPath
        };

        Assertions.assertThatThrownBy(runStoryRunner(arguments)).isInstanceOf(ClassCastException.class);
    }

    @Test
    public void testRunnerShouldThrowExceptionForInvalidStoryFinderClass() {
        var embedderClassName = JBehaveConfigurationOptions.DEFAULT_EMBEDDER_CLASS_NAME;
        var storyFinderClassName = "com.example.NonExistentStoryFinder";
        var storyPath = getStoryFolderPath();

        var arguments = new String[]{
            Arguments.EMBEDDER_CLASS_PARAMETER_NAME, embedderClassName,
            Arguments.STORY_FINDER_CLASS_PARAMETER_NAME, storyFinderClassName,
            Arguments.STORY_PATH_PARAMETER_NAME, storyPath
        };

        Assertions.assertThatThrownBy(runStoryRunner(arguments))
            .isInstanceOf(JBehaveRunException.class)
            .hasMessage("Failed to instantiate story finder class: " + storyFinderClassName)
            .hasRootCauseInstanceOf(ClassNotFoundException.class)
            .hasRootCauseMessage(storyFinderClassName);
    }

    private String getStoryPath() {
        return Paths.get(getTestDataPath() + "/src/test/resources/Stories/test.story")
            .toAbsolutePath()
            .toString();
    }

    private String getStoryFolderPath() {
        return Paths.get(getTestDataPath() + "/src/test/resources/Stories")
            .toAbsolutePath()
            .toString();
    }

    private String getClassPathElements() {
        return OrderEnumerator.orderEntries(getFixture().getModule())
            .recursively()
            .getPathsList()
            .getPathList()
            .stream()
            .toString();
    }
}
