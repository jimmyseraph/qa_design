package vip.testops.qa_design.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QaDesignTestCaseFoldingBuilder  extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        PsiTreeUtil.findChildrenOfType(root, QaDesignRuleTestCaseDesign.class).forEach(testCase -> {
            descriptors.add(new FoldingDescriptor(testCase.getNode(),
                    new TextRange(
                            testCase.getTextRange().getStartOffset() + testCase.getName().length()+1,
                            testCase.getTextRange().getEndOffset()),
                    FoldingGroup.newGroup("test_case")) {
                @Override
                public @Nullable String getPlaceholderText() {
                    return " " + testCase.getValue();
                }
            });
        });
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        if(node.getElementType() == QaDesignTypes.RULE_TEST_CASE_DESIGN) {
            return node.getText() + "...";
        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return true;
    }
}
