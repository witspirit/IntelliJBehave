package com.github.kumaraman21.intellijbehave.highlighter;

import com.github.kumaraman21.intellijbehave.utility.LocalizedStorySupport;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.jetbrains.annotations.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryLocalizedLexer_MalformedTest extends BasePlatformTestCase {

    private StoryLocalizedLexer storyLexer;

    public void testParse_tableWithoutPreamble() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start("| Travis | Pacman |");

        assertToken(StoryTokenType.TABLE_DELIM, "|");
        advanceAndAssert(StoryTokenType.TABLE_CELL, " Travis ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM, "|");
        advanceAndAssert(StoryTokenType.TABLE_CELL, " Pacman ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM, "|");
        advanceAndAssert(null);
    }

    public void testParse_scenarioWithoutText() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start("Scenario:\n" +
                "Given\n" +
                "When\n" +
                "Then\n" +
                "And\n");

        assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_WHEN, "When");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_THEN, "Then");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_AND, "And");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(null);
    }

    public void testParse_scenarioWithStepAsText() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start("Scenario: Given a nice\n" +
                "Given\n" +
                "When\n" +
                "Then\n");

        assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " Given a nice");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_WHEN, "When");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_THEN, "Then");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(null);
    }

    private void advanceAndAssert(@Nullable IElementType storyTokenType) {
        storyLexer.advance();
        assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
    }

    private void advanceAndAssert(IElementType storyTokenType, String content) {
        storyLexer.advance();
        assertToken(storyTokenType, content);
    }

    private void assertToken(IElementType storyTokenType, String content) {
        assertThat(storyLexer.getTokenSequence()).isEqualTo(content);
        assertThat(storyLexer.getTokenType()).isEqualTo(storyTokenType);
    }

    private static void traceAll(String content, StoryLocalizedLexer storyLexer) {
        storyLexer.start(content);

        IElementType tokenType;
        do {
            tokenType = storyLexer.getTokenType();
            System.out.println(
                    rightPad("" + storyLexer.getPosition(), 3) + " " +
                            "[" + rightPad(tokenType, "STORY_DESCRIPTION".length()) + "]" +
                            rightPad(storyLexer.lexerState(), "IN_DIRECTIVE".length()) +
                            ": >>" + escape(storyLexer.getTokenSequence()) + "<<");

            storyLexer.advance();
            tokenType = storyLexer.getTokenType();
        }
        while (tokenType != null);
    }

    private static String rightPad(Object object, int length) {
        StringBuilder builder = new StringBuilder(object.toString());
        while (builder.length() < length) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private static String escape(CharSequence tokenSequence) {
        return tokenSequence.toString().replace("\n", "\\n").replace("\r", "\\r");
    }


}
