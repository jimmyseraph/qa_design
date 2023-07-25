package vip.testops.qa_design.run;

import com.intellij.execution.TestStateStorage;
import com.intellij.execution.lineMarker.ExecutorAction;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.lang.QaDesignUtil;
import vip.testops.qa_design.lang.psi.QaDesignFile;
import vip.testops.qa_design.lang.psi.QaDesignTypes;
import vip.testops.qa_design.utils.ReferenceUtil;

public class QaDesignRunLineMarkerContributor extends RunLineMarkerContributor {

    private static final TokenSet RUN_LINE_MARKER_ELEMENTS = TokenSet.create(
//            QaDesignTypes.REQUIREMENT_KEY,
//            QaDesignTypes.TEST_POINT_KEY,
            QaDesignTypes.TEST_CASE_NAME_KEY
    );

    @Override
    public Info getSlowInfo(@NotNull PsiElement element) {
        if (!(element instanceof LeafElement)) {
            return null;
        }
        PsiFile psiFile = element.getContainingFile();
        if (!(psiFile instanceof QaDesignFile)) {
            return null;
        }
        IElementType type = PsiUtilCore.getElementType(element);
        if (!RUN_LINE_MARKER_ELEMENTS.contains(type)) {
            return null;
        }
        if(type == QaDesignTypes.TEST_CASE_NAME_KEY && !isTestCaseLinkedMethod(element)) {
            return null;
        }
//        if(type == QaDesignTypes.TEST_POINT_KEY && !isContainLinkedMethod(element)) {
//            return null;
//        }
//        if (type == QaDesignTypes.REQUIREMENT_KEY && !isContainLinkedMethod(element)) {
//            return null;
//        }
        TestStateStorage.Record state = getTestStateStorage(element);
        return getInfo(state, type != QaDesignTypes.TEST_CASE_NAME_KEY);
    }

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement element) {
        return null;
//        if (!(element instanceof LeafElement)) {
//            return null;
//        }
//        PsiFile psiFile = element.getContainingFile();
//        if (!(psiFile instanceof QaDesignFile)) {
//            return null;
//        }
//        IElementType type = PsiUtilCore.getElementType(element);
//        if (!RUN_LINE_MARKER_ELEMENTS.contains(type)) {
//            return null;
//        }
//        if(type == QaDesignTypes.TEST_CASE_NAME_KEY && !isTestCaseLinkedMethod(element)) {
//            return null;
//        }
//        if(type == QaDesignTypes.TEST_POINT_KEY && !isContainLinkedMethod(element)) {
//            return null;
//        }
//        if (type == QaDesignTypes.REQUIREMENT_KEY && !isContainLinkedMethod(element)) {
//            return null;
//        }
//        TestStateStorage.Record state = getTestStateStorage(element);
//        return getInfo(state, type != QaDesignTypes.TEST_CASE_NAME_KEY);
    }

    private boolean isTestCaseLinkedMethod(@NotNull PsiElement element) {
        return ReferenceUtil.getLink(element) != null;
    }

//    private boolean isContainLinkedMethod(@NotNull PsiElement element) {
//        if(PsiUtilCore.getElementType(element) == QaDesignTypes.REQUIREMENT_KEY) {
//            PsiElement parent = element.getParent();
//            parent = parent.getNextSibling();
//            while (parent != null) {
//                if (PsiUtilCore.getElementType(parent) == QaDesignTypes.RULE_TEST_POINT_DESIGN) {
//                    PsiElement child = parent.getFirstChild();
//                    while (child != null) {
//                        if (PsiUtilCore.getElementType(child) == QaDesignTypes.RULE_LINKED_METHOD) {
//                            return true;
//                        }
//                        child = child.getNextSibling();
//                    }
//                }
//                parent = parent.getNextSibling();
//            }
//            return false;
//        }
//        if(PsiUtilCore.getElementType(element) == QaDesignTypes.TEST_POINT_KEY) {
//            PsiElement sibling = element.getNextSibling();
//            while (sibling != null) {
//                if (PsiUtilCore.getElementType(sibling) == QaDesignTypes.RULE_LINKED_METHOD) {
//                    return true;
//                }
//                sibling = sibling.getNextSibling();
//            }
//        }
//        return false;
//    }

    private @Nullable TestStateStorage.Record getTestStateStorage(@NotNull PsiElement element) {

        String url = element.getContainingFile().getVirtualFile().getUrl() + ":" + QaDesignUtil.getLineNumber(element);
        return TestStateStorage.getInstance(element.getProject()).getState(url);
    }

    private @NotNull Info getInfo(@Nullable TestStateStorage.Record state, boolean isClass) {
        return new Info(
                getTestStateIcon(state, isClass),
                ExecutorAction.getActions(),
                RUN_TEST_TOOLTIP_PROVIDER
        );
    }
}
