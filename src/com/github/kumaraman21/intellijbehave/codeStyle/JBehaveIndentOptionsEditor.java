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
    private final List<IndentField> indentFields = new ArrayList<IndentField>();

    private JBehaveIndentOptionsEditor addIndentField(IndentField field) {
        indentFields.add(field);
        return this;
    }

    public JBehaveIndentOptionsEditor addIndentField(String labelText, String fieldName) {
        return addIndentField(new IndentField(labelText, fieldName));
    }

    protected void addComponents() {
        //super.addComponents();
        for (IndentField field : indentFields) {
            field.createUi();
            add(field.label, field.textField);
        }
    }

    public boolean isModified(final CodeStyleSettings settings, final CommonCodeStyleSettings.IndentOptions options) {
        //if (super.isModified(settings, options)) return true;
        try {
            JBehaveCodeStyleSettings customSettings = settings.getCustomSettings(JBehaveCodeStyleSettings.class);
            for (IndentField field : indentFields) {
                if (field.isModified(customSettings)) return true;
            }
        } catch (NoSuchFieldException ignored) {

        } catch (IllegalAccessException ignored) {

        }
        return false;
    }

    public void apply(final CodeStyleSettings settings, final CommonCodeStyleSettings.IndentOptions options) {
        //super.apply(settings, options);
        try {
            JBehaveCodeStyleSettings customSettings = settings.getCustomSettings(JBehaveCodeStyleSettings.class);
            for (IndentField field : indentFields) {
                field.apply(customSettings);
            }

        } catch (NoSuchFieldException ignored) {

        } catch (IllegalAccessException ignored) {

        }
    }

    public void reset(@NotNull final CodeStyleSettings settings,
                      @NotNull final CommonCodeStyleSettings.IndentOptions options) {
        //super.reset(settings, options);
        JBehaveCodeStyleSettings customSettings = settings.getCustomSettings(JBehaveCodeStyleSettings.class);

        try {
            for (IndentField field : indentFields) {
                field.reset(customSettings);
            }
        } catch (NoSuchFieldException ignored) {

        } catch (IllegalAccessException ignored) {

        }
    }

    public void setEnabled(final boolean enabled) {
        //super.setEnabled(enabled);
        for (IndentField field : indentFields) {
            field.setEnabled(enabled);
        }
    }

    public static class IndentField {
        private final String labelText;
        private JLabel label;
        private JTextField textField;
        private final String fieldName;

        public IndentField(String labelText, String fieldName) {
            this.labelText = labelText;
            this.fieldName = fieldName;
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

        public void reset(JBehaveCodeStyleSettings settings) throws NoSuchFieldException, IllegalAccessException {
            Class<? extends JBehaveCodeStyleSettings> settingsClass = settings.getClass();
            Field field = settingsClass.getField(fieldName);
            Integer currentValue = (Integer) field.get(settings);
            textField.setText(String.valueOf(currentValue));
        }

        public void setEnabled(final boolean enabled) {
            label.setEnabled(enabled);
            textField.setEnabled(enabled);
        }

        public void apply(JBehaveCodeStyleSettings settings) throws NoSuchFieldException, IllegalAccessException {
            int value = getFieldValue(textField);
            Class<? extends JBehaveCodeStyleSettings> settingsClass = settings.getClass();
            Field field = settingsClass.getField(fieldName);
            field.setInt(settings, value);
        }

        int getFieldValue(JTextField field) {
            try {
                return Math.max(Integer.parseInt(field.getText()), Integer.MIN_VALUE);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}
