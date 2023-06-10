// This is a generated file. Not intended for manual editing.
package vip.testops.qa_design.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static vip.testops.qa_design.lang.psi.QaDesignTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class QaDesignParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return qaDesignFile(b, l + 1);
  }

  /* ********************************************************** */
  // INSIDE
  public static boolean fake_rule(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fake_rule")) return false;
    if (!nextTokenIs(b, INSIDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INSIDE);
    exit_section_(b, m, FAKE_RULE, r);
    return r;
  }

  /* ********************************************************** */
  // COMMENT|rule_first_line (rule_test_point_design)*|CRLF
  static boolean qaDesignFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qaDesignFile")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    if (!r) r = qaDesignFile_1(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  // rule_first_line (rule_test_point_design)*
  private static boolean qaDesignFile_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qaDesignFile_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rule_first_line(b, l + 1);
    r = r && qaDesignFile_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (rule_test_point_design)*
  private static boolean qaDesignFile_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qaDesignFile_1_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!qaDesignFile_1_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "qaDesignFile_1_1", c)) break;
    }
    return true;
  }

  // (rule_test_point_design)
  private static boolean qaDesignFile_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qaDesignFile_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rule_test_point_design(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // REQUIREMENT_KEY SEPARATOR CONTENT (CONCAT_NEW_LINE CONTENT)*
  public static boolean rule_first_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_first_line")) return false;
    if (!nextTokenIs(b, REQUIREMENT_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, REQUIREMENT_KEY, SEPARATOR, CONTENT);
    r = r && rule_first_line_3(b, l + 1);
    exit_section_(b, m, RULE_FIRST_LINE, r);
    return r;
  }

  // (CONCAT_NEW_LINE CONTENT)*
  private static boolean rule_first_line_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_first_line_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_first_line_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_first_line_3", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE CONTENT
  private static boolean rule_first_line_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_first_line_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONCAT_NEW_LINE, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TEST_CASE_DATA_KEY SEPARATOR (CONCAT_NEW_LINE? CONTENT)*
  public static boolean rule_test_case_data(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_data")) return false;
    if (!nextTokenIs(b, TEST_CASE_DATA_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TEST_CASE_DATA_KEY, SEPARATOR);
    r = r && rule_test_case_data_2(b, l + 1);
    exit_section_(b, m, RULE_TEST_CASE_DATA, r);
    return r;
  }

  // (CONCAT_NEW_LINE? CONTENT)*
  private static boolean rule_test_case_data_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_data_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_case_data_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_case_data_2", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE? CONTENT
  private static boolean rule_test_case_data_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_data_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rule_test_case_data_2_0_0(b, l + 1);
    r = r && consumeToken(b, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // CONCAT_NEW_LINE?
  private static boolean rule_test_case_data_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_data_2_0_0")) return false;
    consumeToken(b, CONCAT_NEW_LINE);
    return true;
  }

  /* ********************************************************** */
  // TEST_CASE_DESC_KEY SEPARATOR CONTENT (CONCAT_NEW_LINE CONTENT)*
  public static boolean rule_test_case_desc(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_desc")) return false;
    if (!nextTokenIs(b, TEST_CASE_DESC_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TEST_CASE_DESC_KEY, SEPARATOR, CONTENT);
    r = r && rule_test_case_desc_3(b, l + 1);
    exit_section_(b, m, RULE_TEST_CASE_DESC, r);
    return r;
  }

  // (CONCAT_NEW_LINE CONTENT)*
  private static boolean rule_test_case_desc_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_desc_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_case_desc_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_case_desc_3", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE CONTENT
  private static boolean rule_test_case_desc_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_desc_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONCAT_NEW_LINE, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TEST_CASE_NAME_KEY SEPARATOR CONTENT (CONCAT_NEW_LINE CONTENT)* rule_test_case_desc rule_test_case_data? rule_test_case_step rule_test_case_expect
  public static boolean rule_test_case_design(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_design")) return false;
    if (!nextTokenIs(b, TEST_CASE_NAME_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TEST_CASE_NAME_KEY, SEPARATOR, CONTENT);
    r = r && rule_test_case_design_3(b, l + 1);
    r = r && rule_test_case_desc(b, l + 1);
    r = r && rule_test_case_design_5(b, l + 1);
    r = r && rule_test_case_step(b, l + 1);
    r = r && rule_test_case_expect(b, l + 1);
    exit_section_(b, m, RULE_TEST_CASE_DESIGN, r);
    return r;
  }

  // (CONCAT_NEW_LINE CONTENT)*
  private static boolean rule_test_case_design_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_design_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_case_design_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_case_design_3", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE CONTENT
  private static boolean rule_test_case_design_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_design_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONCAT_NEW_LINE, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // rule_test_case_data?
  private static boolean rule_test_case_design_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_design_5")) return false;
    rule_test_case_data(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // TEST_CASE_EXPECT_KEY SEPARATOR CONTENT (CONCAT_NEW_LINE CONTENT)*
  public static boolean rule_test_case_expect(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_expect")) return false;
    if (!nextTokenIs(b, TEST_CASE_EXPECT_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TEST_CASE_EXPECT_KEY, SEPARATOR, CONTENT);
    r = r && rule_test_case_expect_3(b, l + 1);
    exit_section_(b, m, RULE_TEST_CASE_EXPECT, r);
    return r;
  }

  // (CONCAT_NEW_LINE CONTENT)*
  private static boolean rule_test_case_expect_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_expect_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_case_expect_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_case_expect_3", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE CONTENT
  private static boolean rule_test_case_expect_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_expect_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONCAT_NEW_LINE, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TEST_CASE_STEP_KEY SEPARATOR CONTENT (CONCAT_NEW_LINE CONTENT)*
  public static boolean rule_test_case_step(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_step")) return false;
    if (!nextTokenIs(b, TEST_CASE_STEP_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TEST_CASE_STEP_KEY, SEPARATOR, CONTENT);
    r = r && rule_test_case_step_3(b, l + 1);
    exit_section_(b, m, RULE_TEST_CASE_STEP, r);
    return r;
  }

  // (CONCAT_NEW_LINE CONTENT)*
  private static boolean rule_test_case_step_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_step_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_case_step_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_case_step_3", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE CONTENT
  private static boolean rule_test_case_step_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_case_step_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONCAT_NEW_LINE, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TEST_POINT_KEY SEPARATOR CONTENT (CONCAT_NEW_LINE CONTENT)* (rule_test_case_design)*
  public static boolean rule_test_point_design(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_point_design")) return false;
    if (!nextTokenIs(b, TEST_POINT_KEY)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, TEST_POINT_KEY, SEPARATOR, CONTENT);
    r = r && rule_test_point_design_3(b, l + 1);
    r = r && rule_test_point_design_4(b, l + 1);
    exit_section_(b, m, RULE_TEST_POINT_DESIGN, r);
    return r;
  }

  // (CONCAT_NEW_LINE CONTENT)*
  private static boolean rule_test_point_design_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_point_design_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_point_design_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_point_design_3", c)) break;
    }
    return true;
  }

  // CONCAT_NEW_LINE CONTENT
  private static boolean rule_test_point_design_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_point_design_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, CONCAT_NEW_LINE, CONTENT);
    exit_section_(b, m, null, r);
    return r;
  }

  // (rule_test_case_design)*
  private static boolean rule_test_point_design_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_point_design_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!rule_test_point_design_4_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rule_test_point_design_4", c)) break;
    }
    return true;
  }

  // (rule_test_case_design)
  private static boolean rule_test_point_design_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rule_test_point_design_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rule_test_case_design(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
