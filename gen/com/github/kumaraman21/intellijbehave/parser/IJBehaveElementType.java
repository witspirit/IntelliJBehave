// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.parser;

import com.github.kumaraman21.intellijbehave.highlighter.JBehaveElementType;
import com.github.kumaraman21.intellijbehave.highlighter.JBehaveTokenType;
import com.github.kumaraman21.intellijbehave.psi.impl.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public interface IJBehaveElementType {

    IElementType JB_DESCRIPTION = new JBehaveElementType("JB_DESCRIPTION");
    IElementType JB_EXAMPLES = new JBehaveElementType("JB_EXAMPLES");
    IElementType JB_GIVEN_STORIES = new JBehaveElementType("JB_GIVEN_STORIES");
    IElementType JB_IP_ADDRESS = new JBehaveElementType("JB_IP_ADDRESS");
    IElementType JB_LIFECYCLE = new JBehaveElementType("JB_LIFECYCLE");
    IElementType JB_LIFECYCLE_AFTER = new JBehaveElementType("JB_LIFECYCLE_AFTER");
    IElementType JB_LIFECYCLE_BEFORE = new JBehaveElementType("JB_LIFECYCLE_BEFORE");
    IElementType JB_META_ELEMENT = new JBehaveElementType("JB_META_ELEMENT");
    IElementType JB_META_KEY = new JBehaveElementType("JB_META_KEY");
    IElementType JB_META_STATEMENT = new JBehaveElementType("JB_META_STATEMENT");
    IElementType JB_META_VALUE = new JBehaveElementType("JB_META_VALUE");
    IElementType JB_NARRATIVE = new JBehaveElementType("JB_NARRATIVE");
    IElementType JB_NARRATIVE_TEXT = new JBehaveElementType("JB_NARRATIVE_TEXT");
    IElementType JB_SCENARIO = new JBehaveElementType("JB_SCENARIO");
    IElementType JB_SCENARIO_TITLE = new JBehaveElementType("JB_SCENARIO_TITLE");
    IElementType JB_STEP = new JBehaveElementType("JB_STEP");
    IElementType JB_STEP_ARGUMENT = new JBehaveElementType("JB_STEP_ARGUMENT");
    IElementType JB_STEP_LINE = new JBehaveElementType("JB_STEP_LINE");
    IElementType JB_STEP_PAR = new JBehaveElementType("JB_STEP_PAR");
    IElementType JB_STEP_POST_PARAMETER = new JBehaveElementType("JB_STEP_POST_PARAMETER");
    IElementType JB_STORY = new JBehaveElementType("JB_STORY");
    IElementType JB_STORY_PATH = new JBehaveElementType("JB_STORY_PATH");
    IElementType JB_STORY_PATHS = new JBehaveElementType("JB_STORY_PATHS");
    IElementType JB_TABLE = new JBehaveElementType("JB_TABLE");
    IElementType JB_TABLE_CELL = new JBehaveElementType("JB_TABLE_CELL");
    IElementType JB_TABLE_CELL_EMPTY = new JBehaveElementType("JB_TABLE_CELL_EMPTY");
    IElementType JB_TABLE_ROW = new JBehaveElementType("JB_TABLE_ROW");
    IElementType JB_URI = new JBehaveElementType("JB_URI");
    IElementType JB_URI_IDENTIFIER = new JBehaveElementType("JB_URI_IDENTIFIER");
    IElementType JB_URI_WORD = new JBehaveElementType("JB_URI_WORD");

    IElementType JB_TOKEN_AFTER = new JBehaveTokenType("After:");
    IElementType JB_TOKEN_AND = new JBehaveTokenType("And");
    IElementType JB_TOKEN_AT = new JBehaveTokenType("@");
    IElementType JB_TOKEN_BEFORE = new JBehaveTokenType("Before:");
    IElementType JB_TOKEN_BRACKET_CLOSE = new JBehaveTokenType(">");
    IElementType JB_TOKEN_BRACKET_OPEN = new JBehaveTokenType("<");
    IElementType JB_TOKEN_COLON = new JBehaveTokenType(":");
    IElementType JB_TOKEN_COMMENT = new JBehaveTokenType("TOKEN_COMMENT");
    IElementType JB_TOKEN_DBRACKET_CLOSE = new JBehaveTokenType(">>");
    IElementType JB_TOKEN_DBRACKET_OPEN = new JBehaveTokenType("<<");
    IElementType JB_TOKEN_EXAMPLES = new JBehaveTokenType("Examples:");
    IElementType JB_TOKEN_GIVEN = new JBehaveTokenType("Given");
    IElementType JB_TOKEN_GIVEN_STORIES = new JBehaveTokenType("GivenStories:");
    IElementType JB_TOKEN_INJECT = new JBehaveTokenType("TOKEN_INJECT");
    IElementType JB_TOKEN_IP = new JBehaveTokenType("TOKEN_IP");
    IElementType JB_TOKEN_LIFECYCLE = new JBehaveTokenType("Lifecycle:");
    IElementType JB_TOKEN_META = new JBehaveTokenType("Meta:");
    IElementType JB_TOKEN_NARRATIVE = new JBehaveTokenType("Narrative:");
    IElementType JB_TOKEN_NEWLINE = new JBehaveTokenType("TOKEN_NEWLINE");
    IElementType JB_TOKEN_PATH = new JBehaveTokenType("TOKEN_PATH");
    IElementType JB_TOKEN_PIPE = new JBehaveTokenType("|");
    IElementType JB_TOKEN_SCENARIO = new JBehaveTokenType("Scenario:");
    IElementType JB_TOKEN_SPACE = new JBehaveTokenType("TOKEN_SPACE");
    IElementType JB_TOKEN_THEN = new JBehaveTokenType("Then");
    IElementType JB_TOKEN_USER_INJECT = new JBehaveTokenType("TOKEN_USER_INJECT");
    IElementType JB_TOKEN_WHEN = new JBehaveTokenType("When");
    IElementType JB_TOKEN_WORD = new JBehaveTokenType("TOKEN_WORD");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == JB_DESCRIPTION) {
                return new JBehaveDescriptionImpl(node);
            } else if (type == JB_EXAMPLES) {
                return new JBehaveExamplesImpl(node);
            } else if (type == JB_GIVEN_STORIES) {
                return new JBehaveGivenStoriesImpl(node);
            } else if (type == JB_IP_ADDRESS) {
                return new JBehaveIpAddressImpl(node);
            } else if (type == JB_LIFECYCLE) {
                return new JBehaveLifecycleImpl(node);
            } else if (type == JB_LIFECYCLE_AFTER) {
                return new JBehaveLifecycleAfterImpl(node);
            } else if (type == JB_LIFECYCLE_BEFORE) {
                return new JBehaveLifecycleBeforeImpl(node);
            } else if (type == JB_META_ELEMENT) {
                return new JBehaveMetaElementImpl(node);
            } else if (type == JB_META_KEY) {
                return new JBehaveMetaKeyImpl(node);
            } else if (type == JB_META_STATEMENT) {
                return new JBehaveMetaStatementImpl(node);
            } else if (type == JB_META_VALUE) {
                return new JBehaveMetaValueImpl(node);
            } else if (type == JB_NARRATIVE) {
                return new JBehaveNarrativeImpl(node);
            } else if (type == JB_NARRATIVE_TEXT) {
                return new JBehaveNarrativeTextImpl(node);
            } else if (type == JB_SCENARIO) {
                return new JBehaveScenarioImpl(node);
            } else if (type == JB_SCENARIO_TITLE) {
                return new JBehaveScenarioTitleImpl(node);
            } else if (type == JB_STEP) {
                return new JBehaveStepImpl(node);
            } else if (type == JB_STEP_ARGUMENT) {
                return new JBehaveStepArgumentImpl(node);
            } else if (type == JB_STEP_LINE) {
                return new JBehaveStepLineImpl(node);
            } else if (type == JB_STEP_PAR) {
                return new JBehaveStepParImpl(node);
            } else if (type == JB_STEP_POST_PARAMETER) {
                return new JBehaveStepPostParameterImpl(node);
            } else if (type == JB_STORY) {
                return new JBehaveStoryImpl(node);
            } else if (type == JB_STORY_PATH) {
                return new JBehaveStoryPathImpl(node);
            } else if (type == JB_STORY_PATHS) {
                return new JBehaveStoryPathsImpl(node);
            } else if (type == JB_TABLE) {
                return new JBehaveTableImpl(node);
            } else if (type == JB_TABLE_CELL) {
                return new JBehaveTableCellImpl(node);
            } else if (type == JB_TABLE_CELL_EMPTY) {
                return new JBehaveTableCellEmptyImpl(node);
            } else if (type == JB_TABLE_ROW) {
                return new JBehaveTableRowImpl(node);
            } else if (type == JB_URI) {
                return new JBehaveUriImpl(node);
            } else if (type == JB_URI_IDENTIFIER) {
                return new JBehaveUriIdentifierImpl(node);
            } else if (type == JB_URI_WORD) {
                return new JBehaveUriWordImpl(node);
            }
            throw new AssertionError("Unknown element type: " + type);
    }
    }
}
