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
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = "JBehaveSettings",
        storages = {@Storage(
                file = "$APP_CONFIG$/jbehave_settings.xml"
        )}
)
@Service
public class JBehaveSettings implements PersistentStateComponent<JBehaveSettings> {

    private String storyRunner;
    private boolean storyAutoCompletion;

    public static JBehaveSettings getInstance() {
        return ServiceManager.getService(JBehaveSettings.class);
    }

    @Override
    public JBehaveSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull JBehaveSettings jBehaveSettings) {
        XmlSerializerUtil.copyBean(jBehaveSettings, this);
    }

    public String getStoryRunner() {
        return storyRunner;
    }

    public void setStoryRunner(String storyRunner) {
        this.storyRunner = storyRunner;
    }

    public boolean isStoryAutoCompletion() {
        return storyAutoCompletion;
    }

    public void setStoryAutoCompletion(boolean storyAutoCompletion) {
        this.storyAutoCompletion = storyAutoCompletion;
    }
}
