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
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiMethodUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBehaveSettingsPanel {

    private static final ClassFilter MAIN_CLASS_FILTER = new MainClassFilter();

    private JPanel contentPane;
    private JLabel storyRunnerLabel;
    private TextFieldWithBrowseButton storyRunnerField;

    private JBehaveSettings jBehaveSettings;

    public JBehaveSettingsPanel() {
        jBehaveSettings = JBehaveSettings.getInstance();
        storyRunnerField.addActionListener(new BrowseMainClassListener(storyRunnerField));
    }

    public void apply() {
        jBehaveSettings.setStoryRunner(storyRunnerField.getText());
    }

    public void reset() {
        storyRunnerField.setText(jBehaveSettings.getStoryRunner());
    }

    public boolean isModified() {
        return !storyRunnerField.getText().equals(jBehaveSettings.getStoryRunner());
    }

    private class BrowseMainClassListener implements ActionListener {
        private TextFieldWithBrowseButton textField;

        public BrowseMainClassListener(TextFieldWithBrowseButton textField) {
            this.textField = textField;
        }

        public void actionPerformed(ActionEvent e) {
            DataContext dataContext = DataManager.getInstance().getDataContext(JBehaveSettingsPanel.this.contentPane);
            Project project = DataKeys.PROJECT.getData(dataContext);

            // TODO: display error message if project is null

            TreeJavaClassChooserDialog dialog = new TreeJavaClassChooserDialog("Main class for running stories",
                    project,
                    GlobalSearchScope.allScope(project),
                    MAIN_CLASS_FILTER,
                    null);

            final PsiClass currentClass = JavaPsiFacade.getInstance(project).findClass(textField.getText(),
                    GlobalSearchScope.allScope(project));

            //TODO: this is not working for some reason
            if (currentClass != null) {
                dialog.select(currentClass);
            }

            dialog.show();

            if (dialog.getExitCode() == TreeJavaClassChooserDialog.CANCEL_EXIT_CODE) {
                return;
            }

            textField.setText(dialog.getSelected().getQualifiedName());
        }
    }

    public JPanel getContentPane() {
        return this.contentPane;
    }

    private static class MainClassFilter implements ClassFilter {
        @Override
        public boolean isAccepted(final PsiClass aClass) {
            return ConfigurationUtil.MAIN_CLASS.value(aClass) && PsiMethodUtil.findMainMethod(aClass) != null;
        }
    }
}
