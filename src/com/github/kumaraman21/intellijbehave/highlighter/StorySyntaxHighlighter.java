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

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.CodeInsightColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class StorySyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new THashMap<IElementType, TextAttributesKey>();

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new StorySyntaxHighlightingLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }

    @NonNls
    public static final String STORY_DESCRIPTION_ID = "JBEHAVE.STORY_DESCRIPTION";
    @NonNls
    public static final String SCENARIO_TYPE_ID = "JBEHAVE.SCENARIO_TYPE";
    @NonNls
    public static final String SCENARIO_TEXT_ID = "JBEHAVE.SCENARIO_TEXT";
    @NonNls
    public static final String STEP_TYPE_ID = "JBEHAVE.STEP_TYPE";
    @NonNls
    public static final String STEP_TEXT_ID = "JBEHAVE.STEP_TEXT";
    @NonNls
    public static final String STEP_PARAMETER_ID = "JBEHAVE.STEP_PARAMETER";
    @NonNls
    public static final String TABLE_DELIM_ID = "JBEHAVE.TABLE_DELIM";
    @NonNls
    public static final String TABLE_CELL_ID = "JBEHAVE.TABLE_CELL";
    @NonNls
    public static final String META_TYPE_ID = "JBEHAVE.META_TYPE";
    @NonNls
    public static final String META_KEY_ID = "JBEHAVE.META_KEY";
    @NonNls
    public static final String META_TEXT_ID = "JBEHAVE.META_TEXT";
    @NonNls
    public static final String LINE_COMMENT_ID = "JBEHAVE.COMMENT";
    @NonNls
    public static final String BAD_CHARACTER_ID = "JBEHAVE.BAD_CHARACTER";

    // Registering TextAttributes
    static {
        createKey(STORY_DESCRIPTION_ID, DefaultLanguageHighlighterColors.NUMBER);
        createKey(SCENARIO_TYPE_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(SCENARIO_TEXT_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(STEP_TYPE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STEP_TEXT_ID, HighlighterColors.TEXT);
        createKey(STEP_PARAMETER_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(TABLE_DELIM_ID, DefaultLanguageHighlighterColors.BRACES);
        createKey(TABLE_CELL_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(META_TYPE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(META_KEY_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(META_TEXT_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(LINE_COMMENT_ID, DefaultLanguageHighlighterColors.LINE_COMMENT);
        createKey(BAD_CHARACTER_ID, DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);
    }

    public static TextAttributesKey STORY_DESCRIPTION = createTextAttributesKey(STORY_DESCRIPTION_ID);
    public static TextAttributesKey SCENARIO_TYPE = createTextAttributesKey(SCENARIO_TYPE_ID);
    public static TextAttributesKey SCENARIO_TEXT = createTextAttributesKey(SCENARIO_TEXT_ID);
    public static TextAttributesKey STEP_TYPE = createTextAttributesKey(STEP_TYPE_ID);
    public static TextAttributesKey STEP_TEXT = createTextAttributesKey(STEP_TEXT_ID);
    public static TextAttributesKey STEP_PARAMETER = createTextAttributesKey(STEP_PARAMETER_ID);
    public static TextAttributesKey TABLE_DELIM = createTextAttributesKey(TABLE_DELIM_ID);
    public static TextAttributesKey TABLE_CELL = createTextAttributesKey(TABLE_CELL_ID);
    public static TextAttributesKey META_TYPE = createTextAttributesKey(META_TYPE_ID);
    public static TextAttributesKey META_KEY = createTextAttributesKey(META_KEY_ID);
    public static TextAttributesKey META_TEXT = createTextAttributesKey(META_TEXT_ID);
    public static TextAttributesKey LINE_COMMENT = createTextAttributesKey(LINE_COMMENT_ID);
    public static TextAttributesKey BAD_CHARACTER = createTextAttributesKey(BAD_CHARACTER_ID);

    static {
        ATTRIBUTES.put(StoryTokenType.STORY_DESCRIPTION, STORY_DESCRIPTION);
        ATTRIBUTES.put(StoryTokenType.NARRATIVE_TYPE, STORY_DESCRIPTION);
        ATTRIBUTES.put(StoryTokenType.NARRATIVE_TEXT, STORY_DESCRIPTION);
        ATTRIBUTES.put(StoryTokenType.SCENARIO_TYPE, SCENARIO_TYPE);
        ATTRIBUTES.put(StoryTokenType.SCENARIO_TEXT, SCENARIO_TEXT);
        ATTRIBUTES.put(StoryTokenType.GIVEN_TYPE, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.WHEN_TYPE, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.THEN_TYPE, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.STEP_TYPE_GIVEN, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.STEP_TYPE_WHEN, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.STEP_TYPE_THEN, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.STEP_TYPE_AND, STEP_TYPE);
        ATTRIBUTES.put(StoryTokenType.STEP_TEXT, STEP_TEXT);
        ATTRIBUTES.put(StoryTokenType.TABLE_DELIM, TABLE_DELIM);
        ATTRIBUTES.put(StoryTokenType.TABLE_CELL, TABLE_CELL);
        ATTRIBUTES.put(StoryTokenType.META, META_TYPE);
        ATTRIBUTES.put(StoryTokenType.META_KEY, META_KEY);
        ATTRIBUTES.put(StoryTokenType.META_TEXT, META_TEXT);
        ATTRIBUTES.put(StoryTokenType.COMMENT, LINE_COMMENT);
        ATTRIBUTES.put(StoryTokenType.COMMENT_WITH_LOCALE, LINE_COMMENT);
        ATTRIBUTES.put(StoryTokenType.BAD_CHARACTER, BAD_CHARACTER);
    }

    private static TextAttributesKey createKey(String externalName, TextAttributesKey textAttributesKey) {
        return createTextAttributesKey(externalName, textAttributesKey);
    }
}
