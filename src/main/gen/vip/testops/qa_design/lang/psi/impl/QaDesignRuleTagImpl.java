// This is a generated file. Not intended for manual editing.
package vip.testops.qa_design.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static vip.testops.qa_design.lang.psi.QaDesignTypes.*;
import vip.testops.qa_design.lang.psi.*;

public class QaDesignRuleTagImpl extends QaDesignNamedElementImpl implements QaDesignRuleTag {

  public QaDesignRuleTagImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull QaDesignVisitor visitor) {
    visitor.visitRuleTag(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof QaDesignVisitor) accept((QaDesignVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public String getName() {
    return QaDesignPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return QaDesignPsiImplUtil.setName(this, newName);
  }

  @Override
  public String getValue() {
    return QaDesignPsiImplUtil.getValue(this);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return QaDesignPsiImplUtil.getNameIdentifier(this);
  }

}
