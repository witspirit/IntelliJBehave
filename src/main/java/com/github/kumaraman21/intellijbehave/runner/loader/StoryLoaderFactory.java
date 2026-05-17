package com.github.kumaraman21.intellijbehave.runner.loader;

import com.github.kumaraman21.intellijbehave.runner.JBehaveStoryLoader;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.LoadFromRelativeFile;
import org.jbehave.core.io.LoadFromURL;
import org.jbehave.core.io.StoryLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StoryLoaderFactory {

    private StoryLoaderFactory() {
    }

    public static StoryLoader createLoader(@NotNull Configuration configuration) {
        StoryLoadersChain chain = new UrlStoryLoader();
        chain.setNext(fallbackLoader(configuration));
        return new JBehaveStoryLoader(chain);
    }

    @Nullable
    private static StoryLoadersChain fallbackLoader(@NotNull Configuration configuration) {
        return switch (configuration.storyLoader()) {
            case null -> null;
            case LoadFromURL ignore -> null; // we don't need two URL story loaders
            case LoadFromRelativeFile delegate -> new RelativeFileStoryLoader(delegate);
            case LoadFromClasspath ignore -> new ClassPathStoryLoader();
            case StoryLoader delegate -> new StoryLoaderWrapper(delegate);
        };
    }
}
