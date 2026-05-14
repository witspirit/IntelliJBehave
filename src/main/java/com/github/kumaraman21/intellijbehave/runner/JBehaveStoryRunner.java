package com.github.kumaraman21.intellijbehave.runner;

import com.beust.jcommander.ParameterException;
import com.github.kumaraman21.intellijbehave.runner.internal.EmbedderFactory;
import com.github.kumaraman21.intellijbehave.runner.internal.EmbedderFactoryImpl;
import com.github.kumaraman21.intellijbehave.runner.internal.JBehaveReporter;
import com.github.kumaraman21.intellijbehave.runner.internal.SMTStoryReporter;
import com.github.kumaraman21.intellijbehave.runner.internal.StatisticCollector;
import com.github.kumaraman21.intellijbehave.runner.internal.StoryPathResolver;
import com.github.kumaraman21.intellijbehave.runner.internal.StoryPathResolverImpl;
import org.apache.commons.lang3.Validate;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.EmbedderClassLoader;
import org.jbehave.core.reporters.PostStoryStatisticsCollector;
import org.jbehave.core.reporters.StoryReporter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public final class JBehaveStoryRunner implements AutoCloseable {

    private final Arguments arguments;
    private final EmbedderFactory embedderFactory;
    private final StoryPathResolver storyPathResolver;
    private final EmbedderClassLoader classLoader;
    private final StatisticCollector statisticCollector;

    private JBehaveStoryRunner(
        @NotNull Arguments arguments,
        @NotNull EmbedderFactory factory,
        @NotNull StoryPathResolver resolver,
        @NotNull EmbedderClassLoader classLoader,
        @NotNull StatisticCollector collector
    ) {
        this.arguments = Validate.notNull(arguments, "Arguments cannot be null");
        this.embedderFactory = Validate.notNull(factory, "EmbedderFactory cannot be null");
        this.storyPathResolver = Validate.notNull(resolver, "StoryPathResolver cannot be null");
        this.classLoader = Validate.notNull(classLoader, "EmbedderClassLoader cannot be null");
        this.statisticCollector = Validate.notNull(collector, "StatisticCollector cannot be null");
    }

    public static void main(String[] args) {
        ExitCode code = execute(args);
        System.exit(code.getCode());
    }

    private static ExitCode execute(String[] args) {
        Arguments arguments;
        try {
            arguments = Arguments.parse(args);
        } catch (ParameterException e) {
            printError("Failed to initialize arguments", e, true, false);
            return ExitCode.ARGUMENT_ERROR;
        }

        try (JBehaveStoryRunner runner = JBehaveStoryRunner.create(arguments)) {
            return runner.run();
        } catch (Exception e) {
            printError("Failed to run JBehave stories", e, arguments.verbose(), arguments.stacktrace());
            return ExitCode.RUNTIME_ERROR;
        }
    }

    private static void printError(String message, Exception e, boolean verbose, boolean stacktrace) {
        System.err.println(message);
        if (verbose && e != null) {
            System.err.println(e.getMessage());
        }

        if (stacktrace && e != null) {
            e.printStackTrace(System.err);
        }
    }

    public static JBehaveStoryRunner create(@NotNull Arguments arguments) {
        EmbedderClassLoader loader = embedderClassLoader(arguments);
        StoryPathResolver resolver = new StoryPathResolverImpl(loader);
        StatisticCollector collector = new StatisticCollector();
        EmbedderFactory factory = embedderFactory(loader, collector);
        return new JBehaveStoryRunner(arguments, factory, resolver, loader, collector);
    }

    private static EmbedderClassLoader embedderClassLoader(Arguments arguments) {
        List<String> elements = arguments.classPathElements();
        return new EmbedderClassLoader(elements);
    }

    private static EmbedderFactory embedderFactory(EmbedderClassLoader classLoader, StatisticCollector collector) {
        StoryReporter consoleReporter = new JBehaveReporter(System.out);
        StoryReporter smtStoryReporter = new SMTStoryReporter(System.out);
        StoryReporter storyStatisticsCollector = new PostStoryStatisticsCollector(collector);
        return new EmbedderFactoryImpl(classLoader, smtStoryReporter, storyStatisticsCollector, consoleReporter);
    }

    public ExitCode run() {
        Embedder embedder = embedderFactory.createEmbedder(arguments);
        List<String> storyPaths = storyPathResolver.getStoryPaths(arguments);
        embedder.runStoriesAsPaths(storyPaths);
        return statisticCollector.wasSuccessful() ? ExitCode.SUCCESS : ExitCode.RUNTIME_ERROR;
    }

    @Override
    public void close() throws IOException {
        classLoader.close();
    }

    public enum ExitCode {
        SUCCESS(0),
        RUNTIME_ERROR(1),
        ARGUMENT_ERROR(2);

        private final int code;

        ExitCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
