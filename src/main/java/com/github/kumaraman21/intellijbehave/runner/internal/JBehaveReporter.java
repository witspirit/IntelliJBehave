package com.github.kumaraman21.intellijbehave.runner.internal;

import org.jbehave.core.reporters.TxtOutput;

import java.io.PrintStream;
import java.util.Properties;

/**
 * TXT Reporter which uses adjusted printing pattern.
 */
public class JBehaveReporter extends TxtOutput {

    public JBehaveReporter(PrintStream output) {
        super(output, myProperties());
    }

    private static Properties myProperties() {
        Properties patterns = new Properties();
        patterns.setProperty("beforeStep", "");
        patterns.setProperty("dryRun", "{0}\n");
        patterns.setProperty("storyCancelled", "{0} ({1} {2} s)\n");
        patterns.setProperty("beforeStory", "{1}\n({2})\n");
        patterns.setProperty("afterStory", "\n");
        patterns.setProperty("metaStart", "{0}\n");
        patterns.setProperty("metaProperty", "{0}{1} {2}\n");
        patterns.setProperty("metaEnd", "\n");
        patterns.setProperty("filter", "{0}\n");
        patterns.setProperty("narrative", "{0}\n{1} {2}\n{3} {4}\n{5} {6}\n\n");
        patterns.setProperty("lifecycleStart", "{0}\n");
        patterns.setProperty("lifecycleEnd", "\n");
        patterns.setProperty("lifecycleBeforeStart", "{0}\n");
        patterns.setProperty("lifecycleBeforeEnd", "\n");
        patterns.setProperty("lifecycleAfterStart", "{0}\n");
        patterns.setProperty("lifecycleAfterEnd", "\n");
        patterns.setProperty("lifecycleBeforeScopeStart", "{0} {1}\n");
        patterns.setProperty("lifecycleBeforeScopeEnd", "\n");
        patterns.setProperty("lifecycleAfterScopeStart", "{0} {1}\n");
        patterns.setProperty("lifecycleAfterScopeEnd", "\n");
        patterns.setProperty("lifecycleOutcomeStart", "{0} {1}\n");
        patterns.setProperty("lifecycleOutcomeEnd", "\n");
        patterns.setProperty("lifecycleMetaFilter", "{0} {1}\n");
        patterns.setProperty("lifecycleStep", "{0}\n");
        patterns.setProperty("beforeBeforeStoriesSteps", "");
        patterns.setProperty("afterBeforeStoriesSteps", "");
        patterns.setProperty("beforeAfterStoriesSteps", "");
        patterns.setProperty("afterAfterStoriesSteps", "");
        patterns.setProperty("beforeBeforeSystemStorySteps", "");
        patterns.setProperty("afterBeforeSystemStorySteps", "");
        patterns.setProperty("beforeAfterSystemStorySteps", "");
        patterns.setProperty("afterAfterSystemStorySteps", "");
        patterns.setProperty("beforeBeforeUserStorySteps", "");
        patterns.setProperty("afterBeforeUserStorySteps", "");
        patterns.setProperty("beforeAfterUserStorySteps", "");
        patterns.setProperty("afterAfterUserStorySteps", "");
        patterns.setProperty("beforeBeforeSystemScenarioSteps", "");
        patterns.setProperty("afterBeforeSystemScenarioSteps", "");
        patterns.setProperty("beforeAfterSystemScenarioSteps", "");
        patterns.setProperty("afterAfterSystemScenarioSteps", "");
        patterns.setProperty("beforeBeforeUserScenarioSteps", "");
        patterns.setProperty("afterBeforeUserScenarioSteps", "");
        patterns.setProperty("beforeAfterUserScenarioSteps", "");
        patterns.setProperty("afterAfterUserScenarioSteps", "");
        patterns.setProperty("beforeScenario", "{1} {2}\n");
        patterns.setProperty("afterScenario", "\n");
        patterns.setProperty("afterScenarioWithFailure", "\n{0}\n");
        patterns.setProperty("givenStories", "{0} {1}\n");
        patterns.setProperty("givenStoriesStart", "{0}\n");
        patterns.setProperty("givenStory", "{0}{1}\n");
        patterns.setProperty("givenStoriesEnd", "\n");
        patterns.setProperty("successful", "{0}\n");
        patterns.setProperty("ignorable", "{0}\n");
        patterns.setProperty("comment", "{0}\n");
        patterns.setProperty("pending", "{0} ({1})\n({2})\n");
        patterns.setProperty("notPerformed", "{0} ({1})\n");
        patterns.setProperty("failed", "{0} ({1})\n({2})\n");
        patterns.setProperty("restarted", "{0} ({1})\n");
        patterns.setProperty("restartedStory", "{0} ({1})\n");
        patterns.setProperty("outcomesTableStart", "");
        patterns.setProperty("outcomesTableHeadStart", "|");
        patterns.setProperty("outcomesTableHeadCell", "{0}|");
        patterns.setProperty("outcomesTableHeadEnd", "\n");
        patterns.setProperty("outcomesTableBodyStart", "");
        patterns.setProperty("outcomesTableRowStart", "|");
        patterns.setProperty("outcomesTableCell", "{0}|");
        patterns.setProperty("outcomesTableRowEnd", "\n");
        patterns.setProperty("outcomesTableBodyEnd", "");
        patterns.setProperty("outcomesTableEnd", "");
        patterns.setProperty("beforeExamples", "{0}\n");
        patterns.setProperty("examplesStep", "{0}\n");
        patterns.setProperty("afterExamples", "\n");
        patterns.setProperty("examplesTableStart", "\n");
        patterns.setProperty("examplesTableHeadStart", "|");
        patterns.setProperty("examplesTableHeadCell", "{0}|");
        patterns.setProperty("examplesTableHeadEnd", "\n");
        patterns.setProperty("examplesTableBodyStart", "");
        patterns.setProperty("examplesTableRowStart", "|");
        patterns.setProperty("examplesTableCell", "{0}|");
        patterns.setProperty("examplesTableRowEnd", "\n");
        patterns.setProperty("examplesTableBodyEnd", "");
        patterns.setProperty("examplesTableEnd", "");
        patterns.setProperty("example", "\n{0} {1}\n");
        patterns.setProperty("parameterValueStart", "");
        patterns.setProperty("parameterValueEnd", "");
        patterns.setProperty("parameterValueNewline", "\n");
        return patterns;
    }
}
