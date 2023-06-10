// This is a generated file. Not intended for manual editing.
package vip.testops.qa_design.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;

public interface QaDesignRuleTestPointDesign extends QaDesignNamedElement {

  @NotNull
  List<QaDesignRuleTestCaseDesign> getRuleTestCaseDesignList();

  String getName();

  PsiElement setName(String newName);

  String getValue();

  String getContent();

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

}
