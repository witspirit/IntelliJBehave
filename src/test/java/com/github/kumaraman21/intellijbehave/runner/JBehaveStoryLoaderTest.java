package com.github.kumaraman21.intellijbehave.runner;

import com.github.kumaraman21.intellijbehave.runner.loader.ClassPathStoryLoader;
import com.github.kumaraman21.intellijbehave.runner.loader.RelativeFileStoryLoader;
import com.github.kumaraman21.intellijbehave.runner.loader.StoryLoadersChain;
import com.github.kumaraman21.intellijbehave.runner.loader.StoryResourceException;
import com.github.kumaraman21.intellijbehave.runner.loader.UrlStoryLoader;
import com.github.kumaraman21.intellijbehave.utility.PathUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class JBehaveStoryLoaderTest {

    @Test
    void testUrlStoryLoaderShouldWork() {
        StoryLoadersChain chain = new UrlStoryLoader();
        JBehaveStoryLoader storyLoader = new JBehaveStoryLoader(chain);

        String path = PathUtils.toUrlString("src/test/resources/storyloader/myfile.txt");
        String actual = storyLoader.loadStoryAsText(path);
        Assertions.assertThat(actual).isEqualTo("Hello World!");
    }

    @Test
    void testClassPathStoryLoaderShouldWork() {
        StoryLoadersChain chain = new ClassPathStoryLoader();
        JBehaveStoryLoader storyLoader = new JBehaveStoryLoader(chain);

        String path = "storyloader/myfile.txt";
        String actual = storyLoader.loadStoryAsText(path);
        Assertions.assertThat(actual).isEqualTo("Hello World!");
    }

    @Test
    void testRelativeStoryLoaderShouldWork() throws MalformedURLException {
        URL location = new File("src/test/resources/storyloader").toURI().toURL();
        StoryLoadersChain chain = new RelativeFileStoryLoader(location);
        JBehaveStoryLoader storyLoader = new JBehaveStoryLoader(chain);

        String path = "myfile.txt";
        String actual = storyLoader.loadStoryAsText(path);
        Assertions.assertThat(actual).isEqualTo("Hello World!");
    }

    @Test
    void testCompositeStoryLoaderShouldWork() {
        StoryLoadersChain chain = new UrlStoryLoader();
        chain.setNext(new ClassPathStoryLoader());
        JBehaveStoryLoader storyLoader = new JBehaveStoryLoader(chain);

        String path = "storyloader/myfile.txt";
        String actual = storyLoader.loadStoryAsText(path);
        Assertions.assertThat(actual).isEqualTo("Hello World!");
    }

    @Test
    void testCompositeStoryLoaderIncorrectPath() {
        StoryLoadersChain chain = new UrlStoryLoader();
        chain.setNext(new ClassPathStoryLoader());
        JBehaveStoryLoader storyLoader = new JBehaveStoryLoader(chain);

        String path = "myfile.txt";
        Assertions.assertThatThrownBy(() -> storyLoader.loadStoryAsText(path))
            .isInstanceOf(StoryResourceException.class)
            .hasMessage("Unable to load story from path: " + path);
    }
}
