// Generated by JFlex 1.9.1 http://jflex.de/  (tweaked for IntelliJ platform)
// source: QaDesign.flex

package vip.testops.qa_design.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import vip.testops.qa_design.QaDesignBundle;import vip.testops.qa_design.lang.psi.QaDesignTypes;
import com.intellij.psi.TokenType;

class QaDesignLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int WAITING_VALUE = 2;
  public static final int WAITING_LINKED_METHOD = 4;
  public static final int WAITING_TAG = 6;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3, 3
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
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
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\3\1\4\22\0\1\5"+
    "\1\0\1\6\1\7\4\0\1\10\1\11\2\0\1\12"+
    "\15\0\1\13\5\0\1\14\33\0\1\15\4\0\1\16"+
    "\5\0\1\17\1\0\1\20\1\0\1\21\1\22\1\0"+
    "\1\23\5\0\1\24\u018b\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
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
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\4\0\1\1\1\2\1\1\1\3\1\4\1\5\1\1"+
    "\1\6\1\7\2\10\1\6\1\11\1\3\1\12\1\2"+
    "\1\13\1\14\1\15\1\4\1\1\1\0\7\1\1\16"+
    "\1\0\1\17";

  private static int [] zzUnpackAction() {
    int [] result = new int[36];
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
    "\0\0\0\25\0\52\0\77\0\124\0\151\0\176\0\223"+
    "\0\250\0\223\0\275\0\223\0\322\0\347\0\374\0\322"+
    "\0\u0111\0\u0126\0\u013b\0\u0150\0\223\0\223\0\223\0\u0165"+
    "\0\u017a\0\u0126\0\u018f\0\u01a4\0\u01b9\0\u01ce\0\u01e3\0\u01f8"+
    "\0\u020d\0\u0222\0\u0237\0\223";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[36];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\5\3\6\1\7\1\6\1\10\1\11\2\10\1\5"+
    "\1\12\1\13\1\14\7\5\1\15\1\16\4\17\7\15"+
    "\1\20\7\15\1\21\5\6\1\22\1\21\2\10\13\21"+
    "\1\23\5\24\1\25\1\23\1\10\1\26\1\27\12\23"+
    "\1\5\3\0\1\5\2\0\1\5\2\0\1\5\1\0"+
    "\1\5\1\0\7\5\1\0\5\6\17\0\1\5\3\6"+
    "\1\7\1\6\1\0\1\5\2\0\1\5\1\0\1\5"+
    "\1\0\7\5\25\0\1\11\1\30\1\0\1\30\1\5"+
    "\2\30\1\11\2\30\1\11\1\30\1\11\1\30\7\11"+
    "\1\5\3\0\1\5\2\0\1\5\2\0\1\5\1\0"+
    "\1\31\1\0\7\5\2\15\2\0\11\15\1\0\10\15"+
    "\1\16\2\17\2\16\7\15\1\0\7\15\1\0\5\17"+
    "\17\0\1\21\6\0\1\21\2\0\13\21\1\0\5\32"+
    "\3\0\1\26\13\0\1\23\6\0\1\23\3\0\12\23"+
    "\1\0\5\24\3\0\1\26\13\0\2\30\1\0\1\30"+
    "\1\0\20\30\1\5\3\0\1\5\2\0\1\5\2\0"+
    "\1\5\1\0\1\5\1\0\4\5\1\33\1\5\1\34"+
    "\1\5\3\0\1\5\2\0\1\5\2\0\1\5\1\0"+
    "\1\5\1\0\2\5\1\35\5\5\3\0\1\5\2\0"+
    "\1\5\2\0\1\5\1\0\1\5\1\0\1\36\7\5"+
    "\3\0\1\5\2\0\1\5\2\0\1\5\1\0\1\5"+
    "\1\0\5\5\1\37\2\5\3\0\1\5\2\0\1\5"+
    "\2\0\1\5\1\0\1\5\1\0\1\5\1\40\6\5"+
    "\3\0\1\5\2\0\1\5\2\0\1\5\1\0\1\5"+
    "\1\0\3\5\1\41\4\5\3\0\1\5\2\0\1\5"+
    "\1\42\1\0\1\5\1\0\1\5\1\0\10\5\3\0"+
    "\1\5\2\0\1\5\1\43\1\0\1\5\1\0\1\5"+
    "\1\0\7\5\1\0\5\42\20\0\5\43\1\44\16\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[588];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
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

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\4\0\3\1\1\11\1\1\1\11\1\1\1\11\10\1"+
    "\3\11\2\1\1\0\10\1\1\0\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[36];
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

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  protected int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  private boolean zzEOFDone;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  QaDesignLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
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
   * Returns the character at position {@code pos} from the
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
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
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
    
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
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
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            zzDoEOF();
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { yybegin(YYINITIAL);
                                                                String text = yytext().toString().trim();
                                                                if(text.equals(QaDesignBundle.message("keywords.qa_design.feature"))){
                                                                    return QaDesignTypes.FEATURE;
                                                                }
                                                                else if (text.equals(QaDesignBundle.message("keywords.qa_design.test_point"))){
                                                                    return QaDesignTypes.TEST_POINT_KEY;
                                                                }
                                                                else if (text.equals(QaDesignBundle.message("keywords.qa_design.testcase"))){
                                                                    return QaDesignTypes.TEST_CASE_NAME_KEY;
                                                                }
                                                                else if (text.equals(QaDesignBundle.message("keywords.qa_design.testcase.desc"))){
                                                                    return QaDesignTypes.TEST_CASE_DESC_KEY;
                                                                }
                                                                else if (text.equals(QaDesignBundle.message("keywords.qa_design.testcase.data"))){
                                                                    return QaDesignTypes.TEST_CASE_DATA_KEY;
                                                                }
                                                                else if (text.equals(QaDesignBundle.message("keywords.qa_design.testcase.step"))){
                                                                    return QaDesignTypes.TEST_CASE_STEP_KEY;
                                                                }
                                                                else if (text.equals(QaDesignBundle.message("keywords.qa_design.testcase.expect"))){
                                                                    return QaDesignTypes.TEST_CASE_EXPECT_KEY;
                                                                }
                                                                //else if (text.equals("@@"+QaDesignBundle.message("keywords.qa_design.link"))){
                                                                   // return QaDesignTypes.LINKED_METHOD_KEY;
                                                                //}
                                                                else {
                                                                    return QaDesignTypes.INSIDE;
                                                                }
            }
          // fall through
          case 16: break;
          case 2:
            { yybegin(YYINITIAL); return TokenType.WHITE_SPACE;
            }
          // fall through
          case 17: break;
          case 3:
            { return TokenType.BAD_CHARACTER;
            }
          // fall through
          case 18: break;
          case 4:
            { yybegin(YYINITIAL); return QaDesignTypes.COMMENT;
            }
          // fall through
          case 19: break;
          case 5:
            { yybegin(WAITING_VALUE); return QaDesignTypes.SEPARATOR;
            }
          // fall through
          case 20: break;
          case 6:
            { yybegin(WAITING_VALUE); return QaDesignTypes.CONCAT_NEW_LINE;
            }
          // fall through
          case 21: break;
          case 7:
            { yybegin(YYINITIAL); return QaDesignTypes.CONTENT;
            }
          // fall through
          case 22: break;
          case 8:
            { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE;
            }
          // fall through
          case 23: break;
          case 9:
            { return QaDesignTypes.LINKED_METHOD_VALUE;
            }
          // fall through
          case 24: break;
          case 10:
            { return QaDesignTypes.TAG_VALUE;
            }
          // fall through
          case 25: break;
          case 11:
            { return QaDesignTypes.DOUBLE_QUOTE;
            }
          // fall through
          case 26: break;
          case 12:
            { yybegin(YYINITIAL); return QaDesignTypes.RIGHT_BOUNDARY;
            }
          // fall through
          case 27: break;
          case 13:
            { return QaDesignTypes.COMMA;
            }
          // fall through
          case 28: break;
          case 14:
            { yybegin(WAITING_TAG); return QaDesignTypes.LEFT_BOUNDARY_TAG;
            }
          // fall through
          case 29: break;
          case 15:
            { yybegin(WAITING_LINKED_METHOD); return QaDesignTypes.LEFT_BOUNDARY_LINK;
            }
          // fall through
          case 30: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
