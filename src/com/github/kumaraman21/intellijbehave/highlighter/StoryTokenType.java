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
package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NonNls;

import static com.github.kumaraman21.intellijbehave.language.StoryFileType.STORY_FILE_TYPE;

public class StoryTokenType extends IElementType {

    public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    public static final IElementType PUNCTUATION = new StoryTokenType("PUNCTUATION");

    public static final IElementType STORY_DESCRIPTION = new StoryTokenType("STORY_DESCRIPTION");
    public static final IElementType SCENARIO_TYPE = new StoryTokenType("SCENARIO_TYPE");
    public static final IElementType SCENARIO_TEXT = new StoryTokenType("SCENARIO_TEXT");

    public static final IElementType GIVEN_TYPE = new StoryTokenType("GIVEN_TYPE");
    public static final IElementType WHEN_TYPE = new StoryTokenType("WHEN_TYPE");
    public static final IElementType THEN_TYPE = new StoryTokenType("THEN_TYPE");
    public static final IElementType STEP_TYPE_GIVEN = new StoryTokenType("STEP_TYPE_GIVEN");
    public static final IElementType STEP_TYPE_WHEN = new StoryTokenType("STEP_TYPE_WHEN");
    public static final IElementType STEP_TYPE_THEN = new StoryTokenType("STEP_TYPE_THEN");
    public static final IElementType STEP_TYPE_AND = new StoryTokenType("STEP_TYPE_AND");

    public static final TokenSet STEP_TYPES = TokenSet.create(GIVEN_TYPE, WHEN_TYPE, THEN_TYPE, STEP_TYPE_GIVEN, STEP_TYPE_WHEN, STEP_TYPE_THEN, STEP_TYPE_AND);

    public static final IElementType STEP_TEXT = new StoryTokenType("STEP_TEXT");

    public static final IElementType TABLE_DELIM = new StoryTokenType("TABLE_DELIM");
    public static final IElementType TABLE_CELL = new StoryTokenType("TABLE_CELL");

    public static final IElementType COMMENT = new StoryTokenType("COMMENT");
    public static final IElementType COMMENT_WITH_LOCALE = new StoryTokenType("COMMENT_WITH_LOCALE");

    public static final IElementType META = new StoryTokenType("META");
    public static final IElementType META_KEY = new StoryTokenType("META_KEY");
    public static final IElementType META_TEXT = new StoryTokenType("META_TEXT");
    public static final IElementType EXAMPLE_TYPE = new StoryTokenType("EXAMPLE_TYPE");
    public static final IElementType GIVEN_STORIES_TYPE = new StoryTokenType("GIVEN_STORIES_TYPE");
    public static final IElementType STORY_PATH = new StoryTokenType("STORY_PATH");

    public static final IElementType NARRATIVE_TYPE = new StoryTokenType("NARRATIVE_TYPE");
    public static final IElementType NARRATIVE_TEXT = new StoryTokenType("NARRATIVE_TEXT");

    private final String key;

    public StoryTokenType(@NonNls String debugName) {
        super(debugName, STORY_FILE_TYPE.getLanguage());
        this.key = debugName;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof StoryTokenType
                && ((StoryTokenType) other).key.equals(key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
