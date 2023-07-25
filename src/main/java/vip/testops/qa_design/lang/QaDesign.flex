package vip.testops.qa_design.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import vip.testops.qa_design.QaDesignBundle;import vip.testops.qa_design.lang.psi.QaDesignTypes;
import com.intellij.psi.TokenType;
%%

%class QaDesignLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

CRLF=[\r\n]
WHITE_SPACE=[ \t\f\r\n]
FIRST_VALUE_CHARACTER=[^ \n\f\r\n]
VALUE_CHARACTER=[^\n\f\\]
CONCAT_NEW_LINE = "\\"
END_OF_LINE_COMMENT="#"[^\r\n]*
SEPARATOR=[:]
KEY_CHARACTER=[^:\ \n\t\f\\\(\)\"]

%state WAITING_VALUE
%state WAITING_LINKED_METHOD
%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return QaDesignTypes.COMMENT; }
<YYINITIAL> @@link\({WHITE_SPACE}*\"                              { yybegin(WAITING_LINKED_METHOD); return QaDesignTypes.LEFT_BOUNDARY; }

<YYINITIAL> {KEY_CHARACTER}+                                {
                                                                yybegin(YYINITIAL);
                                                                String text = yytext().toString().trim();
                                                                if(text.equals(QaDesignBundle.message("keywords.qa_design.requirement"))){
                                                                    return QaDesignTypes.REQUIREMENT_KEY;
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
<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return QaDesignTypes.SEPARATOR; }
<YYINITIAL> {CONCAT_NEW_LINE}                               { yybegin(WAITING_VALUE); return QaDesignTypes.CONCAT_NEW_LINE; }
<WAITING_VALUE> {CONCAT_NEW_LINE}                           { yybegin(WAITING_VALUE); return QaDesignTypes.CONCAT_NEW_LINE; }
<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return QaDesignTypes.CONTENT; }

({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }


<WAITING_LINKED_METHOD> {
    \"{WHITE_SPACE}*\)                  { yybegin(YYINITIAL); return QaDesignTypes.RIGHT_BOUNDARY; }
    [^ \"\n\r\f\t\(\)]+                       { return QaDesignTypes.LINKED_METHOD_VALUE; }
    {WHITE_SPACE}+                         { return TokenType.BAD_CHARACTER; }
}
[^]                                                         { return TokenType.BAD_CHARACTER; }