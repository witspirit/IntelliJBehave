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

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = "JBehaveSettings",
        storages = {@Storage(
                id = "main",
                file = "$PROJECT_CONFIG_DIR$/jbehave_settings.xml")})
public class JBehaveSettings implements ProjectComponent, PersistentStateComponent<JBehaveSettings> {
    private String storyRunner;

    public static JBehaveSettings getInstance(Project project) {
        return ServiceManager.getService(project, JBehaveSettings.class);
    }

    @Override
    public JBehaveSettings getState() {
        return this;
    }

    @Override
    public void loadState(JBehaveSettings jBehaveSettings) {
        XmlSerializerUtil.copyBean(jBehaveSettings, this);
    }

    public String getStoryRunner() {
        return storyRunner;
    }

    public void setStoryRunner(String storyRunner) {
        this.storyRunner = storyRunner;
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "JBehaveSettings";
    }

    @Override
    public void projectOpened() {

    }

    @Override
    public void projectClosed() {

    }
}
