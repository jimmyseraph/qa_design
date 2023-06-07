package vip.testops.qa_design.lang;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignIcons;
import vip.testops.qa_design.lang.psi.*;

import javax.swing.*;

public class QaDesignIconProvider extends IconProvider {
    @Override
    public @Nullable Icon getIcon(@NotNull PsiElement element, int flags) {
        if(element instanceof QaDesignRuleFirstLine) {
            return QaDesignIcons.REQUIREMENT_ICON;
        } else if (element instanceof QaDesignRuleTestPointDesign) {
            return QaDesignIcons.TEST_POINT_ICON;
        } else if (element instanceof QaDesignRuleTestCaseDesign) {
            return QaDesignIcons.TEST_CASE_ICON;
        } else if (
                element instanceof QaDesignRuleTestCaseDesc ||
                element instanceof QaDesignRuleTestCaseData ||
                element instanceof QaDesignRuleTestCaseExpect ||
                element instanceof QaDesignRuleTestCaseStep
        ) {
            return QaDesignIcons.TEST_CASE_DETAIL_ICON;
        } else {
            return null;
        }
    }
}
