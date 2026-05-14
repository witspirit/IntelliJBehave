package com.github.kumaraman21.intellijbehave.runner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Arguments {

    public static final String CLASS_PATH_ELEMENTS_PARAMETER_NAME = "--classPathElements";
    public static final String META_FILTERS_PARAMETER_NAME = "--metaFilters";
    public static final String EMBEDDER_CLASS_PARAMETER_NAME = "--embedderClass";
    public static final String STORY_PATH_PARAMETER_NAME = "--storyPath";
    public static final String INCLUDES_PARAMETER_NAME = "--includes";
    public static final String EXCLUDES_PARAMETER_NAME = "--excludes";
    public static final String STORY_FINDER_CLASS_PARAMETER_NAME = "--storyFinderClass";
    public static final String VERBOSE_PARAMETER_NAME = "--verbose";
    public static final String STACKTRACE_PARAMETER_NAME = "--stacktrace";

    @Parameter(names = CLASS_PATH_ELEMENTS_PARAMETER_NAME, required = true)
    private List<String> classPathElements = new ArrayList<>();

    @Parameter(names = META_FILTERS_PARAMETER_NAME)
    private List<String> metaFilters = new ArrayList<>();

    @Parameter(names = EMBEDDER_CLASS_PARAMETER_NAME, required = true)
    private String embedderClass;

    @Parameter(names = STORY_PATH_PARAMETER_NAME, required = true)
    private String storyPath;

    @Parameter(names = INCLUDES_PARAMETER_NAME)
    private List<String> includes = new ArrayList<>();

    @Parameter(names = EXCLUDES_PARAMETER_NAME)
    private List<String> excludes = new ArrayList<>();

    @Parameter(names = STORY_FINDER_CLASS_PARAMETER_NAME, required = true)
    private String storyFinderClass;

    @Parameter(names = VERBOSE_PARAMETER_NAME)
    private boolean verbose;

    @Parameter(names = STACKTRACE_PARAMETER_NAME)
    private boolean stacktrace;

    private Arguments() {
    }

    /**
     * Constructs JBehave story runner arguments holder object using provided arguments
     *
     * @param args command line arguments to parse
     * @return JBehave story runner arguments holder
     * @throws com.beust.jcommander.ParameterException if arguments are invalid or required arguments are missing
     */
    public static Arguments parse(String[] args) {
        Arguments arguments = new Arguments();
        new JCommander(arguments).parse(args);
        return arguments;
    }

    public List<String> classPathElements() {
        return Collections.unmodifiableList(classPathElements);
    }

    public List<String> metaFilters() {
        return Collections.unmodifiableList(metaFilters);
    }

    public String embedderClass() {
        return embedderClass;
    }

    public String storyPath() {
        return storyPath;
    }

    public List<String> includes() {
        return Collections.unmodifiableList(includes);
    }

    public List<String> excludes() {
        return Collections.unmodifiableList(excludes);
    }

    public String storyFinderClass() {
        return storyFinderClass;
    }

    public boolean verbose() {
        return verbose;
    }

    public boolean stacktrace() {
        return stacktrace;
    }

    @Override
    public String toString() {
        return "Arguments{" +
            "classPathElements=" + classPathElements +
            ", metaFilters=" + metaFilters +
            ", embedderClass='" + embedderClass + '\'' +
            ", storyPath='" + storyPath + '\'' +
            ", includes=" + includes +
            ", excludes=" + excludes +
            ", storyFinderClass='" + storyFinderClass + '\'' +
            ", verbose=" + verbose +
            ", stacktrace=" + stacktrace +
            '}';
    }
}
