// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.*;
import static com.github.kumaraman21.intellijbehave.peg.StoryPegParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class StoryPegParser implements PsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == STORY_ALNUM) {
      r = Alnum(b, 0);
    }
    else if (t == STORY_DESCRIPTION) {
      r = Description(b, 0);
    }
    else if (t == STORY_EXAMPLES) {
      r = Examples(b, 0);
    }
    else if (t == STORY_GIVEN_STORIES) {
      r = GivenStories(b, 0);
    }
    else if (t == STORY_INJECT) {
      r = Inject(b, 0);
    }
    else if (t == STORY_IP_ADDRESS) {
      r = IpAddress(b, 0);
    }
    else if (t == STORY_LIFECYCLE) {
      r = Lifecycle(b, 0);
    }
    else if (t == STORY_LIFECYCLE_AFTER) {
      r = LifecycleAfter(b, 0);
    }
    else if (t == STORY_LIFECYCLE_BEFORE) {
      r = LifecycleBefore(b, 0);
    }
    else if (t == STORY_LINE) {
      r = Line(b, 0);
    }
    else if (t == STORY_LINE_EX) {
      r = LineEx(b, 0);
    }
    else if (t == STORY_META_ELEMENT) {
      r = MetaElement(b, 0);
    }
    else if (t == STORY_META_KEY) {
      r = MetaKey(b, 0);
    }
    else if (t == STORY_META_STATEMENT) {
      r = MetaStatement(b, 0);
    }
    else if (t == STORY_META_VALUE) {
      r = MetaValue(b, 0);
    }
    else if (t == STORY_MULTI_TEXT_LINE) {
      r = MultiTextLine(b, 0);
    }
    else if (t == STORY_NARRATIVE) {
      r = Narrative(b, 0);
    }
    else if (t == STORY_NARRATIVE_TEXT) {
      r = NarrativeText(b, 0);
    }
    else if (t == STORY_RECOVER_STEP) {
      r = RecoverStep(b, 0);
    }
    else if (t == STORY_SCENARIO) {
      r = Scenario(b, 0);
    }
    else if (t == STORY_SCENARIO_TITLE) {
      r = ScenarioTitle(b, 0);
    }
    else if (t == STORY_STEP) {
      r = Step(b, 0);
    }
    else if (t == STORY_STEP_AND) {
      r = StepAnd(b, 0);
    }
    else if (t == STORY_STEP_ARGUMENT) {
      r = StepArgument(b, 0);
    }
    else if (t == STORY_STEP_COMMENT) {
      r = StepComment(b, 0);
    }
    else if (t == STORY_STEP_GIVEN) {
      r = StepGiven(b, 0);
    }
    else if (t == STORY_STEP_LINE) {
      r = StepLine(b, 0);
    }
    else if (t == STORY_STEP_POST_PARAMETER) {
      r = StepPostParameter(b, 0);
    }
    else if (t == STORY_STEP_THEN) {
      r = StepThen(b, 0);
    }
    else if (t == STORY_STEP_WHEN) {
      r = StepWhen(b, 0);
    }
    else if (t == STORY_STORY) {
      r = Story(b, 0);
    }
    else if (t == STORY_STORY_PATH) {
      r = StoryPath(b, 0);
    }
    else if (t == STORY_STORY_PATHS) {
      r = StoryPaths(b, 0);
    }
    else if (t == STORY_TABLE) {
      r = Table(b, 0);
    }
    else if (t == STORY_TABLE_CELL) {
      r = TableCell(b, 0);
    }
    else if (t == STORY_TABLE_CELL_EMPTY) {
      r = TableCellEmpty(b, 0);
    }
    else if (t == STORY_TABLE_ROW) {
      r = TableRow(b, 0);
    }
    else if (t == STORY_URI) {
      r = Uri(b, 0);
    }
    else if (t == STORY_USER_INJECT) {
      r = UserInject(b, 0);
    }
    else if (t == STORY_WORD) {
      r = Word(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  /* ********************************************************** */
  // TOKEN_WORD
  public static boolean Alnum(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Alnum")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_WORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_WORD);
    exit_section_(b, m, STORY_ALNUM, r);
    return r;
  }

  /* ********************************************************** */
  // MultiTextLine
  public static boolean Description(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Description")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<description>");
    r = MultiTextLine(b, l + 1);
    exit_section_(b, l, m, STORY_DESCRIPTION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_EXAMPLES (Table|StoryPath)
  public static boolean Examples(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Examples")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_EXAMPLES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, STORY_TOKEN_EXAMPLES);
    p = r; // pin = 1
    r = r && Examples_1(b, l + 1);
    exit_section_(b, l, m, STORY_EXAMPLES, r, p, null);
    return r || p;
  }

  // Table|StoryPath
  private static boolean Examples_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Examples_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Table(b, l + 1);
    if (!r) r = StoryPath(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_GIVEN_STORIES SpaceStar StoryPaths Newline
  public static boolean GivenStories(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GivenStories")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_GIVEN_STORIES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, STORY_TOKEN_GIVEN_STORIES);
    p = r; // pin = 1
    r = r && report_error_(b, SpaceStar(b, l + 1));
    r = p && report_error_(b, StoryPaths(b, l + 1)) && r;
    r = p && Newline(b, l + 1) && r;
    exit_section_(b, l, m, STORY_GIVEN_STORIES, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // TOKEN_BRACKET_OPEN (IpAddress|Uri|Alnum|Space|TOKEN_PIPE|Punct)+ TOKEN_BRACKET_CLOSE
  public static boolean Inject(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Inject")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_BRACKET_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_BRACKET_OPEN);
    r = r && Inject_1(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_BRACKET_CLOSE);
    exit_section_(b, m, STORY_INJECT, r);
    return r;
  }

  // (IpAddress|Uri|Alnum|Space|TOKEN_PIPE|Punct)+
  private static boolean Inject_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Inject_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Inject_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Inject_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Inject_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // IpAddress|Uri|Alnum|Space|TOKEN_PIPE|Punct
  private static boolean Inject_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Inject_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IpAddress(b, l + 1);
    if (!r) r = Uri(b, l + 1);
    if (!r) r = Alnum(b, l + 1);
    if (!r) r = Space(b, l + 1);
    if (!r) r = consumeToken(b, STORY_TOKEN_PIPE);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_IP
  public static boolean IpAddress(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_IP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_IP);
    exit_section_(b, m, STORY_IP_ADDRESS, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_LIFECYCLE LifecycleBefore? LifecycleAfter?
  public static boolean Lifecycle(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Lifecycle")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_LIFECYCLE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_LIFECYCLE);
    r = r && Lifecycle_1(b, l + 1);
    r = r && Lifecycle_2(b, l + 1);
    exit_section_(b, m, STORY_LIFECYCLE, r);
    return r;
  }

  // LifecycleBefore?
  private static boolean Lifecycle_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Lifecycle_1")) return false;
    LifecycleBefore(b, l + 1);
    return true;
  }

  // LifecycleAfter?
  private static boolean Lifecycle_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Lifecycle_2")) return false;
    LifecycleAfter(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_AFTER Step+
  public static boolean LifecycleAfter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleAfter")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_AFTER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_AFTER);
    r = r && LifecycleAfter_1(b, l + 1);
    exit_section_(b, m, STORY_LIFECYCLE_AFTER, r);
    return r;
  }

  // Step+
  private static boolean LifecycleAfter_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleAfter_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Step(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Step(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LifecycleAfter_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_BEFORE Step+
  public static boolean LifecycleBefore(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleBefore")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_BEFORE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_BEFORE);
    r = r && LifecycleBefore_1(b, l + 1);
    exit_section_(b, m, STORY_LIFECYCLE_BEFORE, r);
    return r;
  }

  // Step+
  private static boolean LifecycleBefore_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleBefore_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Step(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Step(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LifecycleBefore_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (Word|Space|Punct)+
  public static boolean Line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<line>");
    r = Line_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Line_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Line", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, STORY_LINE, r, false, null);
    return r;
  }

  // Word|Space|Punct
  private static boolean Line_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Word(b, l + 1);
    if (!r) r = Space(b, l + 1);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (Word|Space|Punct)+ TOKEN_COLON
  public static boolean LineEx(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineEx")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<line ex>");
    r = LineEx_0(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_COLON);
    exit_section_(b, l, m, STORY_LINE_EX, r, false, null);
    return r;
  }

  // (Word|Space|Punct)+
  private static boolean LineEx_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineEx_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = LineEx_0_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!LineEx_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LineEx_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Word|Space|Punct
  private static boolean LineEx_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LineEx_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Word(b, l + 1);
    if (!r) r = Space(b, l + 1);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_AT MetaKey Space MetaValue
  public static boolean MetaElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaElement")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_AT);
    r = r && MetaKey(b, l + 1);
    r = r && Space(b, l + 1);
    r = r && MetaValue(b, l + 1);
    exit_section_(b, m, STORY_META_ELEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_AT TOKEN_WORD
  public static boolean MetaKey(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaKey")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STORY_TOKEN_AT, STORY_TOKEN_WORD);
    exit_section_(b, m, STORY_META_KEY, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_META (Space MetaElement)? Newline (MetaElement Newline)*
  public static boolean MetaStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_META)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, STORY_TOKEN_META);
    p = r; // pin = 1
    r = r && report_error_(b, MetaStatement_1(b, l + 1));
    r = p && report_error_(b, Newline(b, l + 1)) && r;
    r = p && MetaStatement_3(b, l + 1) && r;
    exit_section_(b, l, m, STORY_META_STATEMENT, r, p, null);
    return r || p;
  }

  // (Space MetaElement)?
  private static boolean MetaStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_1")) return false;
    MetaStatement_1_0(b, l + 1);
    return true;
  }

  // Space MetaElement
  private static boolean MetaStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Space(b, l + 1);
    r = r && MetaElement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (MetaElement Newline)*
  private static boolean MetaStatement_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MetaStatement_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaStatement_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // MetaElement Newline
  private static boolean MetaStatement_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaElement(b, l + 1);
    r = r && Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_WORD
  public static boolean MetaValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaValue")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_WORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_WORD);
    exit_section_(b, m, STORY_META_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // ((Word|Space|TOKEN_COLON|Punct)+ Newline)+
  public static boolean MultiTextLine(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<multi text line>");
    r = MultiTextLine_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!MultiTextLine_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MultiTextLine", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, STORY_MULTI_TEXT_LINE, r, false, null);
    return r;
  }

  // (Word|Space|TOKEN_COLON|Punct)+ Newline
  private static boolean MultiTextLine_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MultiTextLine_0_0(b, l + 1);
    r = r && Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (Word|Space|TOKEN_COLON|Punct)+
  private static boolean MultiTextLine_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MultiTextLine_0_0_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!MultiTextLine_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MultiTextLine_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Word|Space|TOKEN_COLON|Punct
  private static boolean MultiTextLine_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Word(b, l + 1);
    if (!r) r = Space(b, l + 1);
    if (!r) r = consumeToken(b, STORY_TOKEN_COLON);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_NARRATIVE Newline? NarrativeText
  public static boolean Narrative(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Narrative")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_NARRATIVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, STORY_TOKEN_NARRATIVE);
    p = r; // pin = 1
    r = r && report_error_(b, Narrative_1(b, l + 1));
    r = p && NarrativeText(b, l + 1) && r;
    exit_section_(b, l, m, STORY_NARRATIVE, r, p, null);
    return r || p;
  }

  // Newline?
  private static boolean Narrative_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Narrative_1")) return false;
    Newline(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // MultiTextLine
  public static boolean NarrativeText(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NarrativeText")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<narrative text>");
    r = MultiTextLine(b, l + 1);
    exit_section_(b, l, m, STORY_NARRATIVE_TEXT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_NEWLINE|<<eof>>
  static boolean Newline(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Newline")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_NEWLINE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_PUNCT | TOKEN_BRACKET_CLOSE|TOKEN_BRACKET_OPEN|TOKEN_COMMA|TOKEN_COLON
  static boolean Punct(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Punct")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PUNCT);
    if (!r) r = consumeToken(b, STORY_TOKEN_BRACKET_CLOSE);
    if (!r) r = consumeToken(b, STORY_TOKEN_BRACKET_OPEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_COMMA);
    if (!r) r = consumeToken(b, STORY_TOKEN_COLON);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(TOKEN_GIVEN|TOKEN_AND|TOKEN_THEN|TOKEN_WHEN|TOKEN_SCENARIO|TOKEN_COMMENT|TOKEN_GIVEN_STORIES|TOKEN_META|TOKEN_EXAMPLES)
  public static boolean RecoverStep(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecoverStep")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, "<recover step>");
    r = !RecoverStep_0(b, l + 1);
    exit_section_(b, l, m, STORY_RECOVER_STEP, r, false, null);
    return r;
  }

  // TOKEN_GIVEN|TOKEN_AND|TOKEN_THEN|TOKEN_WHEN|TOKEN_SCENARIO|TOKEN_COMMENT|TOKEN_GIVEN_STORIES|TOKEN_META|TOKEN_EXAMPLES
  private static boolean RecoverStep_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecoverStep_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_AND);
    if (!r) r = consumeToken(b, STORY_TOKEN_THEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_WHEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_SCENARIO);
    if (!r) r = consumeToken(b, STORY_TOKEN_COMMENT);
    if (!r) r = consumeToken(b, STORY_TOKEN_GIVEN_STORIES);
    if (!r) r = consumeToken(b, STORY_TOKEN_META);
    if (!r) r = consumeToken(b, STORY_TOKEN_EXAMPLES);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_SCENARIO SpaceStar (ScenarioTitle Newline)? MetaStatement? GivenStories? Step+ Examples?
  public static boolean Scenario(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_SCENARIO)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, STORY_TOKEN_SCENARIO);
    p = r; // pin = 1
    r = r && report_error_(b, SpaceStar(b, l + 1));
    r = p && report_error_(b, Scenario_2(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_3(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_4(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_5(b, l + 1)) && r;
    r = p && Scenario_6(b, l + 1) && r;
    exit_section_(b, l, m, STORY_SCENARIO, r, p, null);
    return r || p;
  }

  // (ScenarioTitle Newline)?
  private static boolean Scenario_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_2")) return false;
    Scenario_2_0(b, l + 1);
    return true;
  }

  // ScenarioTitle Newline
  private static boolean Scenario_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ScenarioTitle(b, l + 1);
    r = r && Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MetaStatement?
  private static boolean Scenario_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_3")) return false;
    MetaStatement(b, l + 1);
    return true;
  }

  // GivenStories?
  private static boolean Scenario_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_4")) return false;
    GivenStories(b, l + 1);
    return true;
  }

  // Step+
  private static boolean Scenario_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Step(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Step(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Scenario_5", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Examples?
  private static boolean Scenario_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_6")) return false;
    Examples(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_WORD ((Punct|Space) TOKEN_WORD)* | TOKEN_PATH
  public static boolean ScenarioTitle(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle")) return false;
    if (!nextTokenIs(b, "<scenario title>", STORY_TOKEN_PATH, STORY_TOKEN_WORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<scenario title>");
    r = ScenarioTitle_0(b, l + 1);
    if (!r) r = consumeToken(b, STORY_TOKEN_PATH);
    exit_section_(b, l, m, STORY_SCENARIO_TITLE, r, false, null);
    return r;
  }

  // TOKEN_WORD ((Punct|Space) TOKEN_WORD)*
  private static boolean ScenarioTitle_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_WORD);
    r = r && ScenarioTitle_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((Punct|Space) TOKEN_WORD)*
  private static boolean ScenarioTitle_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle_0_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!ScenarioTitle_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ScenarioTitle_0_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (Punct|Space) TOKEN_WORD
  private static boolean ScenarioTitle_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ScenarioTitle_0_1_0_0(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_WORD);
    exit_section_(b, m, null, r);
    return r;
  }

  // Punct|Space
  private static boolean ScenarioTitle_0_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle_0_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Punct(b, l + 1);
    if (!r) r = Space(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_SPACE
  static boolean Space(PsiBuilder b, int l) {
    return consumeToken(b, STORY_TOKEN_SPACE);
  }

  /* ********************************************************** */
  // TOKEN_SPACE+
  static boolean SpacePlus(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpacePlus")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_SPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_SPACE);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, STORY_TOKEN_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "SpacePlus", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_SPACE*
  static boolean SpaceStar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpaceStar")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, STORY_TOKEN_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "SpaceStar", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // StepWhen|StepGiven|StepThen|StepAnd|StepComment
  public static boolean Step(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step>");
    r = StepWhen(b, l + 1);
    if (!r) r = StepGiven(b, l + 1);
    if (!r) r = StepThen(b, l + 1);
    if (!r) r = StepAnd(b, l + 1);
    if (!r) r = StepComment(b, l + 1);
    exit_section_(b, l, m, STORY_STEP, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_AND StepArgument
  public static boolean StepAnd(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepAnd")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<step and>");
    r = consumeToken(b, STORY_TOKEN_AND);
    p = r; // pin = 1
    r = r && StepArgument(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_AND, r, p, RecoverStep_parser_);
    return r || p;
  }

  /* ********************************************************** */
  // StepLine StepPostParameter? Newline?
  public static boolean StepArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step argument>");
    r = StepLine(b, l + 1);
    r = r && StepArgument_1(b, l + 1);
    r = r && StepArgument_2(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_ARGUMENT, r, false, null);
    return r;
  }

  // StepPostParameter?
  private static boolean StepArgument_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_1")) return false;
    StepPostParameter(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean StepArgument_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_2")) return false;
    Newline(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_COMMENT (TOKEN_WHEN|TOKEN_THEN|TOKEN_GIVEN|TOKEN_AND|StoryPath|LineEx|Line)* Newline
  public static boolean StepComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepComment")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_COMMENT);
    r = r && StepComment_1(b, l + 1);
    r = r && Newline(b, l + 1);
    exit_section_(b, m, STORY_STEP_COMMENT, r);
    return r;
  }

  // (TOKEN_WHEN|TOKEN_THEN|TOKEN_GIVEN|TOKEN_AND|StoryPath|LineEx|Line)*
  private static boolean StepComment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepComment_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!StepComment_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StepComment_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // TOKEN_WHEN|TOKEN_THEN|TOKEN_GIVEN|TOKEN_AND|StoryPath|LineEx|Line
  private static boolean StepComment_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepComment_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_WHEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_THEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_AND);
    if (!r) r = StoryPath(b, l + 1);
    if (!r) r = LineEx(b, l + 1);
    if (!r) r = Line(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_GIVEN StepArgument
  public static boolean StepGiven(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepGiven")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<step given>");
    r = consumeToken(b, STORY_TOKEN_GIVEN);
    p = r; // pin = 1
    r = r && StepArgument(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_GIVEN, r, p, RecoverStep_parser_);
    return r || p;
  }

  /* ********************************************************** */
  // Line
  public static boolean StepLine(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step line>");
    r = Line(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_LINE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Newline Table| StoryPath
  public static boolean StepPostParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepPostParameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step post parameter>");
    r = StepPostParameter_0(b, l + 1);
    if (!r) r = StoryPath(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_POST_PARAMETER, r, false, null);
    return r;
  }

  // Newline Table
  private static boolean StepPostParameter_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepPostParameter_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Newline(b, l + 1);
    r = r && Table(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_THEN StepArgument
  public static boolean StepThen(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepThen")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<step then>");
    r = consumeToken(b, STORY_TOKEN_THEN);
    p = r; // pin = 1
    r = r && StepArgument(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_THEN, r, p, RecoverStep_parser_);
    return r || p;
  }

  /* ********************************************************** */
  // TOKEN_WHEN StepArgument
  public static boolean StepWhen(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepWhen")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<step when>");
    r = consumeToken(b, STORY_TOKEN_WHEN);
    p = r; // pin = 1
    r = r && StepArgument(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_WHEN, r, p, RecoverStep_parser_);
    return r || p;
  }

  /* ********************************************************** */
  // Description? MetaStatement? Narrative? GivenStories? Lifecycle? Scenario+
  public static boolean Story(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<story>");
    r = Story_0(b, l + 1);
    r = r && Story_1(b, l + 1);
    r = r && Story_2(b, l + 1);
    r = r && Story_3(b, l + 1);
    r = r && Story_4(b, l + 1);
    r = r && Story_5(b, l + 1);
    exit_section_(b, l, m, STORY_STORY, r, false, null);
    return r;
  }

  // Description?
  private static boolean Story_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_0")) return false;
    Description(b, l + 1);
    return true;
  }

  // MetaStatement?
  private static boolean Story_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_1")) return false;
    MetaStatement(b, l + 1);
    return true;
  }

  // Narrative?
  private static boolean Story_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_2")) return false;
    Narrative(b, l + 1);
    return true;
  }

  // GivenStories?
  private static boolean Story_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_3")) return false;
    GivenStories(b, l + 1);
    return true;
  }

  // Lifecycle?
  private static boolean Story_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_4")) return false;
    Lifecycle(b, l + 1);
    return true;
  }

  // Scenario+
  private static boolean Story_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Scenario(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Story_5", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_PATH
  public static boolean StoryPath(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPath")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_PATH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PATH);
    exit_section_(b, m, STORY_STORY_PATH, r);
    return r;
  }

  /* ********************************************************** */
  // StoryPath (TOKEN_COMMA StoryPath)*
  public static boolean StoryPaths(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPaths")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_PATH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StoryPath(b, l + 1);
    r = r && StoryPaths_1(b, l + 1);
    exit_section_(b, m, STORY_STORY_PATHS, r);
    return r;
  }

  // (TOKEN_COMMA StoryPath)*
  private static boolean StoryPaths_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPaths_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!StoryPaths_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StoryPaths_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // TOKEN_COMMA StoryPath
  private static boolean StoryPaths_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPaths_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_COMMA);
    r = r && StoryPath(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TableRow+
  public static boolean Table(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Table")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_PIPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TableRow(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!TableRow(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Table", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, STORY_TABLE, r);
    return r;
  }

  /* ********************************************************** */
  // (Word|Punct) (SpacePlus|Word|Punct)*
  public static boolean TableCell(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<table cell>");
    r = TableCell_0(b, l + 1);
    r = r && TableCell_1(b, l + 1);
    exit_section_(b, l, m, STORY_TABLE_CELL, r, false, null);
    return r;
  }

  // Word|Punct
  private static boolean TableCell_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Word(b, l + 1);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (SpacePlus|Word|Punct)*
  private static boolean TableCell_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!TableCell_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TableCell_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // SpacePlus|Word|Punct
  private static boolean TableCell_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpacePlus(b, l + 1);
    if (!r) r = Word(b, l + 1);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // SpaceStar
  public static boolean TableCellEmpty(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCellEmpty")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<table cell empty>");
    r = SpaceStar(b, l + 1);
    exit_section_(b, l, m, STORY_TABLE_CELL_EMPTY, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_PIPE (SpaceStar TableCell SpaceStar | TableCellEmpty) TOKEN_PIPE ((SpaceStar TableCell SpaceStar | TableCellEmpty) TOKEN_PIPE)* Newline
  public static boolean TableRow(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_PIPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PIPE);
    r = r && TableRow_1(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_PIPE);
    r = r && TableRow_3(b, l + 1);
    r = r && Newline(b, l + 1);
    exit_section_(b, m, STORY_TABLE_ROW, r);
    return r;
  }

  // SpaceStar TableCell SpaceStar | TableCellEmpty
  private static boolean TableRow_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TableRow_1_0(b, l + 1);
    if (!r) r = TableCellEmpty(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SpaceStar TableCell SpaceStar
  private static boolean TableRow_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpaceStar(b, l + 1);
    r = r && TableCell(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((SpaceStar TableCell SpaceStar | TableCellEmpty) TOKEN_PIPE)*
  private static boolean TableRow_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_3")) return false;
    int c = current_position_(b);
    while (true) {
      if (!TableRow_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TableRow_3", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (SpaceStar TableCell SpaceStar | TableCellEmpty) TOKEN_PIPE
  private static boolean TableRow_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TableRow_3_0_0(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_PIPE);
    exit_section_(b, m, null, r);
    return r;
  }

  // SpaceStar TableCell SpaceStar | TableCellEmpty
  private static boolean TableRow_3_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_3_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TableRow_3_0_0_0(b, l + 1);
    if (!r) r = TableCellEmpty(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // SpaceStar TableCell SpaceStar
  private static boolean TableRow_3_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_3_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpaceStar(b, l + 1);
    r = r && TableCell(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_WORD TOKEN_COLON TOKEN_WORD TOKEN_AT IpAddress (TOKEN_COLON TOKEN_WORD)?
  public static boolean Uri(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_WORD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeTokens(b, 0, STORY_TOKEN_WORD, STORY_TOKEN_COLON, STORY_TOKEN_WORD, STORY_TOKEN_AT);
    r = r && IpAddress(b, l + 1);
    p = r; // pin = 5
    r = r && Uri_5(b, l + 1);
    exit_section_(b, l, m, STORY_URI, r, p, null);
    return r || p;
  }

  // (TOKEN_COLON TOKEN_WORD)?
  private static boolean Uri_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri_5")) return false;
    Uri_5_0(b, l + 1);
    return true;
  }

  // TOKEN_COLON TOKEN_WORD
  private static boolean Uri_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STORY_TOKEN_COLON, STORY_TOKEN_WORD);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_DBRACKET_OPEN (IpAddress|Uri|Alnum|Space|TOKEN_PIPE|Punct)+ TOKEN_DBRACKET_CLOSE
  public static boolean UserInject(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UserInject")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_DBRACKET_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_DBRACKET_OPEN);
    r = r && UserInject_1(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_DBRACKET_CLOSE);
    exit_section_(b, m, STORY_USER_INJECT, r);
    return r;
  }

  // (IpAddress|Uri|Alnum|Space|TOKEN_PIPE|Punct)+
  private static boolean UserInject_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UserInject_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = UserInject_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!UserInject_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "UserInject_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // IpAddress|Uri|Alnum|Space|TOKEN_PIPE|Punct
  private static boolean UserInject_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UserInject_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IpAddress(b, l + 1);
    if (!r) r = Uri(b, l + 1);
    if (!r) r = Alnum(b, l + 1);
    if (!r) r = Space(b, l + 1);
    if (!r) r = consumeToken(b, STORY_TOKEN_PIPE);
    if (!r) r = Punct(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IpAddress|Uri|Alnum|Inject|UserInject
  public static boolean Word(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Word")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<word>");
    r = IpAddress(b, l + 1);
    if (!r) r = Uri(b, l + 1);
    if (!r) r = Alnum(b, l + 1);
    if (!r) r = Inject(b, l + 1);
    if (!r) r = UserInject(b, l + 1);
    exit_section_(b, l, m, STORY_WORD, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !<<eof>> Story
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = root_0(b, l + 1);
    p = r; // pin = 1
    r = r && Story(b, l + 1);
    exit_section_(b, l, m, null, r, p, null);
    return r || p;
  }

  // !<<eof>>
  private static boolean root_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !eof(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  final static Parser RecoverStep_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return RecoverStep(b, l + 1);
    }
  };
}
