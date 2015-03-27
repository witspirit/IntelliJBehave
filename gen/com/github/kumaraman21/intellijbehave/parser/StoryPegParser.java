// This is a generated file. Not intended for manual editing.
package com.github.kumaraman21.intellijbehave.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

import static com.github.kumaraman21.intellijbehave.parser.IStoryPegElementType.*;
import static com.github.kumaraman21.intellijbehave.peg.StoryPegParserUtil.*;

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
    if (t == STORY_DESCRIPTION) {
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
    else if (t == STORY_INJECT_IDENTIFIER) {
      r = InjectIdentifier(b, 0);
    }
    else if (t == STORY_INJECT_SEPARATOR) {
      r = InjectSeparator(b, 0);
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
    else if (t == STORY_STEP_ARGUMENT) {
      r = StepArgument(b, 0);
    }
    else if (t == STORY_STEP_COMMENT) {
      r = StepComment(b, 0);
    }
    else if (t == STORY_STEP_LINE) {
      r = StepLine(b, 0);
    }
    else if (t == STORY_STEP_PAR) {
      r = StepPar(b, 0);
    }
    else if (t == STORY_STEP_POST_PARAMETER) {
      r = StepPostParameter(b, 0);
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
    else if (t == STORY_URI_IDENTIFIER) {
      r = UriIdentifier(b, 0);
    }
    else if (t == STORY_URI_WORD) {
      r = UriWord(b, 0);
    }
    else if (t == STORY_USER_INJECT) {
      r = UserInject(b, 0);
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
  // TOKEN_WORD|TOKEN_NUMBER
  static boolean Alnum(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Alnum")) return false;
    if (!nextTokenIs(b, "", STORY_TOKEN_NUMBER, STORY_TOKEN_WORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_WORD);
    if (!r) r = consumeToken(b, STORY_TOKEN_NUMBER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_WHEN|TOKEN_THEN|TOKEN_GIVEN|TOKEN_AND|StoryPath|Line|TOKEN_PIPE|Punct|Space
  static boolean Anything(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Anything")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_WHEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_THEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_AND);
    if (!r) r = StoryPath(b, l + 1);
    if (!r) r = Line(b, l + 1);
    if (!r) r = consumeToken(b, STORY_TOKEN_PIPE);
    if (!r) r = Punct(b, l + 1);
    if (!r) r = Space(b, l + 1);
    exit_section_(b, m, null, r);
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
  // TOKEN_BRACKET_OPEN SpaceStar InjectIdentifier SpaceStar [TOKEN_PIPE SpaceStar InjectIdentifier SpaceStar] TOKEN_BRACKET_CLOSE
  public static boolean Inject(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Inject")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_BRACKET_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_BRACKET_OPEN);
    r = r && SpaceStar(b, l + 1);
    r = r && InjectIdentifier(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    r = r && Inject_4(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_BRACKET_CLOSE);
    exit_section_(b, m, STORY_INJECT, r);
    return r;
  }

  // [TOKEN_PIPE SpaceStar InjectIdentifier SpaceStar]
  private static boolean Inject_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Inject_4")) return false;
    Inject_4_0(b, l + 1);
    return true;
  }

  // TOKEN_PIPE SpaceStar InjectIdentifier SpaceStar
  private static boolean Inject_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Inject_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PIPE);
    r = r && SpaceStar(b, l + 1);
    r = r && InjectIdentifier(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Word ((InjectSeparator|Space)+ Word)* InjectSeparator?
  public static boolean InjectIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectIdentifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<inject identifier>");
    r = Word(b, l + 1);
    r = r && InjectIdentifier_1(b, l + 1);
    r = r && InjectIdentifier_2(b, l + 1);
    exit_section_(b, l, m, STORY_INJECT_IDENTIFIER, r, false, null);
    return r;
  }

  // ((InjectSeparator|Space)+ Word)*
  private static boolean InjectIdentifier_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectIdentifier_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!InjectIdentifier_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InjectIdentifier_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (InjectSeparator|Space)+ Word
  private static boolean InjectIdentifier_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectIdentifier_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = InjectIdentifier_1_0_0(b, l + 1);
    r = r && Word(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (InjectSeparator|Space)+
  private static boolean InjectIdentifier_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectIdentifier_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = InjectIdentifier_1_0_0_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!InjectIdentifier_1_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InjectIdentifier_1_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // InjectSeparator|Space
  private static boolean InjectIdentifier_1_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectIdentifier_1_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = InjectSeparator(b, l + 1);
    if (!r) r = Space(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // InjectSeparator?
  private static boolean InjectIdentifier_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectIdentifier_2")) return false;
    InjectSeparator(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (TOKEN_COMMA|TOKEN_COLON|TOKEN_AMPERSAND|TOKEN_PUNCT|TOKEN_DOT)+
  public static boolean InjectSeparator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectSeparator")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<inject separator>");
    r = InjectSeparator_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!InjectSeparator_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "InjectSeparator", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, STORY_INJECT_SEPARATOR, r, false, null);
    return r;
  }

  // TOKEN_COMMA|TOKEN_COLON|TOKEN_AMPERSAND|TOKEN_PUNCT|TOKEN_DOT
  private static boolean InjectSeparator_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "InjectSeparator_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_COMMA);
    if (!r) r = consumeToken(b, STORY_TOKEN_COLON);
    if (!r) r = consumeToken(b, STORY_TOKEN_AMPERSAND);
    if (!r) r = consumeToken(b, STORY_TOKEN_PUNCT);
    if (!r) r = consumeToken(b, STORY_TOKEN_DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_IP | Alnum (TOKEN_DOT Alnum)+
  public static boolean IpAddress(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<ip address>");
    r = consumeToken(b, STORY_TOKEN_IP);
    if (!r) r = IpAddress_1(b, l + 1);
    exit_section_(b, l, m, STORY_IP_ADDRESS, r, false, null);
    return r;
  }

  // Alnum (TOKEN_DOT Alnum)+
  private static boolean IpAddress_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Alnum(b, l + 1);
    r = r && IpAddress_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (TOKEN_DOT Alnum)+
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

  // TOKEN_DOT Alnum
  private static boolean IpAddress_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "IpAddress_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_DOT);
    r = r && Alnum(b, l + 1);
    exit_section_(b, m, null, r);
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
  // Word (WordSeparator Word)* Punct?
  static boolean Line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Word(b, l + 1);
    r = r && Line_1(b, l + 1);
    r = r && Line_2(b, l + 1);
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

  // Punct?
  private static boolean Line_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Line_2")) return false;
    Punct(b, l + 1);
    return true;
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
  // TOKEN_WORD TOKEN_COLON
  public static boolean MetaKey(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaKey")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_WORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, STORY_TOKEN_WORD, STORY_TOKEN_COLON);
    exit_section_(b, m, STORY_META_KEY, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_META (Space MetaElement)? Newline (MetaElement (Newline))*
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

  // (MetaElement (Newline))*
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

  // MetaElement (Newline)
  private static boolean MetaStatement_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = MetaElement(b, l + 1);
    r = r && MetaStatement_3_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (Newline)
  private static boolean MetaStatement_3_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaStatement_3_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (Punct|Word)+
  public static boolean MetaValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaValue")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<meta value>");
    r = MetaValue_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!MetaValue_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "MetaValue", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, STORY_META_VALUE, r, false, null);
    return r;
  }

  // Punct|Word
  private static boolean MetaValue_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MetaValue_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Punct(b, l + 1);
    if (!r) r = Word(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (SpaceStar Line SpaceStar Newline)+
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

  // SpaceStar Line SpaceStar Newline
  private static boolean MultiTextLine_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "MultiTextLine_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpaceStar(b, l + 1);
    r = r && Line(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    r = r && Newline(b, l + 1);
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
  // TOKEN_NEWLINE | <<eof>>
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
  // TOKEN_PUNCT | TOKEN_BRACKET_CLOSE|TOKEN_BRACKET_OPEN|TOKEN_COMMA|TOKEN_COLON|TOKEN_AMPERSAND
  static boolean Punct(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Punct")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PUNCT);
    if (!r) r = consumeToken(b, STORY_TOKEN_BRACKET_CLOSE);
    if (!r) r = consumeToken(b, STORY_TOKEN_BRACKET_OPEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_COMMA);
    if (!r) r = consumeToken(b, STORY_TOKEN_COLON);
    if (!r) r = consumeToken(b, STORY_TOKEN_AMPERSAND);
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
  // TOKEN_SCENARIO SpaceStar (ScenarioTitle Newline)? MetaStatement? GivenStories? ((Step |StepComment)+ Examples?)*
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
    r = p && Scenario_5(b, l + 1) && r;
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

  // ((Step |StepComment)+ Examples?)*
  private static boolean Scenario_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Scenario_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Scenario_5", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (Step |StepComment)+ Examples?
  private static boolean Scenario_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario_5_0_0(b, l + 1);
    r = r && Scenario_5_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (Step |StepComment)+
  private static boolean Scenario_5_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario_5_0_0_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Scenario_5_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Scenario_5_0_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Step |StepComment
  private static boolean Scenario_5_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Step(b, l + 1);
    if (!r) r = StepComment(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Examples?
  private static boolean Scenario_5_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Scenario_5_0_1")) return false;
    Examples(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Line
  public static boolean ScenarioTitle(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ScenarioTitle")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<scenario title>");
    r = Line(b, l + 1);
    exit_section_(b, l, m, STORY_SCENARIO_TITLE, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Uri|IpAddress|Alnum|UserInject|Inject|StoryPath
  static boolean SimpleWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "SimpleWord")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Uri(b, l + 1);
    if (!r) r = IpAddress(b, l + 1);
    if (!r) r = Alnum(b, l + 1);
    if (!r) r = UserInject(b, l + 1);
    if (!r) r = Inject(b, l + 1);
    if (!r) r = StoryPath(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_SPACE
  static boolean Space(PsiBuilder b, int l) {
    return consumeToken(b, STORY_TOKEN_SPACE);
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
  // StepPar SpaceStar (StepArgument)?
  public static boolean Step(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<step>");
    r = StepPar(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, SpaceStar(b, l + 1));
    r = p && Step_2(b, l + 1) && r;
    exit_section_(b, l, m, STORY_STEP, r, p, RecoverStep_parser_);
    return r || p;
  }

  // (StepArgument)?
  private static boolean Step_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step_2")) return false;
    Step_2_0(b, l + 1);
    return true;
  }

  // (StepArgument)
  private static boolean Step_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Step_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = StepArgument(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // StepLine (Newline|Space)? StepPostParameter? Newline?
  public static boolean StepArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step argument>");
    r = StepLine(b, l + 1);
    r = r && StepArgument_1(b, l + 1);
    r = r && StepArgument_2(b, l + 1);
    r = r && StepArgument_3(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_ARGUMENT, r, false, null);
    return r;
  }

  // (Newline|Space)?
  private static boolean StepArgument_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_1")) return false;
    StepArgument_1_0(b, l + 1);
    return true;
  }

  // Newline|Space
  private static boolean StepArgument_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Newline(b, l + 1);
    if (!r) r = Space(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // StepPostParameter?
  private static boolean StepArgument_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_2")) return false;
    StepPostParameter(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean StepArgument_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepArgument_3")) return false;
    Newline(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_COMMENT Anything* Newline
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

  // Anything*
  private static boolean StepComment_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepComment_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!Anything(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "StepComment_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // StepWord (WordSeparator StepWord)* Punct?
  public static boolean StepLine(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step line>");
    r = StepWord(b, l + 1);
    r = r && StepLine_1(b, l + 1);
    r = r && StepLine_2(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_LINE, r, false, null);
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

  // Punct?
  private static boolean StepLine_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepLine_2")) return false;
    Punct(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TOKEN_WHEN|TOKEN_GIVEN|TOKEN_THEN|TOKEN_AND
  public static boolean StepPar(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepPar")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step par>");
    r = consumeToken(b, STORY_TOKEN_WHEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_GIVEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_THEN);
    if (!r) r = consumeToken(b, STORY_TOKEN_AND);
    exit_section_(b, l, m, STORY_STEP_PAR, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Table| StoryPath
  public static boolean StepPostParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepPostParameter")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<step post parameter>");
    r = Table(b, l + 1);
    if (!r) r = StoryPath(b, l + 1);
    exit_section_(b, l, m, STORY_STEP_POST_PARAMETER, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Uri|IpAddress|Alnum|UserInject|Inject
  static boolean StepSimpleWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "StepSimpleWord")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Uri(b, l + 1);
    if (!r) r = IpAddress(b, l + 1);
    if (!r) r = Alnum(b, l + 1);
    if (!r) r = UserInject(b, l + 1);
    if (!r) r = Inject(b, l + 1);
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
  // Newline? Description? Newline? MetaStatement? Newline? Narrative? Newline? GivenStories? Newline? Lifecycle? Newline? Scenario+ Newline?
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
    r = r && Story_6(b, l + 1);
    r = r && Story_7(b, l + 1);
    r = r && Story_8(b, l + 1);
    r = r && Story_9(b, l + 1);
    r = r && Story_10(b, l + 1);
    r = r && Story_11(b, l + 1);
    r = r && Story_12(b, l + 1);
    exit_section_(b, l, m, STORY_STORY, r, false, null);
    return r;
  }

  // Newline?
  private static boolean Story_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_0")) return false;
    Newline(b, l + 1);
    return true;
  }

  // Description?
  private static boolean Story_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_1")) return false;
    Description(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean Story_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_2")) return false;
    Newline(b, l + 1);
    return true;
  }

  // MetaStatement?
  private static boolean Story_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_3")) return false;
    MetaStatement(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean Story_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_4")) return false;
    Newline(b, l + 1);
    return true;
  }

  // Narrative?
  private static boolean Story_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_5")) return false;
    Narrative(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean Story_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_6")) return false;
    Newline(b, l + 1);
    return true;
  }

  // GivenStories?
  private static boolean Story_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_7")) return false;
    GivenStories(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean Story_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_8")) return false;
    Newline(b, l + 1);
    return true;
  }

  // Lifecycle?
  private static boolean Story_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_9")) return false;
    Lifecycle(b, l + 1);
    return true;
  }

  // Newline?
  private static boolean Story_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_10")) return false;
    Newline(b, l + 1);
    return true;
  }

  // Scenario+
  private static boolean Story_11(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_11")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Scenario(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Scenario(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Story_11", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Newline?
  private static boolean Story_12(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Story_12")) return false;
    Newline(b, l + 1);
    return true;
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
  // (SpaceStar TableRow Newline)+
  public static boolean Table(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Table")) return false;
    if (!nextTokenIs(b, "<table>", STORY_TOKEN_PIPE, STORY_TOKEN_SPACE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<table>");
    r = Table_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!Table_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Table", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, STORY_TABLE, r, false, null);
    return r;
  }

  // SpaceStar TableRow Newline
  private static boolean Table_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Table_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = SpaceStar(b, l + 1);
    r = r && TableRow(b, l + 1);
    r = r && Newline(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Line
  public static boolean TableCell(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableCell")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<table cell>");
    r = Line(b, l + 1);
    exit_section_(b, l, m, STORY_TABLE_CELL, r, false, null);
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
  // TOKEN_PIPE (SpaceStar TableCell SpaceStar | TableCellEmpty) TOKEN_PIPE ((SpaceStar TableCell SpaceStar | TableCellEmpty) TOKEN_PIPE)*
  public static boolean TableRow(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TableRow")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_PIPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PIPE);
    r = r && TableRow_1(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_PIPE);
    r = r && TableRow_3(b, l + 1);
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
  // Alnum TOKEN_COLON Word TOKEN_AT UriIdentifier (TOKEN_COLON Alnum)?
  public static boolean Uri(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Uri")) return false;
    if (!nextTokenIs(b, "<uri>", STORY_TOKEN_NUMBER, STORY_TOKEN_WORD)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, "<uri>");
    r = Alnum(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_COLON);
    r = r && Word(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_AT);
    r = r && UriIdentifier(b, l + 1);
    p = r; // pin = 5
    r = r && Uri_5(b, l + 1);
    exit_section_(b, l, m, STORY_URI, r, p, null);
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
    r = consumeToken(b, STORY_TOKEN_COLON);
    r = r && Alnum(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IpAddress| (UriWord|TOKEN_DOT)+
  public static boolean UriIdentifier(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriIdentifier")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<uri identifier>");
    r = IpAddress(b, l + 1);
    if (!r) r = UriIdentifier_1(b, l + 1);
    exit_section_(b, l, m, STORY_URI_IDENTIFIER, r, false, null);
    return r;
  }

  // (UriWord|TOKEN_DOT)+
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

  // UriWord|TOKEN_DOT
  private static boolean UriIdentifier_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriIdentifier_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = UriWord(b, l + 1);
    if (!r) r = consumeToken(b, STORY_TOKEN_DOT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Alnum|UserInject|Inject
  public static boolean UriWord(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UriWord")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, "<uri word>");
    r = Alnum(b, l + 1);
    if (!r) r = UserInject(b, l + 1);
    if (!r) r = Inject(b, l + 1);
    exit_section_(b, l, m, STORY_URI_WORD, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TOKEN_DBRACKET_OPEN SpaceStar InjectIdentifier SpaceStar [TOKEN_PIPE SpaceStar InjectIdentifier SpaceStar] TOKEN_DBRACKET_CLOSE
  public static boolean UserInject(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UserInject")) return false;
    if (!nextTokenIs(b, STORY_TOKEN_DBRACKET_OPEN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_DBRACKET_OPEN);
    r = r && SpaceStar(b, l + 1);
    r = r && InjectIdentifier(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    r = r && UserInject_4(b, l + 1);
    r = r && consumeToken(b, STORY_TOKEN_DBRACKET_CLOSE);
    exit_section_(b, m, STORY_USER_INJECT, r);
    return r;
  }

  // [TOKEN_PIPE SpaceStar InjectIdentifier SpaceStar]
  private static boolean UserInject_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UserInject_4")) return false;
    UserInject_4_0(b, l + 1);
    return true;
  }

  // TOKEN_PIPE SpaceStar InjectIdentifier SpaceStar
  private static boolean UserInject_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UserInject_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STORY_TOKEN_PIPE);
    r = r && SpaceStar(b, l + 1);
    r = r && InjectIdentifier(b, l + 1);
    r = r && SpaceStar(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // (Punct|Space)+
  static boolean WordSeparator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WordSeparator")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = WordSeparator_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!WordSeparator_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "WordSeparator", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // Punct|Space
  private static boolean WordSeparator_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "WordSeparator_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Punct(b, l + 1);
    if (!r) r = Space(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // !<<eof>> (Story|Table)
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null);
    r = root_0(b, l + 1);
    p = r; // pin = 1
    r = r && root_1(b, l + 1);
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

  // Story|Table
  private static boolean root_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Story(b, l + 1);
    if (!r) r = Table(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  final static Parser RecoverStep_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return RecoverStep(b, l + 1);
    }
  };
}
