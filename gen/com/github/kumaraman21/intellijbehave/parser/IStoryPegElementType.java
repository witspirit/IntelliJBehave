// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.kumaraman21.intellijbehave.highlighter.StoryPegElementType;
import com.github.kumaraman21.intellijbehave.highlighter.StoryPegTokenType;
import com.github.kumaraman21.intellijbehave.psi.impl.*;

public interface IStoryPegElementType {

  IElementType STORY_ALNUM = new StoryPegElementType("STORY_ALNUM");
  IElementType STORY_DESCRIPTION = new StoryPegElementType("STORY_DESCRIPTION");
  IElementType STORY_EXAMPLES = new StoryPegElementType("STORY_EXAMPLES");
  IElementType STORY_GIVEN_STORIES = new StoryPegElementType("STORY_GIVEN_STORIES");
  IElementType STORY_INJECT = new StoryPegElementType("STORY_INJECT");
  IElementType STORY_IP_ADDRESS = new StoryPegElementType("STORY_IP_ADDRESS");
  IElementType STORY_LIFECYCLE = new StoryPegElementType("STORY_LIFECYCLE");
  IElementType STORY_LIFECYCLE_AFTER = new StoryPegElementType("STORY_LIFECYCLE_AFTER");
  IElementType STORY_LIFECYCLE_BEFORE = new StoryPegElementType("STORY_LIFECYCLE_BEFORE");
  IElementType STORY_LINE = new StoryPegElementType("STORY_LINE");
  IElementType STORY_LINE_EX = new StoryPegElementType("STORY_LINE_EX");
  IElementType STORY_META_ELEMENT = new StoryPegElementType("STORY_META_ELEMENT");
  IElementType STORY_META_KEY = new StoryPegElementType("STORY_META_KEY");
  IElementType STORY_META_STATEMENT = new StoryPegElementType("STORY_META_STATEMENT");
  IElementType STORY_META_VALUE = new StoryPegElementType("STORY_META_VALUE");
  IElementType STORY_MULTI_TEXT_LINE = new StoryPegElementType("STORY_MULTI_TEXT_LINE");
  IElementType STORY_NARRATIVE = new StoryPegElementType("STORY_NARRATIVE");
  IElementType STORY_NARRATIVE_TEXT = new StoryPegElementType("STORY_NARRATIVE_TEXT");
  IElementType STORY_RECOVER_STEP = new StoryPegElementType("STORY_RECOVER_STEP");
  IElementType STORY_SCENARIO = new StoryPegElementType("STORY_SCENARIO");
  IElementType STORY_SCENARIO_TITLE = new StoryPegElementType("STORY_SCENARIO_TITLE");
  IElementType STORY_STEP = new StoryPegElementType("STORY_STEP");
  IElementType STORY_STEP_AND = new StoryPegElementType("STORY_STEP_AND");
  IElementType STORY_STEP_ARGUMENT = new StoryPegElementType("STORY_STEP_ARGUMENT");
  IElementType STORY_STEP_COMMENT = new StoryPegElementType("STORY_STEP_COMMENT");
  IElementType STORY_STEP_GIVEN = new StoryPegElementType("STORY_STEP_GIVEN");
  IElementType STORY_STEP_LINE = new StoryPegElementType("STORY_STEP_LINE");
  IElementType STORY_STEP_POST_PARAMETER = new StoryPegElementType("STORY_STEP_POST_PARAMETER");
  IElementType STORY_STEP_THEN = new StoryPegElementType("STORY_STEP_THEN");
  IElementType STORY_STEP_WHEN = new StoryPegElementType("STORY_STEP_WHEN");
  IElementType STORY_STORY = new StoryPegElementType("STORY_STORY");
  IElementType STORY_STORY_PATH = new StoryPegElementType("STORY_STORY_PATH");
  IElementType STORY_STORY_PATHS = new StoryPegElementType("STORY_STORY_PATHS");
  IElementType STORY_TABLE = new StoryPegElementType("STORY_TABLE");
  IElementType STORY_TABLE_CELL = new StoryPegElementType("STORY_TABLE_CELL");
  IElementType STORY_TABLE_CELL_EMPTY = new StoryPegElementType("STORY_TABLE_CELL_EMPTY");
  IElementType STORY_TABLE_ROW = new StoryPegElementType("STORY_TABLE_ROW");
  IElementType STORY_URI = new StoryPegElementType("STORY_URI");
  IElementType STORY_USER_INJECT = new StoryPegElementType("STORY_USER_INJECT");
  IElementType STORY_WORD = new StoryPegElementType("STORY_WORD");

  IElementType STORY_PUNCT = new StoryPegTokenType("PUNCT");
  IElementType STORY_TOKEN_AFTER = new StoryPegTokenType("After:");
  IElementType STORY_TOKEN_AND = new StoryPegTokenType("And");
  IElementType STORY_TOKEN_AT = new StoryPegTokenType("@");
  IElementType STORY_TOKEN_BEFORE = new StoryPegTokenType("Before:");
  IElementType STORY_TOKEN_BRACKET_CLOSE = new StoryPegTokenType(">");
  IElementType STORY_TOKEN_BRACKET_OPEN = new StoryPegTokenType("<");
  IElementType STORY_TOKEN_COLON = new StoryPegTokenType("TOKEN_COLON");
  IElementType STORY_TOKEN_COMMA = new StoryPegTokenType(",");
  IElementType STORY_TOKEN_COMMENT = new StoryPegTokenType("!--");
  IElementType STORY_TOKEN_DBRACKET_CLOSE = new StoryPegTokenType(">>");
  IElementType STORY_TOKEN_DBRACKET_OPEN = new StoryPegTokenType("<<");
  IElementType STORY_TOKEN_EXAMPLES = new StoryPegTokenType("Examples:");
  IElementType STORY_TOKEN_GIVEN = new StoryPegTokenType("Given");
  IElementType STORY_TOKEN_GIVEN_STORIES = new StoryPegTokenType("GivenStories:");
  IElementType STORY_TOKEN_IP = new StoryPegTokenType("TOKEN_IP");
  IElementType STORY_TOKEN_LIFECYCLE = new StoryPegTokenType("Lifecycle:");
  IElementType STORY_TOKEN_META = new StoryPegTokenType("Meta:");
  IElementType STORY_TOKEN_NARRATIVE = new StoryPegTokenType("Narrative:");
  IElementType STORY_TOKEN_NEWLINE = new StoryPegTokenType("TOKEN_NEWLINE");
  IElementType STORY_TOKEN_PATH = new StoryPegTokenType("TOKEN_PATH");
  IElementType STORY_TOKEN_PIPE = new StoryPegTokenType("|");
  IElementType STORY_TOKEN_PUNCT = new StoryPegTokenType("TOKEN_PUNCT");
  IElementType STORY_TOKEN_SCENARIO = new StoryPegTokenType("TOKEN_SCENARIO");
  IElementType STORY_TOKEN_SPACE = new StoryPegTokenType("TOKEN_SPACE");
  IElementType STORY_TOKEN_THEN = new StoryPegTokenType("Then");
  IElementType STORY_TOKEN_WHEN = new StoryPegTokenType("When");
  IElementType STORY_TOKEN_WORD = new StoryPegTokenType("TOKEN_WORD");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == STORY_ALNUM) {
        return new StoryAlnumImpl(node);
      }
      else if (type == STORY_DESCRIPTION) {
        return new StoryDescriptionImpl(node);
      }
      else if (type == STORY_EXAMPLES) {
        return new StoryExamplesImpl(node);
      }
      else if (type == STORY_GIVEN_STORIES) {
        return new StoryGivenStoriesImpl(node);
      }
      else if (type == STORY_INJECT) {
        return new StoryInjectImpl(node);
      }
      else if (type == STORY_IP_ADDRESS) {
        return new StoryIpAddressImpl(node);
      }
      else if (type == STORY_LIFECYCLE) {
        return new StoryLifecycleImpl(node);
      }
      else if (type == STORY_LIFECYCLE_AFTER) {
        return new StoryLifecycleAfterImpl(node);
      }
      else if (type == STORY_LIFECYCLE_BEFORE) {
        return new StoryLifecycleBeforeImpl(node);
      }
      else if (type == STORY_LINE) {
        return new StoryLineImpl(node);
      }
      else if (type == STORY_LINE_EX) {
        return new StoryLineExImpl(node);
      }
      else if (type == STORY_META_ELEMENT) {
        return new StoryMetaElementImpl(node);
      }
      else if (type == STORY_META_KEY) {
        return new StoryMetaKeyImpl(node);
      }
      else if (type == STORY_META_STATEMENT) {
        return new StoryMetaStatementImpl(node);
      }
      else if (type == STORY_META_VALUE) {
        return new StoryMetaValueImpl(node);
      }
      else if (type == STORY_MULTI_TEXT_LINE) {
        return new StoryMultiTextLineImpl(node);
      }
      else if (type == STORY_NARRATIVE) {
        return new StoryNarrativeImpl(node);
      }
      else if (type == STORY_NARRATIVE_TEXT) {
        return new StoryNarrativeTextImpl(node);
      }
      else if (type == STORY_RECOVER_STEP) {
        return new StoryRecoverStepImpl(node);
      }
      else if (type == STORY_SCENARIO) {
        return new StoryScenarioImpl(node);
      }
      else if (type == STORY_SCENARIO_TITLE) {
        return new StoryScenarioTitleImpl(node);
      }
      else if (type == STORY_STEP) {
        return new StoryStepImpl(node);
      }
      else if (type == STORY_STEP_AND) {
        return new StoryStepAndImpl(node);
      }
      else if (type == STORY_STEP_ARGUMENT) {
        return new StoryStepArgumentImpl(node);
      }
      else if (type == STORY_STEP_COMMENT) {
        return new StoryStepCommentImpl(node);
      }
      else if (type == STORY_STEP_GIVEN) {
        return new StoryStepGivenImpl(node);
      }
      else if (type == STORY_STEP_LINE) {
        return new StoryStepLineImpl(node);
      }
      else if (type == STORY_STEP_POST_PARAMETER) {
        return new StoryStepPostParameterImpl(node);
      }
      else if (type == STORY_STEP_THEN) {
        return new StoryStepThenImpl(node);
      }
      else if (type == STORY_STEP_WHEN) {
        return new StoryStepWhenImpl(node);
      }
      else if (type == STORY_STORY) {
        return new StoryStoryImpl(node);
      }
      else if (type == STORY_STORY_PATH) {
        return new StoryStoryPathImpl(node);
      }
      else if (type == STORY_STORY_PATHS) {
        return new StoryStoryPathsImpl(node);
      }
      else if (type == STORY_TABLE) {
        return new StoryTableImpl(node);
      }
      else if (type == STORY_TABLE_CELL) {
        return new StoryTableCellImpl(node);
      }
      else if (type == STORY_TABLE_CELL_EMPTY) {
        return new StoryTableCellEmptyImpl(node);
      }
      else if (type == STORY_TABLE_ROW) {
        return new StoryTableRowImpl(node);
      }
      else if (type == STORY_URI) {
        return new StoryUriImpl(node);
      }
      else if (type == STORY_USER_INJECT) {
        return new StoryUserInjectImpl(node);
      }
      else if (type == STORY_WORD) {
        return new StoryWordImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
