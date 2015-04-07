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

import static com.github.kumaraman21.intellijbehave.language.JBehaveFileType.JBEHAVE_FILE_TYPE;

public class JBehaveElementType extends IElementType {
    public static final JBehaveElementType UNKNOWN_FRAGMENT = new JBehaveElementType("UNKNOWN_FRAGMENT");

    public static final JBehaveElementType COMMENT = new JBehaveElementType("COMMENT");
    public static final IFileElementType STORY_FILE = new IFileElementType(JBEHAVE_FILE_TYPE.getLanguage());
    public static final JBehaveElementType STORY = new JBehaveElementType("STORY");
    public static final JBehaveElementType STORY_DESCRIPTION = new JBehaveElementType("STORY_DESCRIPTION");
    public static final JBehaveElementType SCENARIO = new JBehaveElementType("SCENARIO");
    public static final JBehaveElementType META = new JBehaveElementType("META");
    public static final JBehaveElementType GIVEN_STORIES = new JBehaveElementType("GIVEN_STORIES");
    public static final JBehaveElementType GIVEN_STORIES_TEXT = new JBehaveElementType("STORY_PATH");
    public static final JBehaveElementType EXAMPLES = new JBehaveElementType("EXAMPLES");
    public static final JBehaveElementType TABLE_ROW = new JBehaveElementType("TABLE_ROW");

    public static final JBehaveElementType GIVEN_STEP = new JBehaveElementType("GIVEN_STEP");
    public static final JBehaveElementType WHEN_STEP = new JBehaveElementType("WHEN_STEP");
    public static final JBehaveElementType THEN_STEP = new JBehaveElementType("THEN_STEP");

    public static final TokenSet STEPS_TOKEN_SET = TokenSet.create(GIVEN_STEP, WHEN_STEP, THEN_STEP);


    public JBehaveElementType(@NotNull @NonNls String debugName) {
        super(debugName, JBEHAVE_FILE_TYPE.getLanguage());
    }
}
