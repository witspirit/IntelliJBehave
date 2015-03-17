package com.github.kumaraman21.intellijbehave.peg;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.*;

%%

%{
  public _StoryPegLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _StoryPegLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

TOKEN_PATH=[a-zA-Z_0-9._@-]+("/"[a-zA-Z_0-9._@-]+)+
TOKEN_IP=[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+
TOKEN_PUNCT=[!\"#$%&'()*+,-./;=?\[\\\]\^_`{}~]
TOKEN_WORD=[a-zA-Z_0-9]+
TOKEN_NEWLINE=(\r|\n|\r\n)+
TOKEN_SPACE=[ \t\f]

%%
<YYINITIAL> {

  "Meta:"              { return STORY_TOKEN_META; }
  "Narrative:"         { return STORY_TOKEN_NARRATIVE; }
  "Scenario:"          { return STORY_TOKEN_SCENARIO; }
  "GivenStories:"      { return STORY_TOKEN_GIVEN_STORIES; }
  "Given"              { return STORY_TOKEN_GIVEN; }
  "When"               { return STORY_TOKEN_WHEN; }
  "Then"               { return STORY_TOKEN_THEN; }
  "And"                { return STORY_TOKEN_AND; }
  "Examples:"          { return STORY_TOKEN_EXAMPLES; }
  "!--"                { return STORY_TOKEN_COMMENT; }
  ":"                  { return STORY_TOKEN_COLON; }
  "@"                  { return STORY_TOKEN_AT; }
  ","                  { return STORY_TOKEN_COMMA; }
  "<<"                 { return STORY_TOKEN_DBRACKET_OPEN; }
  ">>"                 { return STORY_TOKEN_DBRACKET_CLOSE; }
  "<"                  { return STORY_TOKEN_BRACKET_OPEN; }
  ">"                  { return STORY_TOKEN_BRACKET_CLOSE; }
  "Lifecycle:"         { return STORY_TOKEN_LIFECYCLE; }
  "Before:"            { return STORY_TOKEN_BEFORE; }
  "After:"             { return STORY_TOKEN_AFTER; }
  "|"                  { return STORY_TOKEN_PIPE; }

  {TOKEN_PATH}         { return STORY_TOKEN_PATH; }
  {TOKEN_IP}           { return STORY_TOKEN_IP; }
  {TOKEN_PUNCT}        { return STORY_TOKEN_PUNCT; }
  {TOKEN_WORD}         { return STORY_TOKEN_WORD; }
  {TOKEN_NEWLINE}      { return STORY_TOKEN_NEWLINE; }
  {TOKEN_SPACE}        { return STORY_TOKEN_SPACE; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
