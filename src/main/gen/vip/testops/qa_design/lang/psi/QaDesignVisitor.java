// This is a generated file. Not intended for manual editing.
package vip.testops.qa_design.lang.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class QaDesignVisitor extends PsiElementVisitor {

  public void visitFakeRule(@NotNull QaDesignFakeRule o) {
    visitPsiElement(o);
  }

  public void visitRuleFirstLine(@NotNull QaDesignRuleFirstLine o) {
    visitNamedElement(o);
  }

  public void visitRuleTestCaseData(@NotNull QaDesignRuleTestCaseData o) {
    visitNamedElement(o);
  }

  public void visitRuleTestCaseDesc(@NotNull QaDesignRuleTestCaseDesc o) {
    visitNamedElement(o);
  }

  public void visitRuleTestCaseDesign(@NotNull QaDesignRuleTestCaseDesign o) {
    visitNamedElement(o);
  }

  public void visitRuleTestCaseExpect(@NotNull QaDesignRuleTestCaseExpect o) {
    visitNamedElement(o);
  }

  public void visitRuleTestCaseStep(@NotNull QaDesignRuleTestCaseStep o) {
    visitNamedElement(o);
  }

  public void visitRuleTestPointDesign(@NotNull QaDesignRuleTestPointDesign o) {
    visitNamedElement(o);
  }

  public void visitNamedElement(@NotNull QaDesignNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
