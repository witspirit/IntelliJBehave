package com.github.kumaraman21.intellijbehave.codeStyle;

import com.github.kumaraman21.intellijbehave.language.JBehaveLanguage;
import com.intellij.application.options.CodeStyleAbstractConfigurable;
import com.intellij.application.options.CodeStyleAbstractPanel;
import com.intellij.application.options.TabbedLanguageCodeStylePanel;
import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by DeBritoD on 30.03.2015.
 */
public class JBehaveCodeStyleSettingsProvider extends CodeStyleSettingsProvider {
    @Override
    public CustomCodeStyleSettings createCustomSettings(CodeStyleSettings settings) {
        return new JBehaveCodeStyleSettings(settings);
    }

    @Override
    public boolean hasSettingsPage() {
        return true;
    }

    @Nullable
    @Override
    public String getConfigurableDisplayName() {
        return "JBehave";
    }

    @NotNull
    @Override
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
        return new MyCodeStyleAbstractConfigurable(settings, originalSettings);
    }

    private static class JBehaveCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {
        public JBehaveCodeStyleMainPanel(CodeStyleSettings currentSettings, CodeStyleSettings settings) {
            super(JBehaveLanguage.JBEHAVE_LANGUAGE, currentSettings, settings);
        }

        protected void initTabs(CodeStyleSettings settings) {
            addIndentOptionsTab(settings);
            //addSpacesTab(settings);
            addBlankLinesTab(settings);

            addWrappingAndBracesTab(settings);
            //addTab(new JsonCodeStylePanel(settings));
        }

    }

    private static class MyCodeStyleAbstractConfigurable extends CodeStyleAbstractConfigurable {
        public MyCodeStyleAbstractConfigurable(CodeStyleSettings settings, CodeStyleSettings originalSettings) {
            super(settings, originalSettings, "JBehave");
        }

        @Override
        protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
            return new JBehaveCodeStyleMainPanel(getCurrentSettings(), settings);
        }

        @Nullable
        @Override
        public String getHelpTopic() {
            return null;
        }
    }
}
