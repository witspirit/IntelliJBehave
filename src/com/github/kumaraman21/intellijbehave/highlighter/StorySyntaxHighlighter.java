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

import com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType;
import com.github.kumaraman21.intellijbehave.parser._JBehaveLexer;
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

import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class StorySyntaxHighlighter extends SyntaxHighlighterBase {

    @NonNls
    public static final String JB_DESCRIPTION_ID = "JBEHAVE.JB_DESCRIPTION";
    public static final String JB_EXAMPLES_ID = "JBEHAVE.JB_EXAMPLES";
    //public static final String JB_GIVEN_STORIES_ID = "JBEHAVE.JB_GIVEN_STORIES";
    public static final String JB_INJECT_ID = "JBEHAVE.JB_INJECT";
    //public static final String JB_INJECT_IDENTIFIER_ID = "JBEHAVE.JB_INJECT_IDENTIFIER";
    //public static final String JB_INJECT_SEPARATOR_ID = "JBEHAVE.JB_INJECT_SEPARATOR";
    public static final String JB_IP_ADDRESS_ID = "JBEHAVE.JB_IP_ADDRESS";
    //public static final String JB_LIFECYCLE_ID = "JBEHAVE.JB_LIFECYCLE";
    public static final String JB_LIFECYCLE_AFTER_ID = "JBEHAVE.JB_LIFECYCLE_AFTER";
    public static final String JB_LIFECYCLE_BEFORE_ID = "JBEHAVE.JB_LIFECYCLE_BEFORE";
    //public static final String JB_META_ELEMENT_ID = "JBEHAVE.JB_META_ELEMENT";
    public static final String JB_META_KEY_ID = "JBEHAVE.JB_META_KEY";
    //public static final String JB_META_STATEMENT_ID = "JBEHAVE.JB_META_STATEMENT";
    public static final String JB_META_VALUE_ID = "JBEHAVE.JB_META_VALUE";
    //public static final String JB_MULTI_TEXT_LINE_ID = "JBEHAVE.JB_MULTI_TEXT";
    //public static final String JB_NARRATIVE_ID = "JBEHAVE.JB_NARRATIVE";
    public static final String JB_NARRATIVE_TEXT_ID = "JBEHAVE.JB_NARRATIVE_TEXT";
    //public static final String JB_RECOVER_STEP_ID = "JBEHAVE.JB_RECOVER_STEP";
    //public static final String JB_SCENARIO_ID = "JBEHAVE.JB_SCENARIO";
    public static final String JB_SCENARIO_TITLE_ID = "JBEHAVE.JB_SCENARIO_TITLE";
    //public static final String JB_STEP_ID = "JBEHAVE.JB_STEP";
    //public static final String JB_STEP_ARGUMENT_ID = "JBEHAVE.JB_STEP_ARGUMENT";
    //public static final String JB_STEP_COMMENT_ID = "JBEHAVE.JB_STEP_COMMENT";
    public static final String JB_STEP_LINE_ID = "JBEHAVE.JB_STEP_LINE";
    //public static final String JB_STEP_PAR_ID = "JBEHAVE.JB_STEP_PAR";
    public static final String JB_STEP_POST_PARAMETER_ID = "JBEHAVE.JB_STEP_POST";
    public static final String JB_JB_ID = "JBEHAVE.JB_JBEHAVE";
    //public static final String JB_JB_PATH_ID = "JBEHAVE.JB_JB_PATH";
    //public static final String JB_JB_PATHS_ID = "JBEHAVE.JB_JB_PATHS";
    public static final String JB_TABLE_ID = "JBEHAVE.JB_TABLE";
    //public static final String JB_TABLE_CELL_EMPTY_ID = "JBEHAVE.JB_TABLE_CELL_EMPTY";
    public static final String JB_TABLE_CELL_ID = "JBEHAVE.JB_TABLE_CELL";
    //public static final String JB_TABLE_ROW_ID = "JBEHAVE.JB_TABLE_ROW";
    public static final String JB_URI_ID = "JBEHAVE.JB_URI";
    //public static final String JB_URI_IDENTIFIER_ID = "JBEHAVE.JB_URI_IDENTIFIER";
    //public static final String JB_URI_WORD_ID = "JBEHAVE.JB_URI_WORD";
    public static final String JB_USER_INJECT_ID = "JBEHAVE.JB_USER_INJECT";
    public static final String JB_ERROR_NO_DEF_FOUND_ID = "JBEHAVE.ERROR_NO_DEF_FOUND";
    public static final String JB_ERROR_FILE_NOT_FOUND_ID = "JBEHAVE.FILE_NOT_FOUND_ID";
    public static final String JB_TOKEN_AFTER_ID = "JBEHAVE.JB_TOKEN_AFTER";
    public static final String JB_TOKEN_AND_ID = "JBEHAVE.JB_TOKEN_AND";
    public static final String JB_TOKEN_BEFORE_ID = "JBEHAVE.JB_TOKEN_BEFORE";
    public static final String JB_TOKEN_EXAMPLES_ID = "JBEHAVE.JB_TOKEN_EXAMPLES";
    public static final String JB_TOKEN_GIVEN_STORIES_ID = "JBEHAVE.JB_TOKEN_GIVEN_STORIES";
    public static final String JB_TOKEN_GIVEN_ID = "JBEHAVE.JB_TOKEN_GIVEN";
    public static final String JB_TOKEN_LIFECYCLE_ID = "JBEHAVE.JB_TOKEN_LIFECYCLE";
    public static final String JB_TOKEN_META_ID = "JBEHAVE.JB_TOKEN_META";
    public static final String JB_TOKEN_NARRATIVE_ID = "JBEHAVE.JB_TOKEN_NARRATIVE";
    public static final String JB_TOKEN_SCENARIO_ID = "JBEHAVE.JB_TOKEN_SCENARIO";
    public static final String JB_TOKEN_THEN_ID = "JBEHAVE.JB_TOKEN_THEN";
    public static final String JB_TOKEN_WHEN_ID = "JBEHAVE.JB_TOKEN_WHEN";
    public static final String JB_TOKEN_PIPE_ID = "JBEHAVE.JB_TOKEN_PIPE";
    public static final String JB_TOKEN_INJECT_ID = "JBEHAVE.JB_TOKEN_INJECT";
    public static final String JB_TOKEN_USER_INJECT_ID = "JBEHAVE.JB_TOKEN_USER_INJECT";
    public static final String JB_TOKEN_COMMENT_ID = "JBEHAVE.JB_COMMENT_ID";
    public static final String STEP_PARAMETER_ID = "JBEHAVE.STEP_PARAMETER";
    public static final String JB_TOKEN_PATH_ID = "JBEHAVE.JB_TOKEN_PATH";

    // Registering TextAttributes
    static {
        createKey(JB_DESCRIPTION_ID, HighlighterColors.TEXT);
        createKey(JB_IP_ADDRESS_ID, CodeInsightColors.HYPERLINK_ATTRIBUTES);
        createKey(JB_LIFECYCLE_AFTER_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_LIFECYCLE_BEFORE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        //   createKey(JB_META_ELEMENT_ID, HighlighterColors.TEXT);
        createKey(JB_META_KEY_ID, DefaultLanguageHighlighterColors.IDENTIFIER);
        //    createKey(JB_META_STATEMENT_ID, HighlighterColors.TEXT);
        createKey(JB_META_VALUE_ID, HighlighterColors.TEXT);
        //     createKey(JB_MULTI_TEXT_LINE_ID, HighlighterColors.TEXT);
        createKey(JB_NARRATIVE_TEXT_ID, HighlighterColors.TEXT);
        createKey(JB_SCENARIO_TITLE_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        //    createKey(JB_STEP_COMMENT_ID, DefaultLanguageHighlighterColors.LINE_COMMENT);
        createKey(JB_STEP_LINE_ID, HighlighterColors.TEXT);
        //
        createKey(JB_ERROR_NO_DEF_FOUND_ID, CodeInsightColors.ERRORS_ATTRIBUTES);
        createKey(JB_ERROR_FILE_NOT_FOUND_ID, CodeInsightColors.ERRORS_ATTRIBUTES);
        //createKey(JB_JB_PATH_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(JB_TABLE_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(JB_TABLE_CELL_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(JB_URI_ID, CodeInsightColors.HYPERLINK_ATTRIBUTES);
        createKey(STEP_PARAMETER_ID, DefaultLanguageHighlighterColors.STRING);


        createKey(JB_TOKEN_AFTER_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_AND_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_BEFORE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_EXAMPLES_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_GIVEN_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_GIVEN_STORIES_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_LIFECYCLE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_META_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_NARRATIVE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_SCENARIO_ID, CodeInsightColors.STATIC_FIELD_ATTRIBUTES);
        createKey(JB_TOKEN_THEN_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_WHEN_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_PIPE_ID, DefaultLanguageHighlighterColors.KEYWORD);
        createKey(JB_TOKEN_INJECT_ID, DefaultLanguageHighlighterColors.LABEL);
        createKey(JB_TOKEN_USER_INJECT_ID, DefaultLanguageHighlighterColors.LABEL);
        createKey(JB_TOKEN_PATH_ID, DefaultLanguageHighlighterColors.STRING);
        createKey(JB_TOKEN_COMMENT_ID, DefaultLanguageHighlighterColors.LINE_COMMENT);
    }

    private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new THashMap<IElementType, TextAttributesKey>();
    public static TextAttributesKey JB_DESCRIPTION = createTextAttributesKey(JB_DESCRIPTION_ID);
    public static TextAttributesKey JB_EXAMPLES = createTextAttributesKey(JB_EXAMPLES_ID);
    //public static TextAttributesKey JB_GIVEN_STORIES = createTextAttributesKey(JB_GIVEN_STORIES_ID);
    public static TextAttributesKey JB_IP_ADDRESS = createTextAttributesKey(JB_IP_ADDRESS_ID);
    // public static TextAttributesKey JB_LIFECYCLE = createTextAttributesKey(JB_LIFECYCLE_ID);
    public static TextAttributesKey JB_LIFECYCLE_AFTER = createTextAttributesKey(JB_LIFECYCLE_AFTER_ID);
    public static TextAttributesKey JB_LIFECYCLE_BEFORE = createTextAttributesKey(JB_LIFECYCLE_BEFORE_ID);
    // public static TextAttributesKey JB_META_ELEMENT = createTextAttributesKey(JB_META_ELEMENT_ID);
    public static TextAttributesKey JB_META_KEY = createTextAttributesKey(JB_META_KEY_ID);
    // public static TextAttributesKey JB_META_STATEMENT = createTextAttributesKey(JB_META_STATEMENT_ID);
    public static TextAttributesKey JB_META_VALUE = createTextAttributesKey(JB_META_VALUE_ID);
    //public static TextAttributesKey JB_MULTI_TEXT_LINE = createTextAttributesKey(JB_MULTI_TEXT_LINE_ID);
    // public static TextAttributesKey JB_NARRATIVE = createTextAttributesKey(JB_NARRATIVE_ID);
    public static TextAttributesKey JB_NARRATIVE_TEXT = createTextAttributesKey(JB_NARRATIVE_TEXT_ID);
    //  public static TextAttributesKey JB_RECOVER_STEP = createTextAttributesKey(JB_RECOVER_STEP_ID);
    //    public static TextAttributesKey JB_SCENARIO = createTextAttributesKey(JB_SCENARIO_ID);
    public static TextAttributesKey JB_SCENARIO_TITLE = createTextAttributesKey(JB_SCENARIO_TITLE_ID);
    //    public static TextAttributesKey JB_STEP = createTextAttributesKey(JB_STEP_ID);
//    public static TextAttributesKey JB_STEP_ARGUMENT = createTextAttributesKey(JB_STEP_ARGUMENT_ID);
    public static TextAttributesKey JB_STEP_LINE = createTextAttributesKey(JB_STEP_LINE_ID);
    //    public static TextAttributesKey JB_STEP_PAR = createTextAttributesKey(JB_STEP_PAR_ID);
    public static TextAttributesKey JB_STEP_POST_PARAMETER = createTextAttributesKey(JB_STEP_POST_PARAMETER_ID);
    public static TextAttributesKey JB_JBEHAVE = createTextAttributesKey(JB_JB_ID);
    //public static TextAttributesKey JB_TOKEN_PATH = createTextAttributesKey(JB_JB_PATH_ID);
//    public static TextAttributesKey JB_JB_PATHS = createTextAttributesKey(JB_JB_PATHS_ID);
    public static TextAttributesKey JB_TABLE = createTextAttributesKey(JB_TABLE_ID);
    public static TextAttributesKey JB_TABLE_CELL = createTextAttributesKey(JB_TABLE_CELL_ID);
    //    public static TextAttributesKey JB_TABLE_CELL_EMPTY = createTextAttributesKey(JB_TABLE_CELL_EMPTY_ID);
//    public static TextAttributesKey JB_TABLE_ROW = createTextAttributesKey(JB_TABLE_ROW_ID);
    public static TextAttributesKey JB_URI = createTextAttributesKey(JB_URI_ID);
    //    public static TextAttributesKey JB_URI_IDENTIFIER = createTextAttributesKey(JB_URI_IDENTIFIER_ID);
//    public static TextAttributesKey JB_URI_WORD = createTextAttributesKey(JB_URI_WORD_ID);
    public static TextAttributesKey STEP_PARAMETER = createTextAttributesKey(STEP_PARAMETER_ID);
    public static TextAttributesKey JB_TOKEN_AFTER = createTextAttributesKey(JB_TOKEN_AFTER_ID);
    public static TextAttributesKey JB_TOKEN_AND = createTextAttributesKey(JB_TOKEN_AND_ID);
    public static TextAttributesKey JB_TOKEN_BEFORE = createTextAttributesKey(JB_TOKEN_BEFORE_ID);
    public static TextAttributesKey JB_TOKEN_EXAMPLES = createTextAttributesKey(JB_TOKEN_EXAMPLES_ID);
    public static TextAttributesKey JB_TOKEN_GIVEN = createTextAttributesKey(JB_TOKEN_GIVEN_ID);
    public static TextAttributesKey JB_TOKEN_GIVEN_STORIES = createTextAttributesKey(JB_TOKEN_GIVEN_STORIES_ID);
    public static TextAttributesKey JB_TOKEN_LIFECYCLE = createTextAttributesKey(JB_TOKEN_LIFECYCLE_ID);
    public static TextAttributesKey JB_TOKEN_META = createTextAttributesKey(JB_TOKEN_META_ID);
    public static TextAttributesKey JB_TOKEN_NARRATIVE = createTextAttributesKey(JB_TOKEN_NARRATIVE_ID);
    public static TextAttributesKey JB_TOKEN_SCENARIO = createTextAttributesKey(JB_TOKEN_SCENARIO_ID);
    public static TextAttributesKey JB_TOKEN_THEN = createTextAttributesKey(JB_TOKEN_THEN_ID);
    public static TextAttributesKey JB_TOKEN_WHEN = createTextAttributesKey(JB_TOKEN_WHEN_ID);
    public static TextAttributesKey JB_TOKEN_PIPE = createTextAttributesKey(JB_TOKEN_PIPE_ID);
    public static TextAttributesKey JB_TOKEN_INJECT = createTextAttributesKey(JB_TOKEN_INJECT_ID);
    public static TextAttributesKey JB_TOKEN_USERINJECT = createTextAttributesKey(JB_TOKEN_USER_INJECT_ID);
    public static TextAttributesKey JB_TOKEN_COMMENT = createTextAttributesKey(JB_TOKEN_COMMENT_ID);
    public static TextAttributesKey JB_TOKEN_PATH = createTextAttributesKey(JB_TOKEN_PATH_ID);
    public static TextAttributesKey JB_ERROR_NO_DEF_FOUND = createTextAttributesKey(JB_ERROR_NO_DEF_FOUND_ID);
    public static TextAttributesKey JB_ERROR_FILE_NOT_FOUND = createTextAttributesKey(JB_ERROR_FILE_NOT_FOUND_ID);

    static {
        ATTRIBUTES.put(IJBehaveElementType.JB_DESCRIPTION, JB_DESCRIPTION);
        ATTRIBUTES.put(IJBehaveElementType.JB_EXAMPLES, JB_EXAMPLES);
        //ATTRIBUTES.put(IJBehaveElementType.JB_GIVEN_STORIES, JB_GIVEN_STORIES);
        //ATTRIBUTES.put(IJBehaveElementType.JB_INJECT, JB_INJECT);
//        ATTRIBUTES.put(IJBehaveElementType.JB_INJECT_IDENTIFIER, JB_INJECT_IDENTIFIER);
//        ATTRIBUTES.put(IJBehaveElementType.JB_INJECT_SEPARATOR, JB_INJECT_SEPARATOR);
        ATTRIBUTES.put(IJBehaveElementType.JB_IP_ADDRESS, JB_IP_ADDRESS);
        //ATTRIBUTES.put(IJBehaveElementType.JB_LIFECYCLE, JB_LIFECYCLE);
        ATTRIBUTES.put(IJBehaveElementType.JB_LIFECYCLE_AFTER, JB_LIFECYCLE_AFTER);
        ATTRIBUTES.put(IJBehaveElementType.JB_LIFECYCLE_BEFORE, JB_LIFECYCLE_BEFORE);
        //ATTRIBUTES.put(IJBehaveElementType.JB_META_ELEMENT, JB_META_ELEMENT);
        ATTRIBUTES.put(IJBehaveElementType.JB_META_KEY, JB_META_KEY);
        //ATTRIBUTES.put(IJBehaveElementType.JB_META_STATEMENT, JB_META_STATEMENT);
        ATTRIBUTES.put(IJBehaveElementType.JB_META_VALUE, JB_META_VALUE);
//        ATTRIBUTES.put(IJBehaveElementType.JB_MULTI_TEXT_LINE, JB_MULTI_TEXT_LINE);
        //ATTRIBUTES.put(IJBehaveElementType.JB_NARRATIVE, JB_NARRATIVE);
        ATTRIBUTES.put(IJBehaveElementType.JB_NARRATIVE_TEXT, JB_NARRATIVE_TEXT);
        //ATTRIBUTES.put(IJBehaveElementType.JB_RECOVER_STEP, JB_RECOVER_STEP);
//        ATTRIBUTES.put(IJBehaveElementType.JB_SCENARIO, JB_SCENARIO);
        ATTRIBUTES.put(IJBehaveElementType.JB_SCENARIO_TITLE, JB_SCENARIO_TITLE);
//        ATTRIBUTES.put(IJBehaveElementType.JB_STEP, JB_STEP);
//        ATTRIBUTES.put(IJBehaveElementType.JB_STEP_ARGUMENT, JB_STEP_ARGUMENT);
        ATTRIBUTES.put(IJBehaveElementType.JB_STEP_LINE, JB_STEP_LINE);
//        ATTRIBUTES.put(IJBehaveElementType.JB_STEP_PAR, JB_STEP_PAR);
        ATTRIBUTES.put(IJBehaveElementType.JB_STEP_POST_PARAMETER, JB_STEP_POST_PARAMETER);
        //ATTRIBUTES.put(IJBehaveElementType.JB_JBEHAVE, JB_JBEHAVE);
        ATTRIBUTES.put(IJBehaveElementType.JB_TABLE, JB_TABLE);
        ATTRIBUTES.put(IJBehaveElementType.JB_TABLE_CELL, JB_TABLE_CELL);
//        ATTRIBUTES.put(IJBehaveElementType.JB_TABLE_CELL_EMPTY, JB_TABLE_CELL_EMPTY);
//        ATTRIBUTES.put(IJBehaveElementType.JB_TABLE_ROW, JB_TABLE_ROW);
        ATTRIBUTES.put(IJBehaveElementType.JB_URI, JB_URI);
//        ATTRIBUTES.put(IJBehaveElementType.JB_URI_IDENTIFIER, JB_URI_IDENTIFIER);
//        ATTRIBUTES.put(IJBehaveElementType.JB_URI_WORD, JB_URI_WORD);
        //ATTRIBUTES.put(IJBehaveElementType.JB_USER_INJECT, JB_USER_INJECT);

        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_AFTER, JB_TOKEN_AFTER);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_AND, JB_TOKEN_AND);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_BEFORE, JB_TOKEN_BEFORE);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_EXAMPLES, JB_TOKEN_EXAMPLES);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_GIVEN, JB_TOKEN_GIVEN);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_GIVEN_STORIES, JB_TOKEN_GIVEN_STORIES);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_LIFECYCLE, JB_TOKEN_LIFECYCLE);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_META, JB_TOKEN_META);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_NARRATIVE, JB_TOKEN_NARRATIVE);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_SCENARIO, JB_TOKEN_SCENARIO);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_THEN, JB_TOKEN_THEN);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_WHEN, JB_TOKEN_WHEN);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_PIPE, JB_TOKEN_PIPE);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_INJECT, JB_TOKEN_INJECT);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_USER_INJECT, JB_TOKEN_USERINJECT);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_COMMENT, JB_TOKEN_COMMENT);
        ATTRIBUTES.put(IJBehaveElementType.JB_TOKEN_PATH, JB_TOKEN_PATH);
    }

    static public TextAttributesKey getTextAttribute(IElementType elementType) {
        return ATTRIBUTES.get(elementType);
    }

    private static TextAttributesKey createKey(String externalName, TextAttributesKey textAttributesKey) {
        return createTextAttributesKey(externalName, textAttributesKey);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new _JBehaveLexer(null));
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(ATTRIBUTES.get(tokenType));
    }
}
