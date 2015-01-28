package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Stack;

%%

%{
    private Stack<Integer> yystates = new Stack<Integer> () {{ push(YYINITIAL); }};
    private int currentStepStart = 0;
    public boolean trace = false;

    public void yystatePush(int yystate) {
        if(trace) System.out.println(">>>> PUSH: " + LexicalState.fromLexer(yystate) + " [" + reverseAndMap(yystates) + "]");
        yybegin(yystate);
        yystates.push(yystate);
    }

    private String reverseAndMap(Stack<Integer> yystates) {
        StringBuilder builder = new StringBuilder();
        for(int i=yystates.size()-1; i>=0; i--) {
            if(builder.length()>0)
                builder.append(", ");
            builder.append(LexicalState.fromLexer(yystates.get(i)));
        }
        return builder.toString();
    }

    public void yystatePopNPush(int yystate) {
        yystatePopNPush(1, yystate);
    }

    public void yystatePopNPush(int nb, int yystate) {
        if(trace) System.out.println(">>>> POP'n PUSH : #" + nb + ", " + LexicalState.fromLexer(yystate) + " [" + reverseAndMap(yystates) + "]");
        for (int i = 0; i < nb; i++) {
            yystatePop();
        }
        yystatePush(yystate);
    }

    public int yystatePop() {
        int popped = yystates.pop();
        if(trace) System.out.println(">>>> POP : " + LexicalState.fromLexer(popped) + " [" + reverseAndMap(yystates) + "]");
        if(!yystates.isEmpty()) {
            yybegin(yystates.peek());
        }// otherwise hopes a push will follow right after
        return popped;
    }

  public final int lastIndexOfCrLf(final CharSequence source) {
        final int length = source.length();
        boolean foundRfOrRn = false;

        for (int i = length - 1; i >= 0; i--) {
            final char c = source.charAt(i);
            if (c == '\r' || c == '\n') {
                foundRfOrRn = true;
            } else {
                if (foundRfOrRn) {
                    return i + 1;
                }
            }
        }

        if (foundRfOrRn) {
            return 0;
        } else {
            return -1;
        }
    }

    public void retrieveMultilineText() {
        yypushback(yytext().length() - lastIndexOfCrLf(yytext()));
        if(currentStepStart != 0) {
            zzStartRead = currentStepStart;
        }
    }

    public void setStepStart() {
        if(currentStepStart==0){
            currentStepStart = getTokenStart();
        }
    }

    public boolean checkAhead(char c) {

        if (zzMarkedPos >= zzBuffer.length()) {
            return false;
        }
        return zzBuffer.charAt(zzMarkedPos) == c;
    }
%}

%class _StoryLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

CRLF           = \r|\n|\r\n
BlankChar      = [ \t\f]
InputChar      = [^\r\n]
WhiteSpace     = {CRLF} | {BlankChar}
NonWhiteSpace  = [^ \n\r\t\f]
TableCellChar  = [^\r\n\|]
NonMetaKey     = [^@\r\n]
AnyKey         = {InputChar}|{InputChar}{CRLF}
KeyWords       = "Scenario: " | "Meta:" | "Examples:" | "Given " | "When " | "Then " | "And " | "!--" | "|" | "GivenStories:"

%state IN_DIRECTIVE
%state IN_STORY
%state IN_SCENARIO
%state IN_GIVEN
%state IN_WHEN
%state IN_THEN
%state IN_META
%state IN_TABLE
%state IN_EXAMPLES
%state IN_GIVENSTORIES
%eof{
    return;
%eof}

%%

<YYINITIAL> {
    ( "Scenario: "
    | "Meta:"
    | "Examples:"
    | "GivenStories:"
    | "Given " | "When " | "Then " | "And "
    | "!--"
    | "|" ) {InputChar}+  { yystatePush(IN_DIRECTIVE); yypushback(yytext().length());       }
    {CRLF}                { yystatePush(IN_STORY); yypushback(yytext().length());           }
    {InputChar}+          { return StoryTokenType.STORY_DESCRIPTION;                        }
}

<IN_DIRECTIVE> {
    "Scenario: "                             { yystatePopNPush(2, IN_SCENARIO); return StoryTokenType.SCENARIO_TYPE; }
    "Meta:"                                  { yystatePopNPush(2, IN_META);     return StoryTokenType.META;          }
    "Examples:"                              { yystatePopNPush(2, IN_EXAMPLES); return StoryTokenType.EXAMPLE_TYPE;  }
    "GivenStories:"                          { yystatePopNPush(2, IN_GIVENSTORIES); return StoryTokenType.GIVEN_STORIES_TYPE;  }
    "Given "                                 { yystatePopNPush(2, IN_GIVEN);    currentStepStart = 0; return StoryTokenType.GIVEN_TYPE;    }
    "When "                                  { yystatePopNPush(2, IN_WHEN);     currentStepStart = 0; return StoryTokenType.WHEN_TYPE;     }
    "Then "                                  { yystatePopNPush(2, IN_THEN);     currentStepStart = 0; return StoryTokenType.THEN_TYPE;     }
    "!--" {InputChar}*                       { yystatePop();                    return StoryTokenType.COMMENT;       }
    "|"                                      { yystatePopNPush(1, IN_TABLE);    return StoryTokenType.TABLE_DELIM;   }
    {WhiteSpace}+                            {                                  return StoryTokenType.WHITE_SPACE;   }
}

<IN_STORY>  {
    {CRLF}
        ( "Scenario: "
        | "Meta:"
        | "Examples:"
        | "GivenStories:"
        | "Given " | "When " | "Then " | "And "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    {InputChar}+                                     { return StoryTokenType.STORY_DESCRIPTION; }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}


<IN_SCENARIO>  {
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then " | "And "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    {InputChar}+                                     { return StoryTokenType.SCENARIO_TEXT; }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}

<IN_GIVENSTORIES>  {
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then " | "And "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    {InputChar}+                                     { return StoryTokenType.GIVEN_STORIES_TEXT; }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}

<IN_META>  {
    "@" {NonWhiteSpace}*                             { return StoryTokenType.META_KEY; }
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then " | "And "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    {NonMetaKey}+                                    { return StoryTokenType.META_TEXT;     }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}

<IN_GIVEN>  {
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    "And "{InputChar}+{CRLF}
        ("And " | "Given " | "When " | "Then "
        | {InputChar})                               { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.GIVEN_TYPE;    }
    {InputChar}+{CRLF}
        ("And " | "Given " | "When " | "Then "
        | "| "
        | "")                                        { retrieveMultilineText(); return StoryTokenType.STEP_TEXT; }
    {InputChar}+{CRLF}{InputChar}                    { setStepStart(); }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}

<IN_WHEN>  {
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    "And "{InputChar}+{CRLF}
        ("And " | "Given " | "When " | "Then "
        | {InputChar})                               { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.WHEN_TYPE;    }
    {InputChar}+{CRLF}
        ("And " | "Given " | "When " | "Then "
        | "| "
        | "")                                        { retrieveMultilineText(); return StoryTokenType.STEP_TEXT; }
    {InputChar}+{CRLF}{InputChar}                    { setStepStart(); }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}

<IN_THEN>  {
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    "And "{InputChar}+{CRLF}
        ("And " | "Given " | "When " | "Then "
        | {InputChar})                               { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.THEN_TYPE;    }
    {InputChar}+{CRLF}
        ("And " | "Given " | "When " | "Then "
        | "| "
        | "")                                        { retrieveMultilineText(); return StoryTokenType.STEP_TEXT; }
    {InputChar}+{CRLF}{InputChar}                    { setStepStart(); }
    {CRLF}                                           { return StoryTokenType.WHITE_SPACE;   }
}

<IN_EXAMPLES> {
    {CRLF}
        ( "Scenario: "
        | "GivenStories: "
        | "Meta:"
        | "Examples:"
        | "Given " | "When " | "Then " | "And "
        | "!--"
        | "|" )                                      { yystatePush(IN_DIRECTIVE); yypushback(yytext().length()); }
    {WhiteSpace}                                     { return StoryTokenType.WHITE_SPACE;   }
}

<IN_TABLE>  {
    {TableCellChar}+                   { return StoryTokenType.TABLE_CELL;  }
    "|"                                { return StoryTokenType.TABLE_DELIM; }
    {CRLF}                             { yystatePop(); yypushback(1); }
}

.                                      { return StoryTokenType.BAD_CHARACTER; }

