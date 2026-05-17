package com.github.kumaraman21.intellijbehave.runner.internal;

import com.github.kumaraman21.intellijbehave.runner.Arguments;
import com.github.kumaraman21.intellijbehave.runner.JBehaveRunException;
import com.github.kumaraman21.intellijbehave.runner.loader.StoryLoaderFactory;
import org.apache.commons.lang3.Validate;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderClassLoader;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Properties;

public class EmbedderFactoryImpl implements EmbedderFactory {

    private static final EmbedderCreator DEFAULT_CREATOR = new DefaultEmbedderCreator();
    private static final List<EmbedderCreator> EMBEDDER_CREATORS = List.of(
        // Be careful! The order of creators is matter here
        new AnnotationBasedEmbedderCreator(),
        new ConfigurableEmbedderCreator(),
        new InjectableEmbedderCreator()
    );

    private final EmbedderClassLoader classLoader;
    private final StoryReporter[] storyReporters;

    public EmbedderFactoryImpl(@NotNull EmbedderClassLoader classLoader, StoryReporter... reporters) {
        this.classLoader = Validate.notNull(classLoader, "ClassLoader cannot be null");
        this.storyReporters = reporters;
    }

    @Override
    public Embedder createEmbedder(Arguments arguments) {
        Embedder embedder = newEmbedder(arguments);
        embedder.useClassLoader(classLoader);
        embedder.useMetaFilters(arguments.metaFilters());
        embedder.useSystemProperties(systemProperties());
        embedder.useConfiguration(configuration(embedder));
        return embedder;
    }

    private Embedder newEmbedder(Arguments arguments) {
        String embedderClassName = arguments.embedderClass();
        Class<?> embedderClass = getEmbedderClass(embedderClassName);

        return EMBEDDER_CREATORS.stream()
            .filter(creator -> creator.supports(embedderClass))
            .findFirst()
            .orElse(DEFAULT_CREATOR)
            .create(embedderClass, classLoader);
    }

    private Class<?> getEmbedderClass(String className) {
        try {
            return Class.forName(className, true, classLoader);
        } catch (ClassNotFoundException e) {
            throw new JBehaveRunException("Failed to load embedder class: " + className, e);
        }
    }

    private Properties systemProperties() {
        return System.getProperties();
    }

    private Configuration configuration(Embedder embedder) {
        Configuration configuration = embedder.configuration();
        configuration.useStoryReporterBuilder(storyReporterBuilder(configuration));
        configuration.useStoryLoader(storyLoader(configuration));
        return configuration;
    }

    private StoryLoader storyLoader(Configuration configuration) {
        return StoryLoaderFactory.createLoader(configuration);
    }

    private StoryReporterBuilder storyReporterBuilder(Configuration configuration) {
        return configuration.storyReporterBuilder().withReporters(storyReporters);
    }

    private interface EmbedderCreator {

        boolean supports(Class<?> embedderClass);

        Embedder create(Class<?> embedderClass, EmbedderClassLoader classLoader);
    }

    private static class AnnotationBasedEmbedderCreator implements EmbedderCreator {

        @Override
        public boolean supports(Class<?> embedderClass) {
            return embedderClass.isAnnotationPresent(UsingEmbedder.class);
        }

        @Override
        public Embedder create(Class<?> embedderClass, EmbedderClassLoader classLoader) {
            return AnnotationBuilders.create(embedderClass, classLoader).buildEmbedder();
        }
    }

    private static class ConfigurableEmbedderCreator implements EmbedderCreator {

        @Override
        public boolean supports(Class<?> embedderClass) {
            return ConfigurableEmbedder.class.isAssignableFrom(embedderClass);
        }

        @Override
        public Embedder create(Class<?> embedderClass, EmbedderClassLoader classLoader) {
            return classLoader.newInstance(ConfigurableEmbedder.class, embedderClass.getName()).configuredEmbedder();
        }
    }

    private static class InjectableEmbedderCreator implements EmbedderCreator {

        @Override
        public boolean supports(Class<?> embedderClass) {
            return InjectableEmbedder.class.isAssignableFrom(embedderClass);
        }

        @Override
        public Embedder create(Class<?> embedderClass, EmbedderClassLoader classLoader) {
            return classLoader.newInstance(InjectableEmbedder.class, embedderClass.getName()).injectedEmbedder();
        }
    }

    private static class DefaultEmbedderCreator implements EmbedderCreator {

        @Override
        public boolean supports(Class<?> embedderClass) {
            return true;
        }

        @Override
        public Embedder create(Class<?> embedderClass, EmbedderClassLoader classLoader) {
            return classLoader.newInstance(Embedder.class, embedderClass.getName());
        }
    }
}