package com.github.kumaraman21.intellijbehave.runner.loader;

import org.jbehave.core.io.LoadFromClasspath;

import java.io.IOException;
import java.io.InputStream;

public final class ClassPathStoryLoader extends AbstractStoryLoadersChain {

    public ClassPathStoryLoader() {
        super(new LoadFromClasspath());
    }

    @Override
    protected boolean canLoadStory(String path) {
        return canLoad(path);
    }

    @Override
    protected boolean canLoadResource(String path) {
        return canLoad(path);
    }

    private static boolean canLoad(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(path)) {
            return is != null;
        } catch (IOException e) {
            return false;
        }
    }
}
