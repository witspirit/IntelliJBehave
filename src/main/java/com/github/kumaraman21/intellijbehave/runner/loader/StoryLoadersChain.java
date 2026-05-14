package com.github.kumaraman21.intellijbehave.runner.loader;

public interface StoryLoadersChain {

    void setNext(StoryLoadersChain chain);

    String loadStoryAsText(String storyPath);

    String loadResourceAsText(String resourcePath);
}
