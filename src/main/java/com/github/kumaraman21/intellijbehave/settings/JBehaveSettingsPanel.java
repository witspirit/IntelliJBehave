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
import com.intellij.ide.plugins.newui.PluginSiteUtils;
import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeJavaClassChooserDialog;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.application.ex.ApplicationUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiMethodUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.kotlin.utils.PluginUtilsKt;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JBehaveSettingsPanel {
    private static final Pattern FILE_EXTENSIONS_PATTERN = Pattern.compile("^\\s*\\.[a-zA-Z0-9]+?\\s*(,\\s*\\.[a-zA-Z0-9]+?\\s*)*$");

    private static final ClassFilter MAIN_CLASS_FILTER = new MainClassFilter();

    private JPanel contentPane;
    private JLabel storyRunnerLabel;
    private TextFieldWithBrowseButton storyRunnerField;
    private JCheckBox enableCodeCompletionInCheckBox;
    private JLabel storyFileExtensionLabel;
    private JTextField storyFileExtensionField;
    private JLabel restartWarningLabel;
    private JLabel storyFileExtensionInvalidLabel;

    private JBehaveSettings jBehaveSettings;

    private boolean fileExtensionFormatValid = true;

    public JBehaveSettingsPanel() {
        jBehaveSettings = JBehaveSettings.getInstance();
        storyRunnerField.addActionListener(new BrowseMainClassListener(storyRunnerField));
        storyFileExtensionField.getDocument().addDocumentListener(new NeedRestartDocumentListener());
        restartWarningLabel.setVisible(false);
        storyFileExtensionInvalidLabel.setVisible(false);
    }

    private void checkFileExtensionsValid() {
        String trimmedFileExtensions = StringUtils.trim(storyFileExtensionField.getText());
        fileExtensionFormatValid = "".equals(trimmedFileExtensions) || FILE_EXTENSIONS_PATTERN.matcher(trimmedFileExtensions).matches();
        storyFileExtensionInvalidLabel.setVisible(!fileExtensionFormatValid);
    }

    public void apply() {
        jBehaveSettings.setStoryRunner(storyRunnerField.getText());
        jBehaveSettings.setStoryAutoCompletion(enableCodeCompletionInCheckBox.isSelected());
        jBehaveSettings.setFileExtensions(storyFileExtensionField.getText());
    }

    public void reset() {
        storyRunnerField.setText(jBehaveSettings.getStoryRunner());
        storyFileExtensionField.setText(jBehaveSettings.getFileExtensions());
        enableCodeCompletionInCheckBox.setSelected(jBehaveSettings.isStoryAutoCompletion());
    }

    public boolean isModified() {
	    boolean storyRunnerChanged = !storyRunnerField.getText().equals(jBehaveSettings.getStoryRunner());
	    boolean storyCodeCompletionChanged = !Objects.equals(enableCodeCompletionInCheckBox.isSelected(), jBehaveSettings.isStoryAutoCompletion());
	    boolean storyFileExtensionChanged = !Objects.equals(storyFileExtensionField.getText(), jBehaveSettings.getFileExtensions());
	    return fileExtensionFormatValid && (storyRunnerChanged || storyCodeCompletionChanged || storyFileExtensionChanged);
    }

    public boolean isNeedRestartOptionsModified() {
        boolean storyFileExtensionChanged = !Objects.equals(storyFileExtensionField.getText(), jBehaveSettings.getFileExtensions());

        return storyFileExtensionChanged;
    }

    private class NeedRestartDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            checkFileExtensionsValid();
            manageWarning();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            checkFileExtensionsValid();
            manageWarning();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            checkFileExtensionsValid();
            manageWarning();
        }

        private void manageWarning() {
            boolean warningVisible = false;
            if(isModified() && isNeedRestartOptionsModified()) {
                warningVisible = true;
            }
            restartWarningLabel.setVisible(warningVisible);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        storyRunnerLabel = new JLabel();
        storyRunnerLabel.setText("Main class for running stories");
        storyRunnerLabel.setToolTipText("Class with a main function to receive the story file to run as a parameter");
        contentPane.add(storyRunnerLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        storyRunnerField = new TextFieldWithBrowseButton();
        storyRunnerField.setAlignmentX(0.5f);
        storyRunnerField.setPreferredSize(new Dimension(400, 28));
        contentPane.add(storyRunnerField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
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
