package com.github.kumaraman21.intellijbehave.runner.internal;

import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.NullStoryReporter;
import org.jbehave.core.steps.StepCreator;
import org.jbehave.core.steps.Timing;
import org.jetbrains.annotations.NotNull;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Replacement of {@link org.jbehave.core.reporters.TeamCityConsoleOutput} as the last one contains some issues.
 */
public class SMTStoryReporter extends NullStoryReporter {

    private final PrintStream stream;

    private String currentStory;
    private String currentScenario;
    private boolean scenarioFailed;
    private boolean scenarioIgnored;

    public SMTStoryReporter(PrintStream stream) {
        this.stream = stream;
    }

    private void serviceMessage(@NotNull ServiceMessage message) {
        stream.println(message.asTeamCityString());
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        currentStory = story.getName();
        serviceMessage(ServiceMessage.of("testSuiteStarted").attr("name", currentStory));
    }

    @Override
    public void afterStory(boolean givenStory) {
        if (currentStory != null) {
            serviceMessage(ServiceMessage.of("testSuiteFinished").attr("name", currentStory));
            currentStory = null;
        }
    }

    @Override
    public void beforeScenario(Scenario scenario) {
        currentScenario = "Scenario: " + scenario.getTitle();
        scenarioFailed = false;
        scenarioIgnored = false;
        serviceMessage(ServiceMessage.of("testStarted").attr("name", currentScenario));
    }

    @Override
    public void afterScenario(Timing timing) {
        if (currentScenario != null && !scenarioIgnored) {
            long durationMs = timing.getDurationInMillis();
            serviceMessage(
                ServiceMessage.of("testFinished")
                    .attr("name", currentScenario)
                    .attr("duration", String.valueOf(durationMs))
            );
        }
        currentScenario = null;
    }

    @Override
    public void failed(String step, Throwable cause) {
        scenarioFailed = true;
        serviceMessage(
            ServiceMessage.of("testFailed")
                .attr("name", currentScenario)
                .attr("message", cause == null ? "Step failed" : cause.getMessage())
                .attr("details", cause == null ? step : stackTrace(step, cause))
        );
    }

    @Override
    public void pending(StepCreator.PendingStep step) {
        scenarioIgnored = true;
        serviceMessage(
            ServiceMessage.of("testIgnored")
                .attr("name", currentScenario)
                .attr("message", "Pending step: " + step.stepAsString())
        );
    }

    @Override
    public void notPerformed(String step) {
        if (!scenarioFailed && !scenarioIgnored) {
            scenarioIgnored = true;
            serviceMessage(
                ServiceMessage.of("testIgnored")
                    .attr("name", currentScenario)
                    .attr("message", "Step not performed: " + step)
            );
        }
    }

    private static String stackTrace(String step, Throwable cause) {
        StringBuilder builder = new StringBuilder();
        builder.append("Step: ").append(step).append("\n");
        builder.append(cause.toString()).append("\n");
        for (StackTraceElement element : cause.getStackTrace()) {
            builder.append("\tat ").append(element).append("\n");
        }
        return builder.toString();
    }

    private static final class ServiceMessage {

        private final String name;
        private final Map<String, String> attributes;

        private ServiceMessage(String name) {
            this.name = name;
            this.attributes = new LinkedHashMap<>();
        }

        static ServiceMessage of(String name) {
            return new ServiceMessage(name);
        }

        ServiceMessage attr(String key, String value) {
            attributes.put(key, value == null ? "" : value);
            return this;
        }

        String asTeamCityString() {
            StringBuilder sb = new StringBuilder("##teamcity[").append(name);
            for (Map.Entry<String, String> e : attributes.entrySet()) {
                sb.append(" ")
                    .append(e.getKey())
                    .append("='")
                    .append(escape(e.getValue()))
                    .append("'");
            }
            return sb.append("]").toString();
        }

        private static String escape(String value) {
            return value
                .replace("|", "||")
                .replace("'", "|'")
                .replace("\n", "|n")
                .replace("\r", "|r")
                .replace("[", "|[")
                .replace("]", "|]");
        }
    }
}
