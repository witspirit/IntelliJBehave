// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.kumaraman21.intellijbehave.parser.IJBehaveElementType.*;
import static com.github.kumaraman21.intellijbehave.parser.JBehaveParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class JBehaveParser implements PsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == JB_DESCRIPTION) {
      r = Description(b, 0);
    }
    else if (t == JB_EXAMPLES) {
      r = Examples(b, 0);
    }
    else if (t == JB_GIVEN_STORIES) {
      r = GivenStories(b, 0);
    }
    else if (t == JB_IP_ADDRESS) {
      r = IpAddress(b, 0);
    }
    else if (t == JB_LIFECYCLE) {
      r = Lifecycle(b, 0);
    }
    else if (t == JB_LIFECYCLE_AFTER) {
      r = LifecycleAfter(b, 0);
    }
    else if (t == JB_LIFECYCLE_BEFORE) {
      r = LifecycleBefore(b, 0);
    }
    else if (t == JB_META_ELEMENT) {
      r = MetaElement(b, 0);
    }
    else if (t == JB_META_KEY) {
      r = MetaKey(b, 0);
    }
    else if (t == JB_META_STATEMENT) {
      r = MetaStatement(b, 0);
    }
    else if (t == JB_META_VALUE) {
      r = MetaValue(b, 0);
    }
    else if (t == JB_NARRATIVE) {
      r = Narrative(b, 0);
    }
    else if (t == JB_NARRATIVE_TEXT) {
      r = NarrativeText(b, 0);
    }
    else if (t == JB_SCENARIO) {
      r = Scenario(b, 0);
    }
    else if (t == JB_SCENARIO_TITLE) {
      r = ScenarioTitle(b, 0);
    }
    else if (t == JB_STEP) {
      r = Step(b, 0);
    }
    else if (t == JB_STEP_ARGUMENT) {
      r = StepArgument(b, 0);
    }
    else if (t == JB_STEP_LINE) {
      r = StepLine(b, 0);
    }
    else if (t == JB_STEP_PAR) {
      r = StepPar(b, 0);
    }
    else if (t == JB_STORY) {
      r = Story(b, 0);
    }
    else if (t == JB_STORY_PATH) {
      r = StoryPath(b, 0);
    }
    else if (t == JB_STORY_PATHS) {
      r = StoryPaths(b, 0);
    }
    else if (t == JB_TABLE) {
      r = Table(b, 0);
    }
    else if (t == JB_TABLE_CELL) {
      r = TableCell(b, 0);
    }
    else if (t == JB_TABLE_ROW) {
      r = TableRow(b, 0);
    }
    else if (t == JB_URI) {
      r = Uri(b, 0);
    }
    else if (t == JB_URI_IDENTIFIER) {
      r = UriIdentifier(b, 0);
    }
    else if (t == JB_URI_WORD) {
      r = UriWord(b, 0);
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
  static boolean Alnum(PsiBuilder b, int l) {
    return consumeToken(b, JB_TOKEN_WORD);
  }

  /* ********************************************************** */
  // MultiTextLine
  public static boolean Description(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Description")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<description>");
    r = MultiTextLine(b, l + 1);
    exit_section_(b, l, m, JB_DESCRIPTION, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_EXAMPLES WhiteSpace (Table|StoryPath)
  public static boolean Examples(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Examples")) return false;
    if (!nextTokenIs(b, JB_TOKEN_EXAMPLES)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, JB_TOKEN_EXAMPLES);
    p = r; // pin = 1
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && Examples_2(b, l + 1) && r;
    exit_section_(b, l, m, JB_EXAMPLES, r, p, null);
    return r || p;
  }

  // Table|StoryPath
  private static boolean Examples_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Examples_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Table(b, l + 1);
    if (!r) r = StoryPath(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_GIVEN_STORIES WhiteSpace StoryPaths?
  public static boolean GivenStories(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GivenStories")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<given stories>");
    r = consumeToken(b, JB_TOKEN_GIVEN_STORIES);
    p = r; // pin = 1
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && GivenStories_2(b, l + 1) && r;
    exit_section_(b, l, m, JB_GIVEN_STORIES, r, p, RecoverMeta_parser_);
    return r || p;
  }

  // StoryPaths?
  private static boolean GivenStories_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GivenStories_2")) return false;
    StoryPaths(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_IP | Alnum ('.' Alnum)+
  public static boolean IpAddress(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress")) return false;
    if (!nextTokenIs(b, "<ip address>", JB_TOKEN_IP, JB_TOKEN_WORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<ip address>");
    r = consumeToken(b, JB_TOKEN_IP);
    if (!r) r = IpAddress_1(b, l + 1);
    exit_section_(b, l, m, JB_IP_ADDRESS, r, false, null);
    return r;
  }

  // Alnum ('.' Alnum)+
  private static boolean IpAddress_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Alnum(b, l + 1);
    r = r && IpAddress_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ('.' Alnum)+
  private static boolean IpAddress_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = IpAddress_1_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!IpAddress_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "IpAddress_1_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // '.' Alnum
  private static boolean IpAddress_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && Alnum(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_LIFECYCLE WhiteSpace LifecycleBefore? WhiteSpace LifecycleAfter?
  public static boolean Lifecycle(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Lifecycle")) return false;
    if (!nextTokenIs(b, JB_TOKEN_LIFECYCLE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, JB_TOKEN_LIFECYCLE);
    p = r; // pin = 1
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && report_error_(b, Lifecycle_2(b, l + 1)) && r;
    r = p && report_error_(b, WhiteSpace(b, l + 1)) && r;
    r = p && Lifecycle_4(b, l + 1) && r;
    exit_section_(b, l, m, JB_LIFECYCLE, r, p, null);
    return r || p;
  }

  // LifecycleBefore?
  private static boolean Lifecycle_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Lifecycle_2")) return false;
    LifecycleBefore(b, l + 1);
    return true;
  }

  // LifecycleAfter?
  private static boolean Lifecycle_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Lifecycle_4")) return false;
    LifecycleAfter(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_AFTER WhiteSpace (WhiteSpace Step)*
  public static boolean LifecycleAfter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleAfter")) return false;
    if (!nextTokenIs(b, JB_TOKEN_AFTER)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, JB_TOKEN_AFTER);
    p = r; // pin = 1
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && LifecycleAfter_2(b, l + 1) && r;
    exit_section_(b, l, m, JB_LIFECYCLE_AFTER, r, p, null);
    return r || p;
  }

  // (WhiteSpace Step)*
  private static boolean LifecycleAfter_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleAfter_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!LifecycleAfter_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LifecycleAfter_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // WhiteSpace Step
  private static boolean LifecycleAfter_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleAfter_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Step(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_BEFORE WhiteSpace (WhiteSpace Step)*
  public static boolean LifecycleBefore(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleBefore")) return false;
    if (!nextTokenIs(b, JB_TOKEN_BEFORE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, JB_TOKEN_BEFORE);
    p = r; // pin = 1
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && LifecycleBefore_2(b, l + 1) && r;
    exit_section_(b, l, m, JB_LIFECYCLE_BEFORE, r, p, null);
    return r || p;
  }

  // (WhiteSpace Step)*
  private static boolean LifecycleBefore_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleBefore_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!LifecycleBefore_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "LifecycleBefore_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // WhiteSpace Step
  private static boolean LifecycleBefore_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LifecycleBefore_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Step(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Word (WordSeparator Word)*
  static boolean Line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Word(b, l + 1);
    r = r && Line_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (WordSeparator Word)*
  private static boolean Line_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Line_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Line_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // WordSeparator Word
  private static boolean Line_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WordSeparator(b, l + 1);
    r = r && Word(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(TOKEN_NARRATIVE|TOKEN_GIVEN_STORIES|TOKEN_LIFECYCLE|TOKEN_SCENARIO) (MetaKey Space MetaValue |MetaKey | MetaValue)
  public static boolean MetaElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaElement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<meta element>");
    r = MetaElement_0(b, l + 1);
    r = r && MetaElement_1(b, l + 1);
    exit_section_(b, l, m, JB_META_ELEMENT, r, false, null);
    return r;
  }

  // !(TOKEN_NARRATIVE|TOKEN_GIVEN_STORIES|TOKEN_LIFECYCLE|TOKEN_SCENARIO)
  private static boolean MetaElement_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaElement_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !MetaElement_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // TOKEN_NARRATIVE|TOKEN_GIVEN_STORIES|TOKEN_LIFECYCLE|TOKEN_SCENARIO
  private static boolean MetaElement_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaElement_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_NARRATIVE);
    if (!r) r = consumeToken(b, JB_TOKEN_GIVEN_STORIES);
    if (!r) r = consumeToken(b, JB_TOKEN_LIFECYCLE);
    if (!r) r = consumeToken(b, JB_TOKEN_SCENARIO);
    exit_section_(b, m, null, r);
    return r;
  }

  // MetaKey Space MetaValue |MetaKey | MetaValue
  private static boolean MetaElement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaElement_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaElement_1_0(b, l + 1);
    if (!r) r = MetaKey(b, l + 1);
    if (!r) r = MetaValue(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MetaKey Space MetaValue
  private static boolean MetaElement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaElement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaKey(b, l + 1);
    r = r && Space(b, l + 1);
    r = r && MetaValue(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_MKEY
  public static boolean MetaKey(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaKey")) return false;
    if (!nextTokenIs(b, JB_TOKEN_MKEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_MKEY);
    exit_section_(b, m, JB_META_KEY, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_META (Newline)* ((Space|Newline)* (MetaElement|StepComment))*
  public static boolean MetaStatement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<meta statement>");
    r = consumeToken(b, JB_TOKEN_META);
    p = r; // pin = 1
    r = r && report_error_(b, MetaStatement_1(b, l + 1));
    r = p && MetaStatement_2(b, l + 1) && r;
    exit_section_(b, l, m, JB_META_STATEMENT, r, p, RecoverMeta_parser_);
    return r || p;
  }

  // (Newline)*
  private static boolean MetaStatement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MetaStatement_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaStatement_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (Newline)
  private static boolean MetaStatement_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((Space|Newline)* (MetaElement|StepComment))*
  private static boolean MetaStatement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MetaStatement_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaStatement_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (Space|Newline)* (MetaElement|StepComment)
  private static boolean MetaStatement_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaStatement_2_0_0(b, l + 1);
    r = r && MetaStatement_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (Space|Newline)*
  private static boolean MetaStatement_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_2_0_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!MetaStatement_2_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaStatement_2_0_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // Space|Newline
  private static boolean MetaStatement_2_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_2_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Space(b, l + 1);
    if (!r) r = Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // MetaElement|StepComment
  private static boolean MetaStatement_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_2_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaElement(b, l + 1);
    if (!r) r = StepComment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Line
  public static boolean MetaValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaValue")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<meta value>");
    r = Line(b, l + 1);
    exit_section_(b, l, m, JB_META_VALUE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (WhiteSpace Line)+
  static boolean MultiTextLine(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MultiTextLine_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!MultiTextLine_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MultiTextLine", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // WhiteSpace Line
  private static boolean MultiTextLine_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Line(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_NARRATIVE WhiteSpace NarrativeText?
  public static boolean Narrative(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Narrative")) return false;
    if (!nextTokenIs(b, JB_TOKEN_NARRATIVE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, JB_TOKEN_NARRATIVE);
    p = r; // pin = 1
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && Narrative_2(b, l + 1) && r;
    exit_section_(b, l, m, JB_NARRATIVE, r, p, null);
    return r || p;
  }

  // NarrativeText?
  private static boolean Narrative_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Narrative_2")) return false;
    NarrativeText(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // MultiTextLine
  public static boolean NarrativeText(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NarrativeText")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<narrative text>");
    r = MultiTextLine(b, l + 1);
    exit_section_(b, l, m, JB_NARRATIVE_TEXT, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_NEWLINE
  static boolean Newline(PsiBuilder b, int l) {
    return consumeToken(b, JB_TOKEN_NEWLINE);
  }

  /* ********************************************************** */
  // !(TOKEN_NARRATIVE|TOKEN_GIVEN_STORIES|TOKEN_LIFECYCLE|TOKEN_SCENARIO|TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND|Newline|TOKEN_COMMENT|TOKEN_EXAMPLES)
  static boolean RecoverMeta(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecoverMeta")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !RecoverMeta_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // TOKEN_NARRATIVE|TOKEN_GIVEN_STORIES|TOKEN_LIFECYCLE|TOKEN_SCENARIO|TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND|Newline|TOKEN_COMMENT|TOKEN_EXAMPLES
  private static boolean RecoverMeta_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RecoverMeta_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_NARRATIVE);
    if (!r) r = consumeToken(b, JB_TOKEN_GIVEN_STORIES);
    if (!r) r = consumeToken(b, JB_TOKEN_LIFECYCLE);
    if (!r) r = consumeToken(b, JB_TOKEN_SCENARIO);
    if (!r) r = consumeToken(b, JB_TOKEN_WHEN);
    if (!r) r = consumeToken(b, JB_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, JB_TOKEN_THEN);
    if (!r) r = consumeToken(b, JB_TOKEN_AND);
    if (!r) r = Newline(b, l + 1);
    if (!r) r = consumeToken(b, JB_TOKEN_COMMENT);
    if (!r) r = consumeToken(b, JB_TOKEN_EXAMPLES);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !(TOKEN_LIFECYCLE|TOKEN_BEFORE|TOKEN_AFTER|TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND)
  //              TOKEN_SCENARIO WhiteSpace ScenarioTitle?
  //              WhiteSpace StepComment?
  //              WhiteSpace MetaStatement?
  //              WhiteSpace StepComment?
  //              WhiteSpace GivenStories?
  //              ((WhiteSpace Step |WhiteSpace StepComment)+
  //              (WhiteSpace Examples)?)*
  public static boolean Scenario(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<scenario>");
    r = Scenario_0(b, l + 1);
    r = r && consumeToken(b, JB_TOKEN_SCENARIO);
    p = r; // pin = 2
    r = r && report_error_(b, WhiteSpace(b, l + 1));
    r = p && report_error_(b, Scenario_3(b, l + 1)) && r;
    r = p && report_error_(b, WhiteSpace(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_5(b, l + 1)) && r;
    r = p && report_error_(b, WhiteSpace(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_7(b, l + 1)) && r;
    r = p && report_error_(b, WhiteSpace(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_9(b, l + 1)) && r;
    r = p && report_error_(b, WhiteSpace(b, l + 1)) && r;
    r = p && report_error_(b, Scenario_11(b, l + 1)) && r;
    r = p && Scenario_12(b, l + 1) && r;
    exit_section_(b, l, m, JB_SCENARIO, r, p, RecoverMeta_parser_);
    return r || p;
  }

  // !(TOKEN_LIFECYCLE|TOKEN_BEFORE|TOKEN_AFTER|TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND)
  private static boolean Scenario_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !Scenario_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // TOKEN_LIFECYCLE|TOKEN_BEFORE|TOKEN_AFTER|TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND
  private static boolean Scenario_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_LIFECYCLE);
    if (!r) r = consumeToken(b, JB_TOKEN_BEFORE);
    if (!r) r = consumeToken(b, JB_TOKEN_AFTER);
    if (!r) r = consumeToken(b, JB_TOKEN_WHEN);
    if (!r) r = consumeToken(b, JB_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, JB_TOKEN_THEN);
    if (!r) r = consumeToken(b, JB_TOKEN_AND);
    exit_section_(b, m, null, r);
    return r;
  }

  // ScenarioTitle?
  private static boolean Scenario_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_3")) return false;
    ScenarioTitle(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Scenario_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // MetaStatement?
  private static boolean Scenario_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_7")) return false;
    MetaStatement(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Scenario_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_9")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // GivenStories?
  private static boolean Scenario_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_11")) return false;
    GivenStories(b, l + 1);
    return true;
  }

  // ((WhiteSpace Step |WhiteSpace StepComment)+
  //              (WhiteSpace Examples)?)*
  private static boolean Scenario_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Scenario_12_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Scenario_12", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (WhiteSpace Step |WhiteSpace StepComment)+
  //              (WhiteSpace Examples)?
  private static boolean Scenario_12_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario_12_0_0(b, l + 1);
    r = r && Scenario_12_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (WhiteSpace Step |WhiteSpace StepComment)+
  private static boolean Scenario_12_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario_12_0_0_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Scenario_12_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Scenario_12_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // WhiteSpace Step |WhiteSpace StepComment
  private static boolean Scenario_12_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario_12_0_0_0_0(b, l + 1);
    if (!r) r = Scenario_12_0_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // WhiteSpace Step
  private static boolean Scenario_12_0_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Step(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // WhiteSpace StepComment
  private static boolean Scenario_12_0_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && StepComment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (WhiteSpace Examples)?
  private static boolean Scenario_12_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0_1")) return false;
    Scenario_12_0_1_0(b, l + 1);
    return true;
  }

  // WhiteSpace Examples
  private static boolean Scenario_12_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_12_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Examples(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Line
  public static boolean ScenarioTitle(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<scenario title>");
    r = Line(b, l + 1);
    exit_section_(b, l, m, JB_SCENARIO_TITLE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // StepSimpleWord|StoryPath|TOKEN_COLON|TOKEN_MKEY|TOKEN_AND|TOKEN_GIVEN|TOKEN_WHEN|TOKEN_THEN
  static boolean SimpleWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleWord")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StepSimpleWord(b, l + 1);
    if (!r) r = StoryPath(b, l + 1);
    if (!r) r = consumeToken(b, JB_TOKEN_COLON);
    if (!r) r = consumeToken(b, JB_TOKEN_MKEY);
    if (!r) r = consumeToken(b, JB_TOKEN_AND);
    if (!r) r = consumeToken(b, JB_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, JB_TOKEN_WHEN);
    if (!r) r = consumeToken(b, JB_TOKEN_THEN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_SPACE
  static boolean Space(PsiBuilder b, int l) {
    return consumeToken(b, JB_TOKEN_SPACE);
  }

  /* ********************************************************** */
  // TOKEN_SPACE*
  static boolean SpaceStar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SpaceStar")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, JB_TOKEN_SPACE)) break;
      if (!empty_element_parsed_guard_(b, "SpaceStar", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // !(TOKEN_LIFECYCLE|TOKEN_BEFORE|TOKEN_AFTER) StepPar SpaceStar StepArgument
  public static boolean Step(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<step>");
    r = Step_0(b, l + 1);
    r = r && StepPar(b, l + 1);
    p = r; // pin = 2
    r = r && report_error_(b, SpaceStar(b, l + 1));
    r = p && StepArgument(b, l + 1) && r;
    exit_section_(b, l, m, JB_STEP, r, p, RecoverMeta_parser_);
    return r || p;
  }

  // !(TOKEN_LIFECYCLE|TOKEN_BEFORE|TOKEN_AFTER)
  private static boolean Step_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_, null);
    r = !Step_0_0(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // TOKEN_LIFECYCLE|TOKEN_BEFORE|TOKEN_AFTER
  private static boolean Step_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_LIFECYCLE);
    if (!r) r = consumeToken(b, JB_TOKEN_BEFORE);
    if (!r) r = consumeToken(b, JB_TOKEN_AFTER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // StepLine SpaceStar (WhiteSpace Table | StoryPaths) | StepLine
  public static boolean StepArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step argument>");
    r = StepArgument_0(b, l + 1);
    if (!r) r = StepLine(b, l + 1);
    exit_section_(b, l, m, JB_STEP_ARGUMENT, r, false, null);
    return r;
  }

  // StepLine SpaceStar (WhiteSpace Table | StoryPaths)
  private static boolean StepArgument_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StepLine(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    r = r && StepArgument_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // WhiteSpace Table | StoryPaths
  private static boolean StepArgument_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StepArgument_0_2_0(b, l + 1);
    if (!r) r = StoryPaths(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // WhiteSpace Table
  private static boolean StepArgument_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Table(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_COMMENT WhiteSpace
  static boolean StepComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepComment")) return false;
    if (!nextTokenIs(b, JB_TOKEN_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_COMMENT);
    r = r && WhiteSpace(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // StepWord (WordSeparator StepWord)* Space* TOKEN_COLON?
  public static boolean StepLine(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step line>");
    r = StepWord(b, l + 1);
    r = r && StepLine_1(b, l + 1);
    r = r && StepLine_2(b, l + 1);
    r = r && StepLine_3(b, l + 1);
    exit_section_(b, l, m, JB_STEP_LINE, r, false, null);
    return r;
  }

  // (WordSeparator StepWord)*
  private static boolean StepLine_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!StepLine_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StepLine_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // WordSeparator StepWord
  private static boolean StepLine_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WordSeparator(b, l + 1);
    r = r && StepWord(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Space*
  private static boolean StepLine_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Space(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StepLine_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // TOKEN_COLON?
  private static boolean StepLine_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine_3")) return false;
    consumeToken(b, JB_TOKEN_COLON);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND
  public static boolean StepPar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepPar")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step par>");
    r = consumeToken(b, JB_TOKEN_WHEN);
    if (!r) r = consumeToken(b, JB_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, JB_TOKEN_THEN);
    if (!r) r = consumeToken(b, JB_TOKEN_AND);
    exit_section_(b, l, m, JB_STEP_PAR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Uri|IpAddress|Alnum|TOKEN_USER_INJECT|TOKEN_INJECT|TOKEN_BRACKET_OPEN|TOKEN_BRACKET_CLOSE|','|'.'
  static boolean StepSimpleWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepSimpleWord")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Uri(b, l + 1);
    if (!r) r = IpAddress(b, l + 1);
    if (!r) r = Alnum(b, l + 1);
    if (!r) r = consumeToken(b, JB_TOKEN_USER_INJECT);
    if (!r) r = consumeToken(b, JB_TOKEN_INJECT);
    if (!r) r = consumeToken(b, JB_TOKEN_BRACKET_OPEN);
    if (!r) r = consumeToken(b, JB_TOKEN_BRACKET_CLOSE);
    if (!r) r = consumeToken(b, JB_TOKEN_COMMA);
    if (!r) r = consumeToken(b, ".");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // StepSimpleWord+
  static boolean StepWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepWord")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StepSimpleWord(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!StepSimpleWord(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StepWord", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // StepComment? Description?
  //             WhiteSpace StepComment? MetaStatement?
  //             WhiteSpace StepComment? Narrative?
  //             WhiteSpace StepComment? GivenStories?
  //             WhiteSpace StepComment? Lifecycle?
  //             WhiteSpace StepComment? (WhiteSpace Scenario)*
  public static boolean Story(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<story>");
    r = Story_0(b, l + 1);
    r = r && Story_1(b, l + 1);
    r = r && WhiteSpace(b, l + 1);
    r = r && Story_3(b, l + 1);
    r = r && Story_4(b, l + 1);
    r = r && WhiteSpace(b, l + 1);
    r = r && Story_6(b, l + 1);
    r = r && Story_7(b, l + 1);
    r = r && WhiteSpace(b, l + 1);
    r = r && Story_9(b, l + 1);
    r = r && Story_10(b, l + 1);
    r = r && WhiteSpace(b, l + 1);
    r = r && Story_12(b, l + 1);
    r = r && Story_13(b, l + 1);
    r = r && WhiteSpace(b, l + 1);
    r = r && Story_15(b, l + 1);
    r = r && Story_16(b, l + 1);
    exit_section_(b, l, m, JB_STORY, r, false, null);
    return r;
  }

  // StepComment?
  private static boolean Story_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_0")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // Description?
  private static boolean Story_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_1")) return false;
    Description(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Story_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_3")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // MetaStatement?
  private static boolean Story_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_4")) return false;
    MetaStatement(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Story_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_6")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // Narrative?
  private static boolean Story_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_7")) return false;
    Narrative(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Story_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_9")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // GivenStories?
  private static boolean Story_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_10")) return false;
    GivenStories(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Story_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_12")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // Lifecycle?
  private static boolean Story_13(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_13")) return false;
    Lifecycle(b, l + 1);
    return true;
  }

  // StepComment?
  private static boolean Story_15(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_15")) return false;
    StepComment(b, l + 1);
    return true;
  }

  // (WhiteSpace Scenario)*
  private static boolean Story_16(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_16")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Story_16_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Story_16", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // WhiteSpace Scenario
  private static boolean Story_16_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_16_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && Scenario(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_PATH
  public static boolean StoryPath(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPath")) return false;
    if (!nextTokenIs(b, JB_TOKEN_PATH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_PATH);
    exit_section_(b, m, JB_STORY_PATH, r);
    return r;
  }

  /* ********************************************************** */
  // StoryPath (WhiteSpace TOKEN_COMMA WhiteSpace StoryPath)*
  public static boolean StoryPaths(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPaths")) return false;
    if (!nextTokenIs(b, JB_TOKEN_PATH)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = StoryPath(b, l + 1);
    p = r; // pin = 1
    r = r && StoryPaths_1(b, l + 1);
    exit_section_(b, l, m, JB_STORY_PATHS, r, p, null);
    return r || p;
  }

  // (WhiteSpace TOKEN_COMMA WhiteSpace StoryPath)*
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

  // WhiteSpace TOKEN_COMMA WhiteSpace StoryPath
  private static boolean StoryPaths_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StoryPaths_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && consumeToken(b, JB_TOKEN_COMMA);
    r = r && WhiteSpace(b, l + 1);
    r = r && StoryPath(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (WhiteSpace TableRow)+
  public static boolean Table(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Table")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<table>");
    r = Table_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Table_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Table", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, JB_TABLE, r, false, null);
    return r;
  }

  // WhiteSpace TableRow
  private static boolean Table_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Table_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WhiteSpace(b, l + 1);
    r = r && TableRow(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (Line|TOKEN_META|MetaElement)+
  public static boolean TableCell(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<table cell>");
    r = TableCell_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!TableCell_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TableCell", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, JB_TABLE_CELL, r, false, null);
    return r;
  }

  // Line|TOKEN_META|MetaElement
  private static boolean TableCell_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Line(b, l + 1);
    if (!r) r = consumeToken(b, JB_TOKEN_META);
    if (!r) r = MetaElement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_PIPE SpaceStar (TableCell SpaceStar)? TOKEN_PIPE (SpaceStar (TableCell SpaceStar)?  TOKEN_PIPE)*
  public static boolean TableRow(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow")) return false;
    if (!nextTokenIs(b, JB_TOKEN_PIPE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = consumeToken(b, JB_TOKEN_PIPE);
    p = r; // pin = 1
    r = r && report_error_(b, SpaceStar(b, l + 1));
    r = p && report_error_(b, TableRow_2(b, l + 1)) && r;
    r = p && report_error_(b, consumeToken(b, JB_TOKEN_PIPE)) && r;
    r = p && TableRow_4(b, l + 1) && r;
    exit_section_(b, l, m, JB_TABLE_ROW, r, p, null);
    return r || p;
  }

  // (TableCell SpaceStar)?
  private static boolean TableRow_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_2")) return false;
    TableRow_2_0(b, l + 1);
    return true;
  }

  // TableCell SpaceStar
  private static boolean TableRow_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TableCell(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (SpaceStar (TableCell SpaceStar)?  TOKEN_PIPE)*
  private static boolean TableRow_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_4")) return false;
    int c = current_position_(b);
    while (true) {
      if (!TableRow_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TableRow_4", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // SpaceStar (TableCell SpaceStar)?  TOKEN_PIPE
  private static boolean TableRow_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpaceStar(b, l + 1);
    r = r && TableRow_4_0_1(b, l + 1);
    r = r && consumeToken(b, JB_TOKEN_PIPE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (TableCell SpaceStar)?
  private static boolean TableRow_4_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_4_0_1")) return false;
    TableRow_4_0_1_0(b, l + 1);
    return true;
  }

  // TableCell SpaceStar
  private static boolean TableRow_4_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow_4_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = TableCell(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Alnum TOKEN_COLON Word '@' UriIdentifier (TOKEN_COLON Alnum)?
  public static boolean Uri(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri")) return false;
    if (!nextTokenIs(b, JB_TOKEN_WORD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = Alnum(b, l + 1);
    r = r && consumeToken(b, JB_TOKEN_COLON);
    r = r && Word(b, l + 1);
    r = r && consumeToken(b, "@");
    r = r && UriIdentifier(b, l + 1);
    p = r; // pin = 5
    r = r && Uri_5(b, l + 1);
    exit_section_(b, l, m, JB_URI, r, p, null);
    return r || p;
  }

  // (TOKEN_COLON Alnum)?
  private static boolean Uri_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri_5")) return false;
    Uri_5_0(b, l + 1);
    return true;
  }

  // TOKEN_COLON Alnum
  private static boolean Uri_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, JB_TOKEN_COLON);
    r = r && Alnum(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IpAddress| (UriWord|'.')+
  public static boolean UriIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriIdentifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<uri identifier>");
    r = IpAddress(b, l + 1);
    if (!r) r = UriIdentifier_1(b, l + 1);
    exit_section_(b, l, m, JB_URI_IDENTIFIER, r, false, null);
    return r;
  }

  // (UriWord|'.')+
  private static boolean UriIdentifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriIdentifier_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = UriIdentifier_1_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!UriIdentifier_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "UriIdentifier_1", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // UriWord|'.'
  private static boolean UriIdentifier_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriIdentifier_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = UriWord(b, l + 1);
    if (!r) r = consumeToken(b, ".");
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Alnum|TOKEN_USER_INJECT|TOKEN_INJECT
  public static boolean UriWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriWord")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<uri word>");
    r = Alnum(b, l + 1);
    if (!r) r = consumeToken(b, JB_TOKEN_USER_INJECT);
    if (!r) r = consumeToken(b, JB_TOKEN_INJECT);
    exit_section_(b, l, m, JB_URI_WORD, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Newline* SpaceStar
  static boolean WhiteSpace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhiteSpace")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<whitespace>");
    r = WhiteSpace_0(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, l, m, null, r, false, null);
    return r;
  }

  // Newline*
  private static boolean WhiteSpace_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WhiteSpace_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Newline(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "WhiteSpace_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // SimpleWord+
  static boolean Word(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Word")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SimpleWord(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!SimpleWord(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Word", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Space+
  static boolean WordSeparator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WordSeparator")) return false;
    if (!nextTokenIs(b, JB_TOKEN_SPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Space(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Space(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "WordSeparator", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !<<eof>> WhiteSpace ( Table | Story ) WhiteSpace
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = root_0(b, l + 1);
    r = r && WhiteSpace(b, l + 1);
    r = r && root_2(b, l + 1);
    p = r; // pin = 3
    r = r && WhiteSpace(b, l + 1);
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

  // Table | Story
  private static boolean root_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Table(b, l + 1);
    if (!r) r = Story(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  final static Parser RecoverMeta_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return RecoverMeta(b, l + 1);
    }
  };
}
