package com.github.kumaraman21.intellijbehave.execution;

import com.intellij.execution.application.JvmMainMethodRunConfigurationOptions;
import com.intellij.openapi.components.StoredProperty;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.StoryFinder;
import org.jetbrains.annotations.Nullable;

public class JBehaveConfigurationOptions extends JvmMainMethodRunConfigurationOptions {

    public static final String DEFAULT_EMBEDDER_CLASS_NAME = Embedder.class.getName();
    public static final String DEFAULT_FOLDER_INCLUSIONS_PATTERN = "**/*.story";
    public static final String DEFAULT_STORY_FINDER_CLASS_NAME = StoryFinder.class.getName();

    private final StoredProperty<String> embedderClass = string(DEFAULT_EMBEDDER_CLASS_NAME)
        .provideDelegate(this, "embedderClass");

    private final StoredProperty<String> filePath = string("")
        .provideDelegate(this, "filePath");

    private final StoredProperty<String> metaFilters = string("")
        .provideDelegate(this, "metaFilters");

    private final StoredProperty<String> inclusions = string(DEFAULT_FOLDER_INCLUSIONS_PATTERN)
        .provideDelegate(this, "inclusions");

    private final StoredProperty<String> exclusions = string("")
        .provideDelegate(this, "exclusions");

    private final StoredProperty<String> storyFinderClass = string(DEFAULT_STORY_FINDER_CLASS_NAME)
        .provideDelegate(this, "storyFinderClass");

    public void setEmbedderClass(String className) {
        embedderClass.setValue(this, className);
    }

    public String getEmbedderClass() {
        return embedderClass.getValue(this);
    }

    public void setFilePath(String path) {
        filePath.setValue(this, path);
    }

    public String getFilePath() {
        return filePath.getValue(this);
    }

    public void setMetaFilters(String filters) {
        metaFilters.setValue(this, filters);
    }

    public String getMetaFilters() {
        return metaFilters.getValue(this);
    }

    public void setInclusions(String patterns) {
        inclusions.setValue(this, patterns);
    }

    public String getInclusions() {
        return inclusions.getValue(this);
    }

    public void setExclusions(String patterns) {
        exclusions.setValue(this, patterns);
    }

    public String getExclusions() {
        return exclusions.getValue(this);
    }

    public void setStoryFinderClass(String className) {
        storyFinderClass.setValue(this, className);
    }

    public String getStoryFinderClass() {
        return storyFinderClass.getValue(this);
    }

    public static boolean isDefaultEmbedderClass(@Nullable String embedderClassName) {
        return DEFAULT_EMBEDDER_CLASS_NAME.equals(embedderClassName);
    }
}
