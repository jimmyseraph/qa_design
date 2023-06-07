// This is a generated file. Not intended for manual editing.
package vip.testops.qa_design.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static vip.testops.qa_design.lang.psi.QaDesignTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import vip.testops.qa_design.lang.psi.*;

public class QaDesignFakeRuleImpl extends ASTWrapperPsiElement implements QaDesignFakeRule {

  public QaDesignFakeRuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull QaDesignVisitor visitor) {
    visitor.visitFakeRule(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof QaDesignVisitor) accept((QaDesignVisitor)visitor);
    else super.accept(visitor);
  }

}
