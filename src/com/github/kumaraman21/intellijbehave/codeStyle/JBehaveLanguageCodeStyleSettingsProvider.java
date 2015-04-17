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
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "META_LINEFEED", "Meta Statements",
                                      CodeStyleSettingsCustomizable.BLANK_LINES);
            consumer.showCustomOption(JBehaveCodeStyleSettings.class, "META_KEEPEMPTYLINES", "Meta Statements",
                                      CodeStyleSettingsCustomizable.BLANK_LINES_KEEP);
        }
    }

    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        JBehaveIndentOptionsEditor editor = new JBehaveIndentOptionsEditor();
        editor.addIndentField(
                new JBehaveIndentOptionsEditor.IndentField("Meta element indent", "INDENT_META_ELEMENT", 6));
        editor.addIndentField(new JBehaveIndentOptionsEditor.IndentField("Story path inden", "INDENT_STORY_PATH", 14));
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
        return "This is a descrtiption.\n" +
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
                "Given the status is idle\n" +
                "Then the plane table is empty\n" +
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
                "Then the plane form is empty\n" +
                "Scenario: Clear button\n" +
                "Given using profile <<PROFILE|DEMO>> 1 the status is idle\n" +
                "When the plane form is cleared\n" +
                "Then the plane form is empty\n" +
                "\n";
    }

}
