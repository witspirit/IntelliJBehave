package com.github.kumaraman21.intellijbehave.codeStyle;

import com.github.kumaraman21.intellijbehave.language.JBehaveLanguage;
import com.intellij.lang.Language;
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable;
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider;
import org.jetbrains.annotations.NotNull;

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
        if (settingsType == SettingsType.SPACING_SETTINGS) {
            consumer.showStandardOptions("SPACE_AROUND_ASSIGNMENT_OPERATORS");
            consumer.renameStandardOption("SPACE_AROUND_ASSIGNMENT_OPERATORS", "Separator");
        } else if (settingsType == SettingsType.BLANK_LINES_SETTINGS) {
            consumer.showStandardOptions("KEEP_BLANK_LINES_IN_CODE");
        } else if (settingsType == SettingsType.WRAPPING_AND_BRACES_SETTINGS) {
            consumer.showStandardOptions("KEEP_LINE_BREAKS");
        }
    }

    @Override
    public String getCodeSample(@NotNull SettingsType settingsType) {
        return "Meta: @type smoke\n" +
                "      @speed fast\n" +
                "      @PROFILES: <<PROFILE|DEMO>> 3\n" +
                "\n" +
                "Narrative:\n" +
                "As a user\n" +
                "I want to perform an action\n" +
                "So that I can achieve a business goal\n" +
                "\n" +
                "Scenario: Toggle application status\n" +
                "Given named timeouts in seconds:\n" +
                "| named   | delay |\n" +
                "| delay 1 | 2     |\n" +
                "| delay 2 | 3     |\n" +
                "| delay 3 | 4     |\n" +
                "\n" +
                "Given using profiles the status is idle within defined timeout:\n" +
                "| profile            | named-data |\n" +
                "| <<PROFILE|DEMO>> 1 | delay 1    |\n" +
                "| <<PROFILE|DEMO>> 2 | delay 2    |\n" +
                "| <<PROFILE|DEMO>> 3 | delay 3    |\n" +
                "\n" +
                "When using profiles the active toggle button is clicked:\n" +
                "| profile            |\n" +
                "| <<PROFILE|DEMO>> 1 |\n" +
                "| <<PROFILE|DEMO>> 2 |\n" +
                "| <<PROFILE|DEMO>> 3 |\n" +
                "\n" +
                "Then using profiles the halted status is verified:\n" +
                "| profile            |\n" +
                "| <<PROFILE|DEMO>> 1 |\n" +
                "| <<PROFILE|DEMO>> 2 |\n" +
                "| <<PROFILE|DEMO>> 3 |\n" +
                "\n" +
                "And using profile <<PROFILE|DEMO>> 1 the input fields are disabled\n" +
                "!--  And the status label is colored red\n" +
                "\n" +
                "When using profile <<PROFILE|DEMO>> 1 the window is resized randomly\n" +
                "And the active toggle button is clicked\n" +
                "Then the status is idle\n" +
                "And the input fields are enabled\n" +
                "!--  And the status label is colored green\n" +
                "\n" +
                "Scenario: Clear button\n" +
                "Given using profile <<PROFILE|DEMO>> 1 the status is idle\n" +
                "When the plane form is cleared\n" +
                "Then the plane form is empty\n" +
                "\n" +
                "When a new plane WEFISE-123 manufactured by Boing used as Civil piloted by Sarah Bing is entered\n" +
                "And the window is moved randomly\n" +
                "And the plane form is cleared\n" +
                "Then the status is idle\n" +
                "And the plane form is empty\n" +
                "\n" +
                "When using profiles the active toggle button is clicked:\n" +
                "| profile            |\n" +
                "| <<PROFILE|DEMO>> 2 |\n" +
                "| <<PROFILE|DEMO>> 3 |\n" +
                "\n" +
                "Then using profiles the idle status is verified:\n" +
                "| profile            |\n" +
                "| <<PROFILE|DEMO>> 1 |\n" +
                "| <<PROFILE|DEMO>> 2 |\n" +
                "| <<PROFILE|DEMO>> 3 |";
    }
}
