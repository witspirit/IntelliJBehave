package com.github.kumaraman21.intellijbehave.codeStyle;

import com.intellij.application.options.SmartIndentOptionsEditor;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DeBritoD on 16.04.2015.
 */
public class JBehaveIndentOptionsEditor extends SmartIndentOptionsEditor {
    private List<IndentField> indentFields = new ArrayList<IndentField>();

    public void addIndentField(IndentField field) {
        indentFields.add(field);
    }

    protected void addComponents() {
        for (IndentField field : indentFields) {
            field.createUi();
            add(field.label, field.textField);
        }
    }

    public boolean isModified(final CodeStyleSettings settings, final CommonCodeStyleSettings.IndentOptions options) {
        try {
            JBehaveCodeStyleSettings customSettings = settings.getCustomSettings(JBehaveCodeStyleSettings.class);
            for (IndentField field : indentFields) {
                if (field.isModified(customSettings)) return true;
            }
        } catch (NoSuchFieldException n) {

        } catch (IllegalAccessException i) {

        }
        return false;
    }

    public void apply(final CodeStyleSettings settings, final CommonCodeStyleSettings.IndentOptions options) {
        try {
            JBehaveCodeStyleSettings customSettings = settings.getCustomSettings(JBehaveCodeStyleSettings.class);
            for (IndentField field : indentFields) {
                field.apply(customSettings);
            }

        } catch (NoSuchFieldException n) {

        } catch (IllegalAccessException i) {

        }
    }

    public void reset(@NotNull final CodeStyleSettings settings,
                      @NotNull final CommonCodeStyleSettings.IndentOptions options) {
        for (IndentField field : indentFields) {
            field.reset();
        }
    }

    public void setEnabled(final boolean enabled) {
        for (IndentField field : indentFields) {
            field.setEnabled(enabled);
        }
    }

    public static class IndentField {
        private String labelText;
        private JLabel label;
        private JTextField textField;
        private int defaultValue;
        private String fieldName;

        public IndentField(String labelText, String fieldName, int defaultValue) {
            this.labelText = labelText;
            this.fieldName = fieldName;
            this.defaultValue = defaultValue;
        }

        public void createUi() {
            textField = new JTextField(4);
            label = new JLabel(labelText);
        }

        public boolean isModified(
                JBehaveCodeStyleSettings settings) throws NoSuchFieldException, IllegalAccessException {
            Class<? extends JBehaveCodeStyleSettings> settingsClass = settings.getClass();
            Field field = settingsClass.getField(fieldName);
            Integer currentValue = (Integer) field.get(settings);
            return isFieldModified(textField, currentValue);
        }

        public void reset() {
            textField.setText(String.valueOf(defaultValue));
        }

        public void setEnabled(final boolean enabled) {
            label.setEnabled(enabled);
            textField.setEnabled(enabled);
        }

        public void apply(JBehaveCodeStyleSettings settings) throws NoSuchFieldException, IllegalAccessException {
            int value = getFieldValue(textField, Integer.MIN_VALUE, 6);
            Class<? extends JBehaveCodeStyleSettings> settingsClass = settings.getClass();
            Field field = settingsClass.getField(fieldName);
            field.setInt(settings, value);
        }

        protected int getFieldValue(JTextField field, int minValue, int defValue) {
            try {
                return Math.max(Integer.parseInt(field.getText()), minValue);
            } catch (NumberFormatException e) {
                return defValue;
            }
        }
    }
}
