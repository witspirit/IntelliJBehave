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
package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class StoryElementType extends IElementType {
    public static final StoryElementType UNKNOWN_FRAGMENT = new StoryElementType("UNKNOWN_FRAGMENT");

    public static final StoryElementType COMMENT = new StoryElementType("COMMENT");
    public static final IFileElementType STORY_FILE = new IFileElementType(STORY_FILE_TYPE.getLanguage());
    public static final StoryElementType STORY = new StoryElementType("STORY");
    public static final StoryElementType STORY_DESCRIPTION = new StoryElementType("STORY_DESCRIPTION");
    public static final StoryElementType SCENARIO = new StoryElementType("SCENARIO");
    public static final StoryElementType META = new StoryElementType("META");
    public static final StoryElementType GIVEN_STORIES = new StoryElementType("GIVEN_STORIES");
    public static final StoryElementType GIVEN_STORIES_TEXT = new StoryElementType("STORY_PATH");
    public static final StoryElementType EXAMPLES = new StoryElementType("EXAMPLES");
    public static final StoryElementType TABLE_ROW = new StoryElementType("TABLE_ROW");

    public static final StoryElementType GIVEN_STEP = new StoryElementType("GIVEN_STEP");
    public static final StoryElementType WHEN_STEP = new StoryElementType("WHEN_STEP");
    public static final StoryElementType THEN_STEP = new StoryElementType("THEN_STEP");

    public static final TokenSet STEPS_TOKEN_SET = TokenSet.create(GIVEN_STEP, WHEN_STEP, THEN_STEP);


    public StoryElementType(@NotNull @NonNls String debugName) {
        super(debugName, STORY_FILE_TYPE.getLanguage());
    }
}
