package com.github.kumaraman21.intellijbehave.runner.internal;

import com.github.kumaraman21.intellijbehave.runner.Arguments;
import com.github.kumaraman21.intellijbehave.runner.JBehaveRunException;
import com.github.kumaraman21.intellijbehave.utility.PathUtils;
import org.apache.commons.lang3.Validate;
import org.jbehave.core.embedder.EmbedderClassLoader;
import org.jbehave.core.io.StoryFinder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class StoryPathResolverImpl implements StoryPathResolver {

    private final EmbedderClassLoader classLoader;

    public StoryPathResolverImpl(@NotNull EmbedderClassLoader classLoader) {
        this.classLoader = Validate.notNull(classLoader, "ClassLoader cannot be null");
    }

    @Override
    public List<String> getStoryPaths(Arguments arguments) {
        String storyPath = arguments.storyPath();
        File file = new File(storyPath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Story path does not exist " + storyPath);
        }

        if (file.isFile()) {
            return List.of(PathUtils.toUrlString(storyPath));
        }

        List<String> includes = arguments.includes();
        List<String> excludes = arguments.excludes();

        return storyFinder(arguments)
            .findPaths(storyPath, includes, excludes)
            .stream()
            .map(path -> PathUtils.toUrlString(storyPath + File.separator + path))
            .toList();
    }

    private StoryFinder storyFinder(Arguments arguments) {
        String storyFinderClass = arguments.storyFinderClass();
        try {
            return classLoader.newInstance(StoryFinder.class, storyFinderClass);
        } catch (Exception e) {
            throw new JBehaveRunException("Failed to instantiate story finder class: " + storyFinderClass, e);
        }
    }
}
