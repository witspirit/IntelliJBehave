/* The following code was generated by JFlex 1.4.3 on 28.01.15 11:59 */

package com.github.kumaraman21.intellijbehave.highlighter;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import java.util.Stack;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 28.01.15 11:59 from the specification file
 * <tt>C:/Users/debritod/Work/Intellij-Idea/IntelliJBehave/src/com/github/kumaraman21/intellijbehave/highlighter/Story.flex</tt>
 */
class _StoryLexer implements FlexLexer {
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int IN_META = 14;
  public static final int IN_GIVEN = 8;
  public static final int IN_DIRECTIVE = 2;
  public static final int IN_THEN = 12;
  public static final int YYINITIAL = 0;
  public static final int IN_EXAMPLES = 18;
  public static final int IN_SCENARIO = 6;
  public static final int IN_WHEN = 10;
  public static final int IN_TABLE = 16;
  public static final int IN_STORY = 4;
  public static final int IN_GIVENSTORIES = 20;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9,  9, 10, 10
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\17\1\37\13\0"+
    "\1\40\14\0\1\16\5\0\1\5\1\35\3\0\1\22\1\0\1\30"+
    "\5\0\1\20\5\0\1\6\1\34\2\0\1\32\11\0\1\12\1\0"+
    "\1\7\1\36\1\10\2\0\1\33\1\14\2\0\1\26\1\24\1\11"+
    "\1\15\1\25\1\0\1\13\1\27\1\21\1\0\1\31\1\0\1\23"+
    "\3\0\1\4\uff83\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\13\0\1\1\2\2\10\1\1\3\1\4\1\5\7\3"+
    "\2\4\1\6\2\4\1\3\2\4\3\3\1\7\1\10"+
    "\1\11\2\12\1\13\1\4\1\14\1\15\7\1\7\0"+
    "\1\15\11\0\2\16\3\0\6\1\6\0\1\17\10\0"+
    "\5\20\3\0\4\1\15\0\1\16\6\0\3\1\1\0"+
    "\1\21\2\0\1\22\1\23\12\0\3\1\3\0\1\24"+
    "\4\0\6\16\3\1\7\0\5\25\5\26\5\27\2\1"+
    "\20\0\1\1\1\0\1\30\12\0\1\31\7\0\1\32";

  private static int [] zzUnpackAction() {
    int [] result = new int[235];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\41\0\102\0\143\0\204\0\245\0\306\0\347"+
    "\0\u0108\0\u0129\0\u014a\0\u016b\0\u018c\0\u01ad\0\u01ce\0\u01ef"+
    "\0\u0210\0\u0231\0\u0252\0\u0273\0\u0294\0\u02b5\0\u01ad\0\u02d6"+
    "\0\u01ad\0\u02f7\0\u0318\0\u0339\0\u035a\0\u037b\0\u039c\0\u03bd"+
    "\0\u03de\0\u03ff\0\u0420\0\u0441\0\u0462\0\u0483\0\u04a4\0\u04c5"+
    "\0\u04e6\0\u0507\0\u0528\0\u0549\0\u056a\0\u058b\0\u05ac\0\u01ad"+
    "\0\u01ad\0\u01ad\0\u05cd\0\u01ce\0\u05ee\0\u060f\0\u0630\0\u0651"+
    "\0\u0672\0\u0693\0\u06b4\0\u06d5\0\u06f6\0\u0717\0\u0738\0\u0759"+
    "\0\u077a\0\u079b\0\u01ad\0\u07bc\0\u07dd\0\u07fe\0\u081f\0\u0840"+
    "\0\u0861\0\u0882\0\u08a3\0\u0483\0\u08c4\0\u08e5\0\u0906\0\u0927"+
    "\0\u0948\0\u0969\0\u098a\0\u09ab\0\u09cc\0\u09ed\0\u0a0e\0\u0a2f"+
    "\0\u0a50\0\u0a71\0\u0a92\0\u0ab3\0\u0ad4\0\u0af5\0\u0b16\0\u0b37"+
    "\0\u0b58\0\u0b79\0\u0b9a\0\u0bbb\0\u0bdc\0\u0bfd\0\u01ad\0\u0c1e"+
    "\0\u0c3f\0\u0c60\0\u0c81\0\u0ca2\0\u0cc3\0\u0ce4\0\u0d05\0\u0d26"+
    "\0\u0d47\0\u0d68\0\u0d89\0\u0daa\0\u0dcb\0\u0dec\0\u0e0d\0\u0e2e"+
    "\0\u0e4f\0\u0e70\0\u0e91\0\u0eb2\0\u0ed3\0\u0ef4\0\u0f15\0\u01ad"+
    "\0\u0f36\0\u0f57\0\u0f78\0\u0f99\0\u0fba\0\u0fdb\0\u0ffc\0\u101d"+
    "\0\u103e\0\u105f\0\u01ad\0\u1080\0\u10a1\0\u01ad\0\u01ad\0\u10c2"+
    "\0\u10e3\0\u1104\0\u1125\0\u1146\0\u1167\0\u0c1e\0\u1188\0\u11a9"+
    "\0\u11ca\0\u11eb\0\u120c\0\u122d\0\u124e\0\u126f\0\u1290\0\u01ad"+
    "\0\u12b1\0\u12d2\0\u12f3\0\u1314\0\u1335\0\u1356\0\u1377\0\u1398"+
    "\0\u13b9\0\u13da\0\u13fb\0\u141c\0\u143d\0\u145e\0\u147f\0\u14a0"+
    "\0\u14c1\0\u14e2\0\u1503\0\u1524\0\u01ad\0\u0c1e\0\u1545\0\u1566"+
    "\0\u1587\0\u01ad\0\u0c1e\0\u15a8\0\u15c9\0\u15ea\0\u01ad\0\u0c1e"+
    "\0\u160b\0\u162c\0\u164d\0\u166e\0\u168f\0\u16b0\0\u16d1\0\u16f2"+
    "\0\u1713\0\u1734\0\u1755\0\u1776\0\u1797\0\u17b8\0\u17d9\0\u17fa"+
    "\0\u181b\0\u183c\0\u185d\0\u187e\0\u189f\0\u18c0\0\u18e1\0\u01ad"+
    "\0\u1902\0\u1923\0\u1944\0\u1965\0\u1986\0\u19a7\0\u19c8\0\u19e9"+
    "\0\u1a0a\0\u1a2b\0\u01ad\0\u1a4c\0\u1a6d\0\u1a8e\0\u1aaf\0\u1ad0"+
    "\0\u1af1\0\u1b12\0\u01ad";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[235];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\14\1\15\1\16\1\14\1\17\1\14\1\20\11\14"+
    "\1\21\1\14\1\22\5\14\1\23\1\14\1\24\1\14"+
    "\1\24\1\25\1\14\1\26\1\14\1\27\3\30\1\31"+
    "\1\27\1\32\10\27\1\30\1\33\1\27\1\34\5\27"+
    "\1\35\1\27\1\36\1\27\1\37\2\27\1\40\1\27"+
    "\1\14\1\41\1\42\36\14\1\43\1\44\1\45\36\43"+
    "\1\46\1\47\1\50\32\46\1\51\4\46\1\47\1\50"+
    "\32\46\1\52\4\46\1\47\1\50\32\46\1\53\3\46"+
    "\1\54\1\44\1\45\2\54\1\55\33\54\1\56\1\57"+
    "\1\60\1\56\1\61\34\56\1\27\1\44\1\45\1\62"+
    "\13\27\1\62\21\27\1\63\1\44\1\45\36\63\1\14"+
    "\2\0\36\14\2\0\1\16\77\0\1\64\2\0\36\64"+
    "\1\14\2\0\4\14\1\65\32\14\2\0\5\14\1\66"+
    "\31\14\2\0\20\14\1\67\16\14\2\0\11\14\1\70"+
    "\25\14\2\0\30\14\1\71\6\14\2\0\6\14\1\72"+
    "\30\14\2\0\35\14\1\73\1\0\3\30\13\0\1\30"+
    "\30\0\1\74\41\0\1\75\53\0\1\76\31\0\1\77"+
    "\57\0\1\100\40\0\1\101\45\0\1\102\2\0\1\42"+
    "\1\0\1\103\1\0\1\104\11\0\1\105\1\0\1\106"+
    "\5\0\1\107\1\0\1\110\1\0\1\110\1\111\1\0"+
    "\1\112\5\0\1\103\1\0\1\104\11\0\1\105\1\0"+
    "\1\106\5\0\1\107\1\0\1\110\1\0\1\110\1\111"+
    "\1\0\1\112\1\0\1\43\2\0\36\43\2\0\1\45"+
    "\1\0\1\103\1\0\1\104\11\0\1\105\1\0\1\106"+
    "\5\0\1\113\1\0\1\110\1\0\1\110\1\111\1\0"+
    "\1\112\5\0\1\103\1\0\1\104\11\0\1\105\1\0"+
    "\1\106\5\0\1\113\1\0\1\110\1\0\1\110\1\111"+
    "\1\0\1\112\1\0\1\114\1\115\1\116\36\114\2\0"+
    "\1\50\1\0\1\103\1\0\1\104\11\0\1\105\1\0"+
    "\1\106\5\0\1\113\1\0\1\110\1\0\1\110\2\0"+
    "\1\112\5\0\1\103\1\0\1\104\11\0\1\105\1\0"+
    "\1\106\5\0\1\113\1\0\1\110\1\0\1\110\2\0"+
    "\1\112\1\0\1\114\1\115\1\116\6\114\1\117\30\114"+
    "\1\115\1\116\6\114\1\120\30\114\1\115\1\116\6\114"+
    "\1\121\27\114\1\54\2\0\2\54\1\0\33\54\1\55"+
    "\3\0\13\55\1\0\21\55\1\56\2\0\1\56\1\0"+
    "\34\56\2\0\1\60\36\0\1\63\2\0\36\63\1\14"+
    "\2\0\5\14\1\122\31\14\2\0\16\14\1\123\20\14"+
    "\2\0\7\14\1\124\27\14\2\0\26\14\1\125\10\14"+
    "\2\0\5\14\1\126\31\14\2\0\33\14\1\127\3\14"+
    "\2\0\35\14\1\17\10\0\1\130\51\0\1\131\31\0"+
    "\1\132\57\0\1\133\17\0\1\134\40\0\1\135\70\0"+
    "\1\136\7\0\1\137\41\0\1\140\53\0\1\141\31\0"+
    "\1\142\57\0\1\143\16\0\1\144\67\0\1\145\14\0"+
    "\1\146\24\0\1\147\1\0\1\116\1\147\1\150\23\147"+
    "\1\151\1\147\1\152\1\147\1\152\1\153\4\147\2\0"+
    "\1\147\1\150\23\147\1\151\1\147\1\152\1\147\1\152"+
    "\1\153\3\147\1\114\1\115\1\116\33\114\1\154\3\114"+
    "\1\115\1\116\33\114\1\155\3\114\1\115\1\116\33\114"+
    "\1\156\2\114\1\14\2\0\6\14\1\157\30\14\2\0"+
    "\7\14\1\160\27\14\2\0\21\14\1\161\15\14\2\0"+
    "\5\14\1\162\31\14\2\0\6\14\1\127\30\14\2\0"+
    "\14\14\1\17\21\14\11\0\1\163\41\0\1\164\52\0"+
    "\1\165\24\0\1\166\41\0\1\167\40\0\1\170\27\0"+
    "\1\136\2\0\36\136\10\0\1\171\51\0\1\172\31\0"+
    "\1\173\57\0\1\174\17\0\1\175\66\0\1\176\42\0"+
    "\1\103\31\0\1\177\26\0\1\200\35\0\1\201\57\0"+
    "\1\202\16\0\1\203\27\0\1\114\1\115\1\116\14\114"+
    "\1\204\22\114\1\115\1\116\14\114\1\205\22\114\1\115"+
    "\1\116\14\114\1\206\21\114\1\14\2\0\7\14\1\207"+
    "\27\14\2\0\13\14\1\17\23\14\2\0\22\14\1\210"+
    "\14\14\2\0\6\14\1\211\27\14\12\0\1\212\44\0"+
    "\1\213\47\0\1\214\24\0\1\215\46\0\1\216\40\0"+
    "\1\217\32\0\1\220\41\0\1\221\52\0\1\222\24\0"+
    "\1\223\41\0\1\176\46\0\1\103\31\0\1\224\61\0"+
    "\1\202\17\0\1\225\66\0\1\226\2\0\1\227\1\115"+
    "\1\116\36\227\1\230\1\115\1\116\36\230\1\231\1\115"+
    "\1\116\36\231\1\14\2\0\10\14\1\232\26\14\2\0"+
    "\23\14\1\233\13\14\2\0\3\14\1\234\10\14\1\17"+
    "\21\14\13\0\1\235\53\0\1\236\20\0\1\237\10\0"+
    "\1\240\33\0\1\241\44\0\1\103\47\0\1\242\24\0"+
    "\1\243\40\0\1\244\40\0\1\226\27\0\1\227\1\245"+
    "\1\246\36\227\1\230\1\247\1\250\36\230\1\231\1\251"+
    "\1\252\36\231\1\14\2\0\11\14\1\253\25\14\2\0"+
    "\5\14\1\254\31\14\2\0\16\14\1\255\17\14\14\0"+
    "\1\256\34\0\1\257\51\0\1\260\32\0\1\261\53\0"+
    "\1\262\20\0\1\263\10\0\1\103\27\0\1\264\10\0"+
    "\1\103\21\0\1\265\1\0\1\246\1\265\1\266\23\265"+
    "\1\267\1\265\1\270\1\265\1\270\1\271\4\265\2\0"+
    "\1\265\1\266\23\265\1\267\1\265\1\270\1\265\1\270"+
    "\1\271\3\265\1\272\1\0\1\250\1\272\1\273\23\272"+
    "\1\274\1\272\1\275\1\272\1\275\1\276\4\272\2\0"+
    "\1\272\1\273\23\272\1\274\1\272\1\275\1\272\1\275"+
    "\1\276\3\272\1\277\1\0\1\252\1\277\1\300\23\277"+
    "\1\301\1\277\1\302\1\277\1\302\1\303\4\277\2\0"+
    "\1\277\1\300\23\277\1\301\1\277\1\302\1\277\1\302"+
    "\1\303\3\277\1\14\2\0\12\14\1\304\24\14\2\0"+
    "\24\14\1\160\12\14\2\0\12\14\1\305\23\14\15\0"+
    "\1\306\52\0\1\307\26\0\1\310\37\0\1\311\34\0"+
    "\1\312\51\0\1\313\40\0\1\314\33\0\1\315\57\0"+
    "\1\316\16\0\1\317\43\0\1\320\57\0\1\321\16\0"+
    "\1\322\43\0\1\323\57\0\1\324\16\0\1\325\27\0"+
    "\1\14\2\0\13\14\1\127\23\14\2\0\10\14\1\326"+
    "\25\14\16\0\1\327\40\0\1\330\35\0\1\331\42\0"+
    "\1\332\52\0\1\221\26\0\1\333\40\0\1\334\54\0"+
    "\1\316\17\0\1\335\66\0\1\336\33\0\1\321\17\0"+
    "\1\337\66\0\1\340\33\0\1\324\17\0\1\341\66\0"+
    "\1\342\2\0\1\14\2\0\11\14\1\233\24\14\17\0"+
    "\1\343\35\0\1\344\42\0\1\176\35\0\1\345\40\0"+
    "\1\346\36\0\1\336\46\0\1\265\32\0\1\340\46\0"+
    "\1\272\32\0\1\342\46\0\1\277\31\0\1\347\44\0"+
    "\1\262\40\0\1\350\53\0\1\351\21\0\1\352\46\0"+
    "\1\353\51\0\1\332\11\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[6963];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  private static final char[] EMPTY_BUFFER = new char[0];
  private static final int YYEOF = -1;
  private static java.io.Reader zzReader = null; // Fake

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\13\0\2\1\1\11\10\1\1\11\1\1\1\11\26\1"+
    "\3\11\11\1\7\0\1\11\11\0\2\1\3\0\6\1"+
    "\6\0\1\1\10\0\1\11\4\1\3\0\4\1\15\0"+
    "\1\11\6\0\3\1\1\0\1\11\2\0\2\11\12\0"+
    "\3\1\3\0\1\11\4\0\11\1\7\0\1\11\4\1"+
    "\1\11\4\1\1\11\6\1\20\0\1\1\1\0\1\11"+
    "\12\0\1\11\7\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[235];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** this buffer may contains the current text array to be matched when it is cheap to acquire it */
  private char[] zzBufferArray;

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
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


  _StoryLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  _StoryLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 106) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart(){
    return zzStartRead;
  }

  public final int getTokenEnd(){
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end,int initialState){
    zzBuffer = buffer;
    zzBufferArray = com.intellij.util.text.CharArrayUtil.fromSequenceWithoutCopying(buffer);
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzPushbackPos = 0;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBufferArray != null ? zzBufferArray[zzStartRead+pos]:zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() {
    if (!zzEOFDone) {
      zzEOFDone = true;
        return;

    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;
    char[] zzBufferArrayL = zzBufferArray;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL)
            zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = (zzBufferArrayL != null ? zzBufferArrayL[zzCurrentPosL++] : zzBufferL.charAt(zzCurrentPosL++));
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 9: 
          { return StoryTokenType.TABLE_CELL;
          }
        case 27: break;
        case 18: 
          { yystatePopNPush(2, IN_WHEN);     currentStepStart = 0; return StoryTokenType.WHEN_TYPE;
          }
        case 28: break;
        case 15: 
          { yystatePop();                    return StoryTokenType.COMMENT;
          }
        case 29: break;
        case 8: 
          { return StoryTokenType.META_KEY;
          }
        case 30: break;
        case 2: 
          { yystatePush(IN_STORY); yypushback(yytext().length());
          }
        case 31: break;
        case 19: 
          { yystatePopNPush(2, IN_THEN);     currentStepStart = 0; return StoryTokenType.THEN_TYPE;
          }
        case 32: break;
        case 23: 
          { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.THEN_TYPE;
          }
        case 33: break;
        case 10: 
          { yystatePop(); yypushback(1);
          }
        case 34: break;
        case 5: 
          { yystatePopNPush(1, IN_TABLE);    return StoryTokenType.TABLE_DELIM;
          }
        case 35: break;
        case 21: 
          { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.GIVEN_TYPE;
          }
        case 36: break;
        case 26: 
          { yystatePopNPush(2, IN_GIVENSTORIES); return StoryTokenType.GIVEN_STORIES_TYPE;
          }
        case 37: break;
        case 13: 
          { yystatePush(IN_DIRECTIVE); yypushback(yytext().length());
          }
        case 38: break;
        case 22: 
          { yypushback(yytext().length() - 4); currentStepStart = 0; return StoryTokenType.WHEN_TYPE;
          }
        case 39: break;
        case 7: 
          { return StoryTokenType.META_TEXT;
          }
        case 40: break;
        case 11: 
          { return StoryTokenType.TABLE_DELIM;
          }
        case 41: break;
        case 20: 
          { yystatePopNPush(2, IN_GIVEN);    currentStepStart = 0; return StoryTokenType.GIVEN_TYPE;
          }
        case 42: break;
        case 6: 
          { return StoryTokenType.SCENARIO_TEXT;
          }
        case 43: break;
        case 24: 
          { yystatePopNPush(2, IN_EXAMPLES); return StoryTokenType.EXAMPLE_TYPE;
          }
        case 44: break;
        case 14: 
          { retrieveMultilineText(); return StoryTokenType.STEP_TEXT;
          }
        case 45: break;
        case 1: 
          { return StoryTokenType.STORY_DESCRIPTION;
          }
        case 46: break;
        case 3: 
          { return StoryTokenType.BAD_CHARACTER;
          }
        case 47: break;
        case 17: 
          { yystatePopNPush(2, IN_META);     return StoryTokenType.META;
          }
        case 48: break;
        case 25: 
          { yystatePopNPush(2, IN_SCENARIO); return StoryTokenType.SCENARIO_TYPE;
          }
        case 49: break;
        case 16: 
          { setStepStart();
          }
        case 50: break;
        case 12: 
          { return StoryTokenType.GIVEN_STORIES_TEXT;
          }
        case 51: break;
        case 4: 
          { return StoryTokenType.WHITE_SPACE;
          }
        case 52: break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            return null;
          }
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
