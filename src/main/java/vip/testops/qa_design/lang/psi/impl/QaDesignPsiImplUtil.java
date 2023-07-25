package vip.testops.qa_design.lang.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.lang.psi.*;

import javax.swing.*;

public class QaDesignPsiImplUtil {

    public static String getValue(QaDesignRuleLinkedMethod element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.LINKED_METHOD_VALUE);
        if (valueNode != null) {
            return valueNode.getText();
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleFirstLine element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleTestPointDesign element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleTestCaseDesign element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleTestCaseDesc element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleTestCaseStep element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleTestCaseData element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getValue(QaDesignRuleTestCaseExpect element) {
        ASTNode valueNode = element.getNode().findChildByType(QaDesignTypes.CONTENT);
        if (valueNode != null) {
            return valueNode.getText().replace("\\\n", "\n");
        } else {
            return null;
        }
    }

    public static String getContent(QaDesignRuleFirstLine element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    public static String getContent(QaDesignRuleTestPointDesign element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    public static String getContent(QaDesignRuleTestCaseDesign element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    public static String getContent(QaDesignRuleTestCaseDesc element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    public static String getContent(QaDesignRuleTestCaseStep element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    public static String getContent(QaDesignRuleTestCaseData element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    public static String getContent(QaDesignRuleTestCaseExpect element) {
        return getNodesContent(element.getNode().findChildByType(QaDesignTypes.CONTENT));
    }

    private static String getNodesContent(ASTNode contentNode) {
        StringBuilder sb = new StringBuilder();
        while (contentNode != null) {
            sb.append(contentNode.getText()).append("\n");
            contentNode = contentNode.getTreeNext();
        }
        return sb.toString();
    }

    public static String getName(QaDesignRuleLinkedMethod element) {
        return QaDesignBundle.message("keywords.qa_design.link");
    }

    public static String getName(QaDesignRuleFirstLine element) {
        return QaDesignBundle.message("keywords.qa_design.requirement");
    }

    public static String getName(QaDesignRuleTestPointDesign element) {
        return QaDesignBundle.message("keywords.qa_design.test_point");
    }

    public static String getName(QaDesignRuleTestCaseDesign element) {
        return QaDesignBundle.message("keywords.qa_design.testcase");
    }

    public static String getName(QaDesignRuleTestCaseDesc element) {
        return QaDesignBundle.message("keywords.qa_design.testcase.desc");
    }

    public static String getName(QaDesignRuleTestCaseStep element) {
        return QaDesignBundle.message("keywords.qa_design.testcase.step");
    }

    public static String getName(QaDesignRuleTestCaseData element) {
        return QaDesignBundle.message("keywords.qa_design.testcase.data");
    }

    public static String getName(QaDesignRuleTestCaseExpect element) {
        return QaDesignBundle.message("keywords.qa_design.testcase.expect");
    }

    public static PsiElement setName(QaDesignRuleLinkedMethod element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleFirstLine element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleTestPointDesign element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleTestCaseDesign element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleTestCaseDesc element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleTestCaseStep element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleTestCaseData element, String newName) {
        return element;
    }

    public static PsiElement setName(QaDesignRuleTestCaseExpect element, String newName) {
        return element;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleLinkedMethod element) {
//        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.LINKED_METHOD_KEY);
//        return keyNode != null ? keyNode.getPsi() : null;
        return element;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleFirstLine element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.REQUIREMENT_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleTestPointDesign element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.TEST_POINT_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleTestCaseDesign element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.TEST_CASE_NAME_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleTestCaseDesc element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.TEST_CASE_DESC_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleTestCaseStep element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.TEST_CASE_STEP_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleTestCaseExpect element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.TEST_CASE_EXPECT_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static PsiElement getNameIdentifier(QaDesignRuleTestCaseData element) {
        ASTNode keyNode = element.getNode().findChildByType(QaDesignTypes.TEST_CASE_DATA_KEY);
        return keyNode != null ? keyNode.getPsi() : null;
    }

    public static ItemPresentation getPresentation(final QaDesignRuleFirstLine element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

    public static ItemPresentation getPresentation(final QaDesignRuleTestPointDesign element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

    public static ItemPresentation getPresentation(final QaDesignRuleTestCaseDesign element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

    public static ItemPresentation getPresentation(final QaDesignRuleTestCaseDesc element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
//                PsiFile containingFile = element.getContainingFile();
//                return containingFile == null ? null : containingFile.getName();
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

    public static ItemPresentation getPresentation(final QaDesignRuleTestCaseStep element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
//                PsiFile containingFile = element.getContainingFile();
//                return containingFile == null ? null : containingFile.getName();
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

    public static ItemPresentation getPresentation(final QaDesignRuleTestCaseExpect element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
//                PsiFile containingFile = element.getContainingFile();
//                return containingFile == null ? null : containingFile.getName();
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

    public static ItemPresentation getPresentation(final QaDesignRuleTestCaseData element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
//                PsiFile containingFile = element.getContainingFile();
//                return containingFile == null ? null : containingFile.getName();
                return element.getValue();
            }

            @Override
            public Icon getIcon(boolean unused) {
                return element.getIcon(0);
            }
        };
    }

}
