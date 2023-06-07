package vip.testops.qa_design.lang;

import com.intellij.icons.AllIcons;
import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.ui.UISettings;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignIcons;
import vip.testops.qa_design.QaDesignLanguage;
import vip.testops.qa_design.lang.psi.*;

import javax.swing.*;
import java.util.Objects;

public class QaDesignStructureAwareNavbar extends StructureAwareNavBarModelExtension {
    @NotNull
    @Override
    protected Language getLanguage() {
        return QaDesignLanguage.INSTANCE;
    }

    @Nullable
    @Override
    public PsiElement getLeafElement(@NotNull DataContext dataContext) {
        return super.getLeafElement(dataContext);
    }

    @Override
    public @Nullable String getPresentableText(Object object) {
//        System.out.println(object);
        if (object instanceof QaDesignFile) {
            return ((QaDesignFile)object).getName();
        }
        if (object instanceof QaDesignRuleFirstLine) {
            return ((QaDesignRuleFirstLine)object).getName();
        }
        if (object instanceof QaDesignRuleTestPointDesign) {
            return ((QaDesignRuleTestPointDesign)object).getName();
        }
        if (object instanceof QaDesignRuleTestCaseDesign) {
            return ((QaDesignRuleTestCaseDesign)object).getName();
        }
        if (object instanceof QaDesignRuleTestCaseDesc) {
            return ((QaDesignRuleTestCaseDesc)object).getName();
        }
        if (object instanceof QaDesignRuleTestCaseStep) {
            return ((QaDesignRuleTestCaseStep)object).getName();
        }
        if (object instanceof QaDesignRuleTestCaseData) {
            return ((QaDesignRuleTestCaseData)object).getName();
        }
        if (object instanceof QaDesignRuleTestCaseExpect) {
            return ((QaDesignRuleTestCaseExpect)object).getName();
        }
        return null;
    }

    @Override
    public @Nullable Icon getIcon(Object object) {
        if (object instanceof QaDesignRuleFirstLine) {
            return QaDesignIcons.REQUIREMENT_ICON;
        }
        if (object instanceof QaDesignRuleTestPointDesign) {
            return QaDesignIcons.TEST_POINT_ICON;
        }
        if (object instanceof QaDesignRuleTestCaseDesign) {
            return QaDesignIcons.TEST_CASE_ICON;
        }
        if (
                object instanceof QaDesignRuleTestCaseDesc ||
                object instanceof QaDesignRuleTestCaseStep ||
                object instanceof QaDesignRuleTestCaseData ||
                object instanceof QaDesignRuleTestCaseExpect
        ) {
            return QaDesignIcons.TEST_CASE_DETAIL_ICON;
        }
        return null;
    }
}
