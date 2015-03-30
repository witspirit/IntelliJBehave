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

import com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType;
import com.github.kumaraman21.intellijbehave.peg._StoryPegLexer;
import com.intellij.lexer.FlexAdapter;
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

import java.io.Reader;
import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class StorySyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new THashMap<IElementType, TextAttributesKey>();

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new _StoryPegLexer((Reader) null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }

    @NonNls
    public static final String STORY_DESCRIPTION_ID = "JBEHAVE.STORY_DESCRIPTION";
    public static final String STORY_EXAMPLES_ID = "JBEHAVE.STORY_EXAMPLES";
    public static final String STORY_GIVEN_STORIES_ID = "JBEHAVE.STORY_GIVEN_STORIES";
    public static final String STORY_INJECT_ID = "JBEHAVE.STORY_INJECT";
    public static final String STORY_INJECT_IDENTIFIER_ID = "JBEHAVE.STORY_INJECT_IDENTIFIER";
    public static final String STORY_INJECT_SEPARATOR_ID = "JBEHAVE.STORY_INJECT_SEPARATOR";
    public static final String STORY_IP_ADDRESS_ID = "JBEHAVE.STORY_IP_ADDRESS";
    public static final String STORY_LIFECYCLE_ID = "JBEHAVE.STORY_LIFECYCLE";
    public static final String STORY_LIFECYCLE_AFTER_ID = "JBEHAVE.STORY_LIFECYCLE_AFTER";
    public static final String STORY_LIFECYCLE_BEFORE_ID = "JBEHAVE.STORY_LIFECYCLE_BEFORE";
    public static final String STORY_META_ELEMENT_ID = "JBEHAVE.STORY_META_ELEMENT";
    public static final String STORY_META_KEY_ID = "JBEHAVE.STORY_META_KEY";
    public static final String STORY_META_STATEMENT_ID = "JBEHAVE.STORY_META_STATEMENT";
    public static final String STORY_META_VALUE_ID = "JBEHAVE.STORY_META_VALUE";
    public static final String STORY_MULTI_TEXT_LINE_ID = "JBEHAVE.STORY_MULTI_TEXT";
    public static final String STORY_NARRATIVE_ID = "JBEHAVE.STORY_NARRATIVE";
    public static final String STORY_NARRATIVE_TEXT_ID = "JBEHAVE.STORY_NARRATIVE_TEXT";
    public static final String STORY_RECOVER_STEP_ID = "JBEHAVE.STORY_RECOVER_STEP";
    public static final String STORY_SCENARIO_ID = "JBEHAVE.STORY_SCENARIO";
    public static final String STORY_SCENARIO_TITLE_ID = "JBEHAVE.STORY_SCENARIO_TITLE";
    public static final String STORY_STEP_ID = "JBEHAVE.STORY_STEP";
    public static final String STORY_STEP_ARGUMENT_ID = "JBEHAVE.STORY_STEP_ARGUMENT";
    public static final String STORY_STEP_COMMENT_ID = "JBEHAVE.STORY_STEP_COMMENT";
    public static final String STORY_STEP_LINE_ID = "JBEHAVE.STORY_STEP_LINE";
    public static final String STORY_STEP_PAR_ID = "JBEHAVE.STORY_STEP_PAR";
    public static final String STORY_STEP_POST_PARAMETER_ID = "JBEHAVE.STORY_STEP_POST";
    public static final String STORY_STORY_ID = "JBEHAVE.STORY_STORY";
    public static final String STORY_STORY_PATH_ID = "JBEHAVE.STORY_STORY_PATH";
    public static final String STORY_STORY_PATHS_ID = "JBEHAVE.STORY_STORY_PATHS";
    public static final String STORY_TABLE_ID = "JBEHAVE.STORY_TABLE";
    public static final String STORY_TABLE_CELL_EMPTY_ID = "JBEHAVE.STORY_TABLE_CELL_EMPTY";
    public static final String STORY_TABLE_CELL_ID = "JBEHAVE.STORY_TABLE_CELL";
    public static final String STORY_TABLE_ROW_ID = "JBEHAVE.STORY_TABLE_ROW";
    public static final String STORY_URI_ID = "JBEHAVE.STORY_URI";
    public static final String STORY_URI_IDENTIFIER_ID = "JBEHAVE.STORY_URI_IDENTIFIER";
    public static final String STORY_URI_WORD_ID = "JBEHAVE.STORY_URI_WORD";
    public static final String STORY_USER_INJECT_ID = "JBEHAVE.STORY_USER_INJECT";

    public static final String STORY_TOKEN_AFTER_ID = "JBEHAVE.STORY_TOKEN_AFTER";
    public static final String STORY_TOKEN_AND_ID = "JBEHAVE.STORY_TOKEN_AND";
    public static final String STORY_TOKEN_BEFORE_ID = "JBEHAVE.STORY_TOKEN_BEFORE";
    public static final String STORY_TOKEN_EXAMPLES_ID = "JBEHAVE.STORY_TOKEN_EXAMPLES";
    public static final String STORY_TOKEN_GIVEN_STORIES_ID = "JBEHAVE.STORY_TOKEN_GIVEN_STORIES";
    public static final String STORY_TOKEN_GIVEN_ID = "JBEHAVE.STORY_TOKEN_GIVEN";
    public static final String STORY_TOKEN_LIFECYCLE_ID = "JBEHAVE.STORY_TOKEN_LIFECYCLE";
    public static final String STORY_TOKEN_META_ID = "JBEHAVE.STORY_TOKEN_META";
    public static final String STORY_TOKEN_NARRATIVE_ID = "JBEHAVE.STORY_TOKEN_NARRATIVE";
    public static final String STORY_TOKEN_SCENARIO_ID = "JBEHAVE.STORY_TOKEN_SCENARIO";
    public static final String STORY_TOKEN_THEN_ID = "JBEHAVE.STORY_TOKEN_THEN";
    public static final String STORY_TOKEN_WHEN_ID = "JBEHAVE.STORY_TOKEN_WHEN";
    public static final String STORY_TOKEN_PIPE_ID = "JBEHAVE.STORY_TOKEN_PIPE";
    public static final String STORY_TOKEN_INJECT_ID = "JBEHAVE.STORY_TOKEN_INJECT";
    public static final String STORY_TOKEN_USER_INJECT_ID = "JBEHAVE.STORY_TOKEN_USER_INJECT";
    public static final String STORY_TOKEN_COMMENT_ID = "JBEHAVE.STORY_COMMENT_ID";
    public static final String STEP_PARAMETER_ID = "JBEHAVE.STEP_PARAMETER";
    public static final String STORY_TOKEN_PATH_ID = "JBEHAVE.STORY_TOKEN_PATH";


    // Registering TextAttributes
    static {
        createKey(STORY_DESCRIPTION_ID, HighlighterColors.TEXT);
        createKey(STORY_INJECT_ID, DefaultLanguageHighlighterColors.FUNCTION_CALL);
        createKey(STORY_IP_ADDRESS_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_LIFECYCLE_AFTER_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_LIFECYCLE_BEFORE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_META_ELEMENT_ID, HighlighterColors.TEXT);
        createKey(STORY_META_KEY_ID, HighlighterColors.TEXT);
        createKey(STORY_META_STATEMENT_ID, HighlighterColors.TEXT);
        createKey(STORY_META_VALUE_ID, HighlighterColors.TEXT);
        createKey(STORY_MULTI_TEXT_LINE_ID, HighlighterColors.TEXT);
        createKey(STORY_NARRATIVE_TEXT_ID, HighlighterColors.TEXT);
        createKey(STORY_SCENARIO_TITLE_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(STORY_STEP_COMMENT_ID, DefaultLanguageHighlighterColors.LINE_COMMENT);
        createKey(STORY_STEP_LINE_ID, HighlighterColors.TEXT);
        //createKey(STORY_STORY_PATH_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(STORY_TABLE_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(STORY_TABLE_CELL_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(STORY_URI_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(STORY_USER_INJECT_ID, DefaultLanguageHighlighterColors.FUNCTION_CALL);
        createKey(STEP_PARAMETER_ID, DefaultLanguageHighlighterColors.STRING);


        createKey(STORY_TOKEN_AFTER_ID, DefaultLanguageHighlighterColors.NUMBER);
        createKey(STORY_TOKEN_AND_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_BEFORE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_EXAMPLES_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_GIVEN_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_GIVEN_STORIES_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_LIFECYCLE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_META_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_NARRATIVE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_SCENARIO_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(STORY_TOKEN_THEN_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_WHEN_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_PIPE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_INJECT_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_USER_INJECT_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(STORY_TOKEN_PATH_ID, DefaultLanguageHighlighterColors.STRING);
    }

    public static TextAttributesKey STORY_DESCRIPTION = createTextAttributesKey(STORY_DESCRIPTION_ID);
    public static TextAttributesKey STORY_EXAMPLES = createTextAttributesKey(STORY_EXAMPLES_ID);
    public static TextAttributesKey STORY_GIVEN_STORIES = createTextAttributesKey(STORY_GIVEN_STORIES_ID);
    public static TextAttributesKey STORY_IP_ADDRESS = createTextAttributesKey(STORY_IP_ADDRESS_ID);
    public static TextAttributesKey STORY_LIFECYCLE = createTextAttributesKey(STORY_LIFECYCLE_ID);
    public static TextAttributesKey STORY_LIFECYCLE_AFTER = createTextAttributesKey(STORY_LIFECYCLE_AFTER_ID);
    public static TextAttributesKey STORY_LIFECYCLE_BEFORE = createTextAttributesKey(STORY_LIFECYCLE_BEFORE_ID);
    public static TextAttributesKey STORY_META_ELEMENT = createTextAttributesKey(STORY_META_ELEMENT_ID);
    public static TextAttributesKey STORY_META_KEY = createTextAttributesKey(STORY_META_KEY_ID);
    public static TextAttributesKey STORY_META_STATEMENT = createTextAttributesKey(STORY_META_STATEMENT_ID);
    public static TextAttributesKey STORY_META_VALUE = createTextAttributesKey(STORY_META_VALUE_ID);
    //public static TextAttributesKey STORY_MULTI_TEXT_LINE = createTextAttributesKey(STORY_MULTI_TEXT_LINE_ID);
    public static TextAttributesKey STORY_NARRATIVE = createTextAttributesKey(STORY_NARRATIVE_ID);
    public static TextAttributesKey STORY_NARRATIVE_TEXT = createTextAttributesKey(STORY_NARRATIVE_TEXT_ID);
    public static TextAttributesKey STORY_RECOVER_STEP = createTextAttributesKey(STORY_RECOVER_STEP_ID);
//    public static TextAttributesKey STORY_SCENARIO = createTextAttributesKey(STORY_SCENARIO_ID);
    public static TextAttributesKey STORY_SCENARIO_TITLE = createTextAttributesKey(STORY_SCENARIO_TITLE_ID);
//    public static TextAttributesKey STORY_STEP = createTextAttributesKey(STORY_STEP_ID);
//    public static TextAttributesKey STORY_STEP_ARGUMENT = createTextAttributesKey(STORY_STEP_ARGUMENT_ID);
    public static TextAttributesKey STORY_STEP_LINE = createTextAttributesKey(STORY_STEP_LINE_ID);
//    public static TextAttributesKey STORY_STEP_PAR = createTextAttributesKey(STORY_STEP_PAR_ID);
    public static TextAttributesKey STORY_STEP_POST_PARAMETER = createTextAttributesKey(STORY_STEP_POST_PARAMETER_ID);
    public static TextAttributesKey STORY_STORY = createTextAttributesKey(STORY_STORY_ID);
    //public static TextAttributesKey STORY_TOKEN_PATH = createTextAttributesKey(STORY_STORY_PATH_ID);
//    public static TextAttributesKey STORY_STORY_PATHS = createTextAttributesKey(STORY_STORY_PATHS_ID);
    public static TextAttributesKey STORY_TABLE = createTextAttributesKey(STORY_TABLE_ID);
    public static TextAttributesKey STORY_TABLE_CELL = createTextAttributesKey(STORY_TABLE_CELL_ID);
//    public static TextAttributesKey STORY_TABLE_CELL_EMPTY = createTextAttributesKey(STORY_TABLE_CELL_EMPTY_ID);
//    public static TextAttributesKey STORY_TABLE_ROW = createTextAttributesKey(STORY_TABLE_ROW_ID);
    public static TextAttributesKey STORY_URI = createTextAttributesKey(STORY_URI_ID);
//    public static TextAttributesKey STORY_URI_IDENTIFIER = createTextAttributesKey(STORY_URI_IDENTIFIER_ID);
//    public static TextAttributesKey STORY_URI_WORD = createTextAttributesKey(STORY_URI_WORD_ID);
    public static TextAttributesKey STEP_PARAMETER = createTextAttributesKey(STEP_PARAMETER_ID);

    public static TextAttributesKey STORY_TOKEN_AFTER = createTextAttributesKey(STORY_TOKEN_AFTER_ID);
    public static TextAttributesKey STORY_TOKEN_AND = createTextAttributesKey(STORY_TOKEN_AND_ID);
    public static TextAttributesKey STORY_TOKEN_BEFORE = createTextAttributesKey(STORY_TOKEN_BEFORE_ID);
    public static TextAttributesKey STORY_TOKEN_EXAMPLES = createTextAttributesKey(STORY_TOKEN_EXAMPLES_ID);
    public static TextAttributesKey STORY_TOKEN_GIVEN = createTextAttributesKey(STORY_TOKEN_GIVEN_ID);
    public static TextAttributesKey STORY_TOKEN_GIVEN_STORIES = createTextAttributesKey(STORY_TOKEN_GIVEN_STORIES_ID);
    public static TextAttributesKey STORY_TOKEN_LIFECYCLE = createTextAttributesKey(STORY_TOKEN_LIFECYCLE_ID);
    public static TextAttributesKey STORY_TOKEN_META = createTextAttributesKey(STORY_TOKEN_META_ID);
    public static TextAttributesKey STORY_TOKEN_NARRATIVE = createTextAttributesKey(STORY_TOKEN_NARRATIVE_ID);
    public static TextAttributesKey STORY_TOKEN_SCENARIO = createTextAttributesKey(STORY_TOKEN_SCENARIO_ID);
    public static TextAttributesKey STORY_TOKEN_THEN = createTextAttributesKey(STORY_TOKEN_THEN_ID);
    public static TextAttributesKey STORY_TOKEN_WHEN = createTextAttributesKey(STORY_TOKEN_WHEN_ID);
    public static TextAttributesKey STORY_TOKEN_PIPE = createTextAttributesKey(STORY_TOKEN_PIPE_ID);
    public static TextAttributesKey STORY_TOKEN_INJECT = createTextAttributesKey(STORY_TOKEN_INJECT_ID);
    public static TextAttributesKey STORY_TOKEN_USERINJECT = createTextAttributesKey(STORY_TOKEN_USER_INJECT_ID);
    public static TextAttributesKey STORY_TOKEN_COMMENT = createTextAttributesKey(STORY_TOKEN_COMMENT_ID);
    public static TextAttributesKey STORY_TOKEN_PATH = createTextAttributesKey(STORY_TOKEN_PATH_ID);

    static {
        ATTRIBUTES.put(IStoryPegElementType.STORY_DESCRIPTION, STORY_DESCRIPTION);
        ATTRIBUTES.put(IStoryPegElementType.STORY_EXAMPLES, STORY_EXAMPLES);
        ATTRIBUTES.put(IStoryPegElementType.STORY_GIVEN_STORIES, STORY_GIVEN_STORIES);
        //ATTRIBUTES.put(IStoryPegElementType.STORY_INJECT, STORY_INJECT);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_INJECT_IDENTIFIER, STORY_INJECT_IDENTIFIER);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_INJECT_SEPARATOR, STORY_INJECT_SEPARATOR);
        ATTRIBUTES.put(IStoryPegElementType.STORY_IP_ADDRESS, STORY_IP_ADDRESS);
        ATTRIBUTES.put(IStoryPegElementType.STORY_LIFECYCLE, STORY_LIFECYCLE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_LIFECYCLE_AFTER, STORY_LIFECYCLE_AFTER);
        ATTRIBUTES.put(IStoryPegElementType.STORY_LIFECYCLE_BEFORE, STORY_LIFECYCLE_BEFORE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_META_ELEMENT, STORY_META_ELEMENT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_META_KEY, STORY_META_KEY);
        ATTRIBUTES.put(IStoryPegElementType.STORY_META_STATEMENT, STORY_META_STATEMENT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_META_VALUE, STORY_META_VALUE);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_MULTI_TEXT_LINE, STORY_MULTI_TEXT_LINE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_NARRATIVE, STORY_NARRATIVE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_NARRATIVE_TEXT, STORY_NARRATIVE_TEXT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_RECOVER_STEP, STORY_RECOVER_STEP);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_SCENARIO, STORY_SCENARIO);
        ATTRIBUTES.put(IStoryPegElementType.STORY_SCENARIO_TITLE, STORY_SCENARIO_TITLE);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_STEP, STORY_STEP);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_STEP_ARGUMENT, STORY_STEP_ARGUMENT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_STEP_LINE, STORY_STEP_LINE);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_STEP_PAR, STORY_STEP_PAR);
        ATTRIBUTES.put(IStoryPegElementType.STORY_STEP_POST_PARAMETER, STORY_STEP_POST_PARAMETER);
        ATTRIBUTES.put(IStoryPegElementType.STORY_STORY, STORY_STORY);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TABLE, STORY_TABLE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TABLE_CELL, STORY_TABLE_CELL);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_TABLE_CELL_EMPTY, STORY_TABLE_CELL_EMPTY);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_TABLE_ROW, STORY_TABLE_ROW);
        ATTRIBUTES.put(IStoryPegElementType.STORY_URI, STORY_URI);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_URI_IDENTIFIER, STORY_URI_IDENTIFIER);
//        ATTRIBUTES.put(IStoryPegElementType.STORY_URI_WORD, STORY_URI_WORD);
        //ATTRIBUTES.put(IStoryPegElementType.STORY_USER_INJECT, STORY_USER_INJECT);

        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_AFTER, STORY_TOKEN_AFTER);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_AND, STORY_TOKEN_AND);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_BEFORE, STORY_TOKEN_BEFORE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_EXAMPLES, STORY_TOKEN_EXAMPLES);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_GIVEN, STORY_TOKEN_GIVEN);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_GIVEN_STORIES, STORY_TOKEN_GIVEN_STORIES);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_LIFECYCLE, STORY_TOKEN_LIFECYCLE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_META, STORY_TOKEN_META);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_NARRATIVE, STORY_TOKEN_NARRATIVE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_SCENARIO, STORY_TOKEN_SCENARIO);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_THEN, STORY_TOKEN_THEN);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_WHEN, STORY_TOKEN_WHEN);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_PIPE, STORY_TOKEN_PIPE);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_INJECT, STORY_TOKEN_INJECT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_USER_INJECT, STORY_TOKEN_USERINJECT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_COMMENT, STORY_TOKEN_COMMENT);
        ATTRIBUTES.put(IStoryPegElementType.STORY_TOKEN_PATH, STORY_TOKEN_PATH);

    }

    static public TextAttributesKey getTextAttribute(IElementType elementType) {
        return ATTRIBUTES.get(elementType);
    }

    private static TextAttributesKey createKey(String externalName, TextAttributesKey textAttributesKey) {
        return createTextAttributesKey(externalName, textAttributesKey);
    }
}
