// This is a generated file. Not intended for manual editing.
package vip.testops.qa_design.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import vip.testops.qa_design.lang.psi.impl.*;

public interface QaDesignTypes {

  IElementType FAKE_RULE = new QaDesignElementType("FAKE_RULE");
  IElementType RULE_FIRST_LINE = new QaDesignElementType("RULE_FIRST_LINE");
  IElementType RULE_LINKED_METHOD = new QaDesignElementType("RULE_LINKED_METHOD");
  IElementType RULE_TAG = new QaDesignElementType("RULE_TAG");
  IElementType RULE_TEST_CASE_DATA = new QaDesignElementType("RULE_TEST_CASE_DATA");
  IElementType RULE_TEST_CASE_DESC = new QaDesignElementType("RULE_TEST_CASE_DESC");
  IElementType RULE_TEST_CASE_DESIGN = new QaDesignElementType("RULE_TEST_CASE_DESIGN");
  IElementType RULE_TEST_CASE_EXPECT = new QaDesignElementType("RULE_TEST_CASE_EXPECT");
  IElementType RULE_TEST_CASE_STEP = new QaDesignElementType("RULE_TEST_CASE_STEP");
  IElementType RULE_TEST_POINT_DESIGN = new QaDesignElementType("RULE_TEST_POINT_DESIGN");

  IElementType COMMA = new QaDesignTokenType("COMMA");
  IElementType COMMENT = new QaDesignTokenType("COMMENT");
  IElementType CONCAT_NEW_LINE = new QaDesignTokenType("CONCAT_NEW_LINE");
  IElementType CONTENT = new QaDesignTokenType("CONTENT");
  IElementType CRLF = new QaDesignTokenType("CRLF");
  IElementType DOUBLE_QUOTE = new QaDesignTokenType("DOUBLE_QUOTE");
  IElementType FEATURE = new QaDesignTokenType("FEATURE");
  IElementType INSIDE = new QaDesignTokenType("INSIDE");
  IElementType LEFT_BOUNDARY_LINK = new QaDesignTokenType("LEFT_BOUNDARY_LINK");
  IElementType LEFT_BOUNDARY_TAG = new QaDesignTokenType("LEFT_BOUNDARY_TAG");
  IElementType LINKED_METHOD_VALUE = new QaDesignTokenType("LINKED_METHOD_VALUE");
  IElementType RIGHT_BOUNDARY = new QaDesignTokenType("RIGHT_BOUNDARY");
  IElementType SEPARATOR = new QaDesignTokenType("SEPARATOR");
  IElementType TAG_VALUE = new QaDesignTokenType("TAG_VALUE");
  IElementType TEST_CASE_DATA_KEY = new QaDesignTokenType("TEST_CASE_DATA_KEY");
  IElementType TEST_CASE_DESC_KEY = new QaDesignTokenType("TEST_CASE_DESC_KEY");
  IElementType TEST_CASE_EXPECT_KEY = new QaDesignTokenType("TEST_CASE_EXPECT_KEY");
  IElementType TEST_CASE_NAME_KEY = new QaDesignTokenType("TEST_CASE_NAME_KEY");
  IElementType TEST_CASE_STEP_KEY = new QaDesignTokenType("TEST_CASE_STEP_KEY");
  IElementType TEST_POINT_KEY = new QaDesignTokenType("TEST_POINT_KEY");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == FAKE_RULE) {
        return new QaDesignFakeRuleImpl(node);
      }
      else if (type == RULE_FIRST_LINE) {
        return new QaDesignRuleFirstLineImpl(node);
      }
      else if (type == RULE_LINKED_METHOD) {
        return new QaDesignRuleLinkedMethodImpl(node);
      }
      else if (type == RULE_TAG) {
        return new QaDesignRuleTagImpl(node);
      }
      else if (type == RULE_TEST_CASE_DATA) {
        return new QaDesignRuleTestCaseDataImpl(node);
      }
      else if (type == RULE_TEST_CASE_DESC) {
        return new QaDesignRuleTestCaseDescImpl(node);
      }
      else if (type == RULE_TEST_CASE_DESIGN) {
        return new QaDesignRuleTestCaseDesignImpl(node);
      }
      else if (type == RULE_TEST_CASE_EXPECT) {
        return new QaDesignRuleTestCaseExpectImpl(node);
      }
      else if (type == RULE_TEST_CASE_STEP) {
        return new QaDesignRuleTestCaseStepImpl(node);
      }
      else if (type == RULE_TEST_POINT_DESIGN) {
        return new QaDesignRuleTestPointDesignImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
