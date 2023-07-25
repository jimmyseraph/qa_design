package vip.testops.qa_design.utils;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiUtilCore;
import vip.testops.qa_design.lang.psi.QaDesignTypes;

public class ReferenceUtil {

    public static PsiElement getReference(PsiElement element) {

        PsiReference[] refs = element.getReferences();
        // get last ref in array
        PsiReference ref = refs[refs.length - 1];
        return ref.resolve();
    }

    // get "@@link" element from rule_test_case_design element
    public static PsiElement getLink(PsiElement element) {
        IElementType type = PsiUtilCore.getElementType(element);
        PsiElement caseDesignRuleElement;
        if (type == QaDesignTypes.TEST_CASE_NAME_KEY) {
            caseDesignRuleElement = element.getParent();
        } else if (type == QaDesignTypes.RULE_TEST_CASE_DESIGN) {
            caseDesignRuleElement = element;
        } else {
            return null;
        }
        PsiElement sibling = caseDesignRuleElement.getPrevSibling();
        while (sibling != null) {
            if (PsiUtilCore.getElementType(sibling) == QaDesignTypes.RULE_LINKED_METHOD) {
                ASTNode node = sibling.getNode().findChildByType(QaDesignTypes.LINKED_METHOD_VALUE);
                return node == null ? null : node.getPsi();
            } else if (PsiUtilCore.getElementType(sibling) == QaDesignTypes.RULE_TEST_CASE_DESIGN) {
                break;
            }
            sibling = sibling.getPrevSibling();
        }
        return null;
    }
}