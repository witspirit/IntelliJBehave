package com.github.kumaraman21.intellijbehave.codeStyle;

import com.github.kumaraman21.intellijbehave.language.JBehaveLanguage;
import com.intellij.application.options.IndentOptionsEditor;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 30.03.2015.
 */
public class JBehaveLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    @NotNull
    @Override
    public Language getLanguage() {
        return JBehaveLanguage.JBEHAVE_LANGUAGE;
    }

    @Override
    public void customizeSettings(@NotNull CodeStyleSettingsCustomizable consumer, @NotNull SettingsType settingsType) {
        if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "ALL_KEEPEMPTYLINES", "Keep blank lines",
                                      CodeStyleSettingsCustomizable.BLANK_LINES_KEEP);
            //
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "DESCRIPTION_LINEFEED", "Description",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            //
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "META_LINEFEED", "Meta",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_META_LINEFEED", "Keyword Meta:",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            //
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "NARRATIVE_LINEFEED", "Narrative",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_NARRATIVE_LINEFEED", "Keyword Narrative:",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "GIVENSTORIES_LINEFEED", "GivenStories",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_GIVENSTORIES_LINEFEED",
                                      "Keyword GivenStories:", CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "LIFECYCLE_LINEFEED", "Lifecycle",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_LIFECYCLE_LINEFEED", "Keyword Lifecycle:",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "LIFECYCLE_AFTER_LINEFEED", "Lifecycle After",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_LIFECYCLE_AFTER_LINEFEED",
                                      "Lifecycle After: Keyword", CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "LIFECYCLE_BEFORE_LINEFEED", "Lifecycle Before",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_LIFECYCLE_BEFORE_LINEFEED",
                                      "Lifecycle Before: Keyword", CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "SCENARIO_LINEFEED", "Scenario",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_SCENARIO_LINEFEED", "Keyword Scenario:",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "SCENARIO_TITLE_LINEFEED", "Scenario Title",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "FIRST_STEP_LINEFEED", "Before First Step",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "GIVEN_STEP_LINEFEED", "Before Given Step",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "WHEN_STEP_LINEFEED", "Before When Step",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "THEN_STEP_LINEFEED", "Before Then Step",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "AND_STEP_LINEFEED", "Before And Step",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
        }
        if (settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_META_KEEPLINEBREAKS", "Keyword Meta:",
                                      "Newline after:");
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_NARRATIVE_KEEPLINEBREAKS",
                                      "Keyword Narrative:", "Newline after:");
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_GIVENSTORIES_KEEPLINEBREAKS",
                                      "Keyword GivenStories:", "Newline after:");
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "TOKEN_SCENARIO_KEEPLINEBREAKS",
                                      "Keyword Scenario:", "Newline after:");
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "ALIGN_META_KEY", "Align meta keys", "Alignment");
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "ALIGN_STORY_PATH", "Align story paths",
                                      "Alignment");
        }
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        JBehaveIndentOptionsEditor editor = new JBehaveIndentOptionsEditor();
        //        editor.addIndentField("Meta element indent", "INDENT_META_ELEMENT", 6)
        //              .addIndentField("Story path indent", "INDENT_STORY_PATH", 14);
        //editor.addIndentField("Indent parents", "INDENT_PARENTS", 0);
        editor.addIndentField("Indent children", "INDENT_LEAFS", 0);
        editor.addIndentField("Indent narrative text", "INDENT_NARRATIVE_TEXT", 0);
        return editor;
    }

    @Nullable
    @Override
    public CommonCodeStyleSettings getDefaultCommonSettings() {
        CommonCodeStyleSettings commonSettings = new CommonCodeStyleSettings(getLanguage());
        CommonCodeStyleSettings.IndentOptions indentOptions = commonSettings.initIndentOptions();
        indentOptions.INDENT_SIZE = 0;
        indentOptions.TAB_SIZE = 0;
        indentOptions.CONTINUATION_INDENT_SIZE = 0;
        return commonSettings;
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return "This is a description.\n" +
                "\n" +
                "One more line\n" +
                "Meta: @type smoke\n" +
                "      @speed fast\n" +
                "      @PROFILES: <<PROFILE|DEMO>> 3\n" +
                "Narrative:\n" +
                "As a user\n" +
                "I want to perform an action\n" +
                "So that I can achieve a business goal\n" +
                "GivenStories: acceptance/smoke/ScenarioBooking-nested-1.story,\n" +
                "              acceptance/smoke/ScenarioBooking-nested-2.story\n" +
                "Lifecycle:\n" +
                "Before:\n" +
                "Given using profile <<PROFILE|DEMO>> 1 the status is idle\n" +
                "When the database clear button is clicked on the area 51 module\n" +
                "Then the status is Setting up database\n" +
                "\n" +
                "After:\n" +
                "Given using profile <<PROFILE|DEMO>> 1 the status is idle within 3 seconds\n" +
                "When using profile <<PROFILE|DEMO>> 1 nothing important is done for 1 seconds\n" +
                "Then the status is idle\n" +
                "\n" +
                "Scenario: Toggle application status\n" +
                "Meta: @type smoke\n" +
                "      @speed fast\n" +
                "      @PROFILES: <<PROFILE|DEMO>> 3\n" +
                "GivenStories: acceptance/smoke/ScenarioBooking-nested-1.story,\n" +
                "              acceptance/smoke/ScenarioBooking-nested-2.story\n" +
                "Given named timeouts in seconds:\n" +
                "| named   | delay |\n" +
                "| delay 1 | 2     |\n" +
                "| delay 2 | 3     |\n" +
                "| delay 3 | 4     |\n" +
                "When using profile <<PROFILE|DEMO>> 1 the window is resized randomly\n" +
                "Then the status is idle\n" +
                "!--  And the status label is colored green\n" +
                "And the input fields are enabled\n" +
                "!--  And the status label is colored green\n" +
                "Examples:\n" +
                "|key |value |\n" +
                "|key1|value1|\n" +
                "Scenario: Clear button\n" +
                "Given using profile <<PROFILE|DEMO>> 1 the status is idle\n" +
                "When the plane form is cleared\n" +
                "Then the plane form is empty\n";
    }

}
