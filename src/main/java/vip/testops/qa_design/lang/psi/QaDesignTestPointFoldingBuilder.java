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

public class QaDesignTestPointFoldingBuilder extends FoldingBuilderEx implements DumbAware {
    @Override
    public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
        List<FoldingDescriptor> descriptors = new ArrayList<>();
        PsiTreeUtil.findChildrenOfType(root, QaDesignRuleTestPointDesign.class).forEach(testPoint -> {
            descriptors.add(new FoldingDescriptor(testPoint.getNode(),
                    new TextRange(
                            testPoint.getTextRange().getStartOffset() + testPoint.getName().length()+1,
                            testPoint.getTextRange().getEndOffset()),
                    FoldingGroup.newGroup("test_point")) {
                @Override
                public @Nullable String getPlaceholderText() {
                    return " " + testPoint.getValue();
                }
            });
        });
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
        if(node.getElementType() == QaDesignTypes.RULE_TEST_POINT_DESIGN) {
            return node.getText() + "...";
        }
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return false;
    }
}
