package com.github.kumaraman21.intellijbehave.runner.internal;

import com.github.kumaraman21.intellijbehave.runner.Arguments;

import java.util.List;

public interface StoryPathResolver {

    /**
     * Resolves paths to stories according to provided settings inside the {@code arguments} object.
     *
     * @param arguments JBehave story runner arguments which contains story path settings
     * @return a list of resolved story paths
     * @throws IllegalArgumentException if the provided story path does not exist or is not a file/directory
     */
    List<String> getStoryPaths(Arguments arguments);
}
