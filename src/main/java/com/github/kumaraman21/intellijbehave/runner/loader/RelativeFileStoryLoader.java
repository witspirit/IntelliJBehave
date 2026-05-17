package com.github.kumaraman21.intellijbehave.runner.loader;

import org.jbehave.core.io.LoadFromRelativeFile;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

public final class RelativeFileStoryLoader extends StoryLoaderWrapper {

    public RelativeFileStoryLoader(@NotNull URL location) {
        this(new LoadFromRelativeFile(location));
    }

    public RelativeFileStoryLoader(@NotNull LoadFromRelativeFile delegate) {
        super(delegate);
    }
}
