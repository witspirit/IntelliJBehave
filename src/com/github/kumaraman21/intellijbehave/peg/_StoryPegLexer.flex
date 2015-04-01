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

TOKEN_NEWLINE=[ \t\f]*(\r|\n|\r\n|\Z)
TOKEN_SPACE=[ \t\f]+
TOKEN_COMMENT=\!--.*
TOKEN_USER_INJECT=<<[^ \t\n\x0B\f\r]+>>
TOKEN_INJECT=<[^ \t\n\x0B\f\r]+>
TOKEN_PATH=([a-zA-Z0-9]|_|@|\.|-)+("/"([a-zA-Z0-9]|_|@|\.|-)+)+
TOKEN_IP=[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+
TOKEN_WORD=([a-zA-Z0-9_-]|&|\!|\\|\"|#|\$|%|\(|'|\)|\*|\+|"/"|;|=|\?|\[|\]|\^|`|\{|\}|\~)+

%%
<YYINITIAL> {
  "Meta:"                  { return STORY_TOKEN_META; }
  "Narrative:"             { return STORY_TOKEN_NARRATIVE; }
  "Scenario:"              { return STORY_TOKEN_SCENARIO; }
  "GivenStories:"          { return STORY_TOKEN_GIVEN_STORIES; }
  "Given"                  { return STORY_TOKEN_GIVEN; }
  "When"                   { return STORY_TOKEN_WHEN; }
  "Then"                   { return STORY_TOKEN_THEN; }
  "And"                    { return STORY_TOKEN_AND; }
  "Examples:"              { return STORY_TOKEN_EXAMPLES; }
  "Lifecycle:"             { return STORY_TOKEN_LIFECYCLE; }
  "Before:"                { return STORY_TOKEN_BEFORE; }
  "After:"                 { return STORY_TOKEN_AFTER; }
  ":"                      { return STORY_TOKEN_COLON; }
  "@"                      { return STORY_TOKEN_AT; }
  "<<"                     { return STORY_TOKEN_DBRACKET_OPEN; }
  ">>"                     { return STORY_TOKEN_DBRACKET_CLOSE; }
  "<"                      { return STORY_TOKEN_BRACKET_OPEN; }
  ">"                      { return STORY_TOKEN_BRACKET_CLOSE; }
  "|"                      { return STORY_TOKEN_PIPE; }

  {TOKEN_NEWLINE}          { return STORY_TOKEN_NEWLINE; }
  {TOKEN_SPACE}            { return STORY_TOKEN_SPACE; }
  {TOKEN_COMMENT}          { return STORY_TOKEN_COMMENT; }
  {TOKEN_USER_INJECT}      { return STORY_TOKEN_USER_INJECT; }
  {TOKEN_INJECT}           { return STORY_TOKEN_INJECT; }
  {TOKEN_PATH}             { return STORY_TOKEN_PATH; }
  {TOKEN_IP}               { return STORY_TOKEN_IP; }
  {TOKEN_WORD}             { return STORY_TOKEN_WORD; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
