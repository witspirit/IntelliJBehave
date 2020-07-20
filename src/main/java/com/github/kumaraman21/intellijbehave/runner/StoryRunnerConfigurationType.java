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
package com.github.kumaraman21.intellijbehave.runner;

import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.openapi.components.Service;
import org.jetbrains.annotations.NotNull;

public class StoryRunnerConfigurationType extends ApplicationConfigurationType {

    public static final String JBEHAVE_STORY_RUNNER = "JBehave Story Runner";

    @NotNull
    @Override
    public String getDisplayName() {
        return JBEHAVE_STORY_RUNNER;
    }

    @Override
    public String getConfigurationTypeDescription() {
        return "Runs a JBehave story file";
    }

    @NotNull
    @Override
    public String getId() {
        return "intellijbehave.storyrunner";
    }
}
