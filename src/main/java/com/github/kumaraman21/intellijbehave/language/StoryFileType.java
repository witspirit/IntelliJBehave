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
package com.github.kumaraman21.intellijbehave.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static com.github.kumaraman21.intellijbehave.language.StoryLanguage.STORY_LANGUAGE;

public class StoryFileType extends LanguageFileType {
    //In case this is called something else, e.g. INSTANCE, the file type icon/file type doesn't get registered
    public static final StoryFileType STORY_FILE_TYPE = new StoryFileType();

    protected StoryFileType() {
        super(STORY_LANGUAGE);
    }

    @NotNull
    @Override
    public String getName() {
        return "JBehave Story";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "JBehave story files";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "story";
    }

    @Override
    public Icon getIcon() {
        return JBehaveIcons.JB;
    }
}
