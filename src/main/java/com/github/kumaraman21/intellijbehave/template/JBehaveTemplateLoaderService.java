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
package com.github.kumaraman21.intellijbehave.template;

import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;
import static com.intellij.openapi.util.io.FileUtil.loadTextAndClose;

@Service
public class JBehaveTemplateLoaderService {

  public JBehaveTemplateLoaderService() {
    FileTemplate template = FileTemplateManager.getInstance().getTemplate(STORY_FILE_TYPE.getName());
    if (template == null) {
      template = FileTemplateManager.getInstance()
        .addTemplate(STORY_FILE_TYPE.getName(), STORY_FILE_TYPE.getDefaultExtension());

      InputStream stream = getClass().getResourceAsStream("/fileTemplates/JBehave Story.story.ft");
      try {
        if(stream!=null)
          template.setText(loadTextAndClose(new InputStreamReader(stream)));
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      finally {
        try {
          if(stream!=null)
            stream.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
