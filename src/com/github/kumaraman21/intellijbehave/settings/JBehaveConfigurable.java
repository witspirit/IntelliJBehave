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

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JBehaveConfigurable implements SearchableConfigurable {
    private final Project project;
    private JBehaveSettingsPanel jBehaveSettingsPanel;

    public JBehaveConfigurable(Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public String getId() {
        return getHelpTopic();
    }

    @Override
    public Runnable enableSearch(String s) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "JBehave";
    }

    @Override
    public String getHelpTopic() {
        return "reference.settingsdialog.project.jbehave";
    }

    @Override
    public JComponent createComponent() {
        if (jBehaveSettingsPanel == null) {
            jBehaveSettingsPanel = new JBehaveSettingsPanel(project);
        }
        reset();
        return jBehaveSettingsPanel.getContentPane();
    }

    @Override
    public boolean isModified() {
        if (jBehaveSettingsPanel != null) {
            return jBehaveSettingsPanel.isModified();
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (jBehaveSettingsPanel != null) {
            jBehaveSettingsPanel.apply();
        }
    }

    @Override
    public void reset() {
        if (jBehaveSettingsPanel != null) {
            jBehaveSettingsPanel.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        jBehaveSettingsPanel = null;
    }
}
