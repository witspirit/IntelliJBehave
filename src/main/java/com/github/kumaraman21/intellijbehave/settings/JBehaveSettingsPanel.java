/*
 * Copyright 2011-12 Aman Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kumaraman21.intellijbehave.settings;

import com.intellij.execution.configurations.ConfigurationUtil;
import com.intellij.ide.DataManager;
import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeJavaClassChooserDialog;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiMethodUtil;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public final class JBehaveSettingsPanel {
    private final JPanel contentPane;
    private final JLabel storyRunnerLabel;
    private final TextFieldWithBrowseButton storyRunnerField;
    private final JCheckBox enableCodeCompletionCheckBox;

    private final JBehaveSettings jBehaveSettings;

    public JBehaveSettingsPanel() {
        jBehaveSettings = JBehaveSettings.getInstance();

        storyRunnerLabel = new JLabel();
        storyRunnerLabel.setText("Main class for running stories:");
        storyRunnerLabel.setToolTipText("Class with a main function to receive the story file to run as a parameter");
        storyRunnerField = new TextFieldWithBrowseButton();
        storyRunnerField.addActionListener(new BrowseMainClassListener(storyRunnerField));

        enableCodeCompletionCheckBox = new JCheckBox("Enable code completion in *.story files");

        contentPane = buildSettingsPanel();
    }

    public void apply() {
        jBehaveSettings.setStoryRunner(storyRunnerField.getText());
        jBehaveSettings.setStoryAutoCompletion(enableCodeCompletionCheckBox.isSelected());
    }

    public void reset() {
        storyRunnerField.setText(jBehaveSettings.getStoryRunner());
        enableCodeCompletionCheckBox.setSelected(jBehaveSettings.isStoryAutoCompletion());
    }

    public boolean isModified() {
        boolean storyRunnerChanged = !storyRunnerField.getText().equals(jBehaveSettings.getStoryRunner());
        boolean storyCodeCompletionChanged = !Objects.equals(enableCodeCompletionCheckBox.isSelected(), jBehaveSettings.isStoryAutoCompletion());
        return storyRunnerChanged || storyCodeCompletionChanged;
    }

    public JPanel getContentPane() {
        return this.contentPane;
    }

    private JPanel buildSettingsPanel() {
        return FormBuilder.createFormBuilder()
                .addLabeledComponent(storyRunnerLabel, storyRunnerField)
                .addVerticalGap(5)
                .addComponent(enableCodeCompletionCheckBox)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    /**
     * Shows a dialog in which the user can browse and select a class as the main class.
     * <p>
     * The qualified name of the class is then populated into the class text field on the settings panel.
     */
    private class BrowseMainClassListener implements ActionListener {
        private final TextFieldWithBrowseButton textField;

        public BrowseMainClassListener(TextFieldWithBrowseButton textField) {
            this.textField = textField;
        }

        public void actionPerformed(ActionEvent e) {
            DataContext dataContext = DataManager.getInstance().getDataContext(JBehaveSettingsPanel.this.contentPane);
            Project project = CommonDataKeys.PROJECT.getData(dataContext);

            // TODO: display error message if project is null

            var dialog = new TreeJavaClassChooserDialog("Main Class for Running Stories",
                    project,
                    GlobalSearchScope.allScope(project),
                    MainClassFilter.INSTANCE,
                    null);

            final PsiClass currentClass = JavaPsiFacade.getInstance(project)
                    .findClass(textField.getText(), GlobalSearchScope.allScope(project));

            //TODO: this is not working for some reason
            if (currentClass != null) {
                dialog.select(currentClass);
            }

            dialog.show();

            if (dialog.getExitCode() != DialogWrapper.CANCEL_EXIT_CODE) {
                textField.setText(dialog.getSelected().getQualifiedName());
            }
        }
    }

    private static class MainClassFilter implements ClassFilter {
        public static final ClassFilter INSTANCE = new MainClassFilter();

        @Override
        public boolean isAccepted(final PsiClass aClass) {
            return PsiMethodUtil.MAIN_CLASS.value(aClass) && PsiMethodUtil.findMainMethod(aClass) != null;
        }
    }
}
