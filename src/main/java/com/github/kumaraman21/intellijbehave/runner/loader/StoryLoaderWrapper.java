package com.github.kumaraman21.intellijbehave.runner.loader;

import org.jbehave.core.io.StoryLoader;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class StoryLoaderWrapper extends AbstractStoryLoadersChain {

    public StoryLoaderWrapper(@NotNull StoryLoader delegate) {
        super(delegate);
    }

    @Override
    protected final boolean canLoadStory(String path) {
        return canLoad(() -> getDelegate().loadStoryAsText(path));
    }

    @Override
    protected final boolean canLoadResource(String path) {
        return canLoad(() -> getDelegate().loadResourceAsText(path));
    }

    private static boolean canLoad(Supplier<String> supplier) {
        try {
            return supplier.get() != null;
        } catch (Exception e) {
            return false;
        }
    }
}
