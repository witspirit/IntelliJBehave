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

import com.github.kumaraman21.intellijbehave.settings.JBehaveSettings;
import com.google.common.collect.Streams;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.*;

public class StoryFileTypeFactory extends FileTypeFactory {
  private static final Pattern TRIM_PATTERN = Pattern.compile("^\\s*\\.?(.*?)\\s*$");

  @Override
  public void createFileTypes(@NotNull final FileTypeConsumer fileTypeConsumer) {
    fileTypeConsumer.consume(STORY_FILE_TYPE, STORY_FILE_TYPE.getDefaultExtension());

    JBehaveSettings settings = JBehaveSettings.getInstance();
    Arrays.stream(settings.getFileExtensions().split(","))
      .forEach((extension) -> {
        Matcher m = TRIM_PATTERN.matcher(extension);
        if(m.find()){
          String formatted = m.group(1);
          fileTypeConsumer.consume(STORY_FILE_TYPE, formatted);
        }
      });
  }
}
