package com.github.kumaraman21.intellijbehave.runner.loader;

import org.apache.commons.lang3.Validate;
import org.jbehave.core.io.StoryLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public abstract class AbstractStoryLoadersChain implements StoryLoadersChain {

    private static final String STORY_TYPE = "story";
    private static final String RESOURCE_TYPE = "resource";

    private final StoryLoader delegate;
    private StoryLoadersChain next;

    protected AbstractStoryLoadersChain(@NotNull StoryLoader delegate) {
        this.delegate = Validate.notNull(delegate, "StoryLoader delegate cannot be null");
    }

    public StoryLoader getDelegate() {
        return delegate;
    }

    @Override
    public void setNext(@Nullable StoryLoadersChain chain) {
        this.next = chain;
    }

    @Override
    public String loadStoryAsText(String storyPath) {
        if (canLoadStory(storyPath)) {
            return load(STORY_TYPE, storyPath, delegate::loadStoryAsText);
        } else if (next != null) {
            return next.loadStoryAsText(storyPath);
        } else {
            throw unavailableException(STORY_TYPE, storyPath);
        }
    }

    @Override
    public String loadResourceAsText(String resourcePath) {
        if (canLoadResource(resourcePath)) {
            return load(RESOURCE_TYPE, resourcePath, delegate::loadResourceAsText);
        } else if (next != null) {
            return next.loadResourceAsText(resourcePath);
        } else {
            throw unavailableException(RESOURCE_TYPE, resourcePath);
        }
    }

    private static String load(String type, String path, Function<String, String> function) {
        try {
            return function.apply(path);
        } catch (Exception e) {
            throw new StoryResourceException(String.format("Failed to load %s from path: %s", type, path), e);
        }
    }

    private static StoryResourceException unavailableException(String type, String path) {
        return new StoryResourceException(String.format("Unable to load %s from path: %s", type, path));
    }

    protected abstract boolean canLoadStory(String path);

    protected abstract boolean canLoadResource(String path);
}
