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
WHITE_SPACE=[ \t\f]
//INDENT=[\ ]{2}|[\t]
FIRST_VALUE_CHARACTER=[^ \n\f\\]| "\\"{CRLF} | "\\".
VALUE_CHARACTER=[^\n\f\\]| "\\"{CRLF} | "\\".
END_OF_LINE_COMMENT="#"[^\r\n]*
//SUB_REQUIRMENT_KEY=需求子条目
//TEST_POINT_KEY=测试要点
//TEST_CASE_KEY=测试案例
//TEST_CASE_NAME_KEY=案例名称
//TEST_CASE_DESC_KEY=案例描述
//TEST_CASE_DATA_KEY=测试数据
//TEST_CASE_STEP_KEY=测试步骤
//TEST_CASE_EXPECT_KEY=预期结果
SEPARATOR=[:]
KEY_CHARACTER=[^:\ \n\t\f\\]
//KEY_WORD="需求子条目"|"测试要点"|"测试案例"|"案例名称"|"案例名称"|"案例描述"|"测试数据"|"测试步骤"|"预期结果"

%state WAITING_VALUE

%%

<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return QaDesignTypes.COMMENT; }
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
                                                                } else {
                                                                    return QaDesignTypes.INSIDE;
                                                                }
                                                            }
//{SUB_REQUIRMENT_KEY}                            { return QaDesignTypes.SUB_REQUIRMENT_KEY; }
//<YYINITIAL> {TEST_POINT_KEY}                                { yybegin(YYINITIAL); return QaDesignTypes.TEST_POINT_KEY; }
//<YYINITIAL> {TEST_CASE_KEY}                                 { yybegin(YYINITIAL); return QaDesignTypes.TEST_CASE_KEY; }
//<YYINITIAL> {TEST_CASE_NAME_KEY}                            { yybegin(YYINITIAL); return QaDesignTypes.TEST_CASE_NAME_KEY; }
//<YYINITIAL> {TEST_CASE_DESC_KEY}                            { yybegin(YYINITIAL); return QaDesignTypes.TEST_CASE_DESC_KEY; }
//<YYINITIAL> {TEST_CASE_DATA_KEY}                            { yybegin(YYINITIAL); return QaDesignTypes.TEST_CASE_DATA_KEY; }
//<YYINITIAL> {TEST_CASE_STEP_KEY}                            { yybegin(YYINITIAL); return QaDesignTypes.TEST_CASE_STEP_KEY; }
//<YYINITIAL> {TEST_CASE_EXPECT_KEY}                          { yybegin(YYINITIAL); return QaDesignTypes.TEST_CASE_EXPECT_KEY; }
<YYINITIAL> {SEPARATOR}                                     { yybegin(WAITING_VALUE); return QaDesignTypes.SEPARATOR; }
<WAITING_VALUE> {CRLF}({CRLF}|{WHITE_SPACE})+               { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {WHITE_SPACE}+                              { yybegin(WAITING_VALUE); return TokenType.WHITE_SPACE; }
<WAITING_VALUE> {FIRST_VALUE_CHARACTER}{VALUE_CHARACTER}*   { yybegin(YYINITIAL); return QaDesignTypes.CONTENT; }
({CRLF}|{WHITE_SPACE})+                                     { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
[^]                                                         { return TokenType.BAD_CHARACTER; }