package com.github.kumaraman21.intellijbehave.runner.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Uses events from {@link org.jbehave.core.reporters.PostStoryStatisticsCollector}.
 */
public class StatisticCollector extends OutputStream {

    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final Properties properties = new Properties();

    @Override
    public void write(int b) {
        buffer.write(b);
    }

    @Override
    public void close() throws IOException {
        byte[] bytes = buffer.toByteArray();
        if (bytes.length > 0) {
            properties.load(new ByteArrayInputStream(bytes));
        }
    }

    private int getProperty(String name, String defaultValue) {
        return Integer.parseInt(properties.getProperty(name, defaultValue));
    }

    public int totalSteps() {
        return getProperty("steps", "0");
    }

    public int getFailedSteps() {
        return getProperty("stepsFailed", "0");
    }

    public int getPendingSteps() {
        return getProperty("stepsPending", "0");
    }

    public int getIgnoreSteps() {
        return getProperty("stepsIgnorable", "0");
    }

    public int getSuccessfulSteps() {
        return getProperty("stepsSuccessful", "0");
    }

    public int totalScenarios() {
        return getProperty("scenarios", "0");
    }

    public int getFailedScenarios() {
        return getProperty("scenariosFailed", "0");
    }

    public int getPendingScenarios() {
        return getProperty("scenariosPending", "0");
    }

    public int getSuccessfulScenarios() {
        return getProperty("scenariosSuccessful", "0");
    }

    public boolean wasSuccessful() {
        return getFailedScenarios() == 0 && getPendingScenarios() == 0;
    }
}
