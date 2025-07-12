package com.github.kumaraman21.intellijbehave.highlighter;

import com.github.kumaraman21.intellijbehave.utility.LocalizedStorySupport;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import static com.github.kumaraman21.intellijbehave.Samples.EXAMPLES_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.LONG_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.META_SAMPLE;
import static com.github.kumaraman21.intellijbehave.Samples.SIMPLE_SAMPLE;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class StoryLocalizedLexer_SamplesTest extends BasePlatformTestCase {

    private StoryLocalizedLexer storyLexer;

    public void _testTraceAll() {
        //traceAll(SIMPLE_SAMPLE);
        traceAll(LONG_SAMPLE);
        //traceAll(META_SAMPLE);
        //traceAll(EXAMPLES_SAMPLE);
        //traceAll(COMPLEX_SAMPLE);
    }

    public void testParseSimpleSample() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start(SIMPLE_SAMPLE);

        assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " An unknown user cannot be logged");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META, "Meta:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META_KEY, "@skip");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i am the user with nickname: \"weird\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_WHEN, "When");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i try to login using the password \"soweird\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_THEN, "Then");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i get an error message of type \"Wrong Credentials\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
    }

    public void testParseMetaSample() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start(META_SAMPLE);

        assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " An unknown user cannot be logged");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META, "Meta:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META_KEY, "@author");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META_TEXT, "carmen");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META_KEY, "@skip");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i am the user with nickname: \"weird\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
    }

    public void testParseLongSample() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start(LONG_SAMPLE);

        assertToken(StoryTokenType.NARRATIVE_TYPE, "Narrative:");
        advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, " ");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.NARRATIVE_TYPE, "In order to");
        advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, " play a game");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.NARRATIVE_TYPE, "As a");
        advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, " player");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.NARRATIVE_TYPE, "I want to");
        advanceAndAssert(StoryTokenType.STORY_DESCRIPTION, " be able to create and manage my account");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " An unknown user cannot be logged");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META, "Meta:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.META_KEY, "@skip");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i am the user with nickname: \"weird\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_WHEN, "When");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i try to login using the password \"soweird\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_THEN, "Then");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i get an error message of type \"Wrong Credentials\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " A known user cannot be logged using a wrong password");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " the following existing users:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, " nickname ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, " password ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "   Travis ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "   PacMan ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i am the user with nickname: \"Travis\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_WHEN, "When");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i try to login using the password \"McCallum\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_THEN, "Then");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i get an error message of type \"Wrong Credentials\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);

        // ...
    }

    public void testParseExamples() {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
        storyLexer.start(EXAMPLES_SAMPLE);

        assertToken(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " An unknown user cannot be logged");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i am the user with nickname: \"<input>\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_WHEN, "When");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i try to login using the password \"soweird\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_THEN, "Then");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " i get an error message of type \"Wrong Credentials\"");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.EXAMPLE_TYPE, "Examples:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "  login   ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "   password   ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "  Travis  ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "   Pacman     ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "  Vlad    ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.TABLE_CELL, "   Thundercat ");
        advanceAndAssert(StoryTokenType.TABLE_DELIM);
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.SCENARIO_TYPE, "Scenario:");
        advanceAndAssert(StoryTokenType.SCENARIO_TEXT, " A known user can be logged using the right password");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);
        advanceAndAssert(StoryTokenType.STEP_TYPE_GIVEN, "Given");
        advanceAndAssert(StoryTokenType.STEP_TEXT, " the following existing users:");
        advanceAndAssert(StoryTokenType.WHITE_SPACE);

        // ...
    }

    private void advanceAndAssert(IElementType storyTokenType) {
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

    private void traceAll(String content) {
        storyLexer = new StoryLocalizedLexer(new LocalizedStorySupport());
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

    private String rightPad(Object object, int length) {
        StringBuilder builder = new StringBuilder(object.toString());
        while (builder.length() < length) {
            builder.append(" ");
        }
        return builder.toString();
    }

    private String escape(CharSequence tokenSequence) {
        return tokenSequence.toString().replace("\n", "\\n").replace("\r", "\\r");
    }


}
