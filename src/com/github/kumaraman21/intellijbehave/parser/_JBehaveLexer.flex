package com.github.kumaraman21.intellijbehave.parser;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;

%%

%{
  public _JBehaveLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _JBehaveLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

TOKEN_NEWLINE=[ \t\f]*(\r|\n|\r\n|\Z)
TOKEN_SPACE=[ \t\f]+
TOKEN_COMMENT=\!--.*
TOKEN_USER_INJECT=<<.+>>
TOKEN_INJECT=<.+>
TOKEN_PIPE=\|
TOKEN_PATH=([A-Z]:|<.+>|<<.+>>)?([a-zA-Z0-9]|<.+>|<<.+>>|("/"|\\))([a-zA-Z0-9]|<.+>|<<.+>>|_|@|\.|-)+(("/"|\\)([a-zA-Z0-9]|<.+>|<<.+>>|_|@|\.|-|\ )+)+([a-zA-Z0-9]|_|@|\.|-|"/")
TOKEN_IP=[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+
TOKEN_MKEY=@[^ \t\n\x0B\f\r]+
TOKEN_WORD=[^ \t\n\x0B\f\r\|,|@]+

%%
<YYINITIAL> {
  "Meta:"                  { return JB_TOKEN_META; }
  "Narrative:"             { return JB_TOKEN_NARRATIVE; }
  "Scenario:"              { return JB_TOKEN_SCENARIO; }
  "GivenStories:"          { return JB_TOKEN_GIVEN_STORIES; }
  "Given"                  { return JB_TOKEN_GIVEN; }
  "When"                   { return JB_TOKEN_WHEN; }
  "Then"                   { return JB_TOKEN_THEN; }
  "And"                    { return JB_TOKEN_AND; }
  "Examples:"              { return JB_TOKEN_EXAMPLES; }
  "Lifecycle:"             { return JB_TOKEN_LIFECYCLE; }
  "Before:"                { return JB_TOKEN_BEFORE; }
  "After:"                 { return JB_TOKEN_AFTER; }
  ":"                      { return JB_TOKEN_COLON; }
  ","                      { return JB_TOKEN_COMMA; }
  "<<"                     { return JB_TOKEN_DBRACKET_OPEN; }
  ">>"                     { return JB_TOKEN_DBRACKET_CLOSE; }
  "<"                      { return JB_TOKEN_BRACKET_OPEN; }
  ">"                      { return JB_TOKEN_BRACKET_CLOSE; }
  "TOKEN_AT"               { return JB_TOKEN_AT; }

  {TOKEN_NEWLINE}          { return JB_TOKEN_NEWLINE; }
  {TOKEN_SPACE}            { return JB_TOKEN_SPACE; }
  {TOKEN_COMMENT}          { return JB_TOKEN_COMMENT; }
  {TOKEN_USER_INJECT}      { return JB_TOKEN_USER_INJECT; }
  {TOKEN_INJECT}           { return JB_TOKEN_INJECT; }
  {TOKEN_PIPE}             { return JB_TOKEN_PIPE; }
  {TOKEN_PATH}             { return JB_TOKEN_PATH; }
  {TOKEN_IP}               { return JB_TOKEN_IP; }
  {TOKEN_MKEY}             { return JB_TOKEN_MKEY; }
  {TOKEN_WORD}             { return JB_TOKEN_WORD; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
