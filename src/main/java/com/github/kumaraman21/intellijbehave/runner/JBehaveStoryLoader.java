package com.github.kumaraman21.intellijbehave.runner;

import com.github.kumaraman21.intellijbehave.runner.loader.StoryLoadersChain;
import org.apache.commons.lang3.Validate;
import org.jbehave.core.io.StoryLoader;
import org.jetbrains.annotations.NotNull;

/**
 * Main source of stories in JBehave plugin is URL, but some project's story files also can contain some references to
 * additional resources (separate scenarios, tables, etc.) as classpath references or relative paths. That's why the
 * chain of story loaders will be useful.
 */
public class JBehaveStoryLoader implements StoryLoader {

    private final StoryLoadersChain chain;

    public JBehaveStoryLoader(@NotNull StoryLoadersChain chain) {
        this.chain = Validate.notNull(chain, "StoryLoadersChain cannot be null");
    }

    @Override
    public String loadStoryAsText(String storyPath) {
        return chain.loadStoryAsText(storyPath);
    }

    @Override
    public String loadResourceAsText(String resourcePath) {
        return chain.loadResourceAsText(resourcePath);
    }
}
