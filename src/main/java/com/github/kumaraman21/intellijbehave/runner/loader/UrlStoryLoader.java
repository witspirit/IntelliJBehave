package com.github.kumaraman21.intellijbehave.runner.loader;

import org.jbehave.core.io.LoadFromURL;

import java.net.URI;

public final class UrlStoryLoader extends AbstractStoryLoadersChain {

    public UrlStoryLoader() {
        super(new LoadFromURL());
    }

    @Override
    protected boolean canLoadStory(String path) {
        return canLoad(path);
    }

    @Override
    protected boolean canLoadResource(String path) {
        return canLoad(path);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static boolean canLoad(String path) {
        try {
            new URI(path).toURL();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
