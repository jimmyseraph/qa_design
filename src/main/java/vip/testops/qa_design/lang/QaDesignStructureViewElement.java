package vip.testops.qa_design.lang;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.lang.psi.*;
import vip.testops.qa_design.lang.psi.impl.*;

import java.util.ArrayList;
import java.util.List;

public class QaDesignStructureViewElement implements StructureViewTreeElement {

    private final NavigatablePsiElement myElement;

    public QaDesignStructureViewElement(NavigatablePsiElement element) {
        this.myElement = element;
    }

    @Override
    public Object getValue() {
        return myElement;
    }

    @Override
    public @NotNull ItemPresentation getPresentation() {
        ItemPresentation presentation = myElement.getPresentation();
        return presentation != null ? presentation : new PresentationData();
    }

    @Override
    public TreeElement @NotNull [] getChildren() {
        if (myElement instanceof QaDesignFile) {
            List<QaDesignRuleFirstLine> firstLines = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleFirstLine.class);
            List<QaDesignRuleTestPointDesign> testPoints = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleTestPointDesign.class);
            List<TreeElement> treeElements = new ArrayList<>(firstLines.size() + testPoints.size());
            for (QaDesignRuleFirstLine property : firstLines) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleFirstLineImpl) property));
            }
            for (QaDesignRuleTestPointDesign testPoint : testPoints) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleTestPointDesignImpl)testPoint));
            }
            return treeElements.toArray(new TreeElement[0]);
        }
        if (myElement instanceof QaDesignRuleTestPointDesign) {
            List<QaDesignRuleTestCaseDesign> testCaseDesigns = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleTestCaseDesign.class);
            List<TreeElement> treeElements = new ArrayList<>(testCaseDesigns.size());
            for (QaDesignRuleTestCaseDesign testCaseDesign : testCaseDesigns) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleTestCaseDesignImpl)testCaseDesign));
            }
            return treeElements.toArray(new TreeElement[0]);
        }
        if (myElement instanceof QaDesignRuleTestCaseDesign) {
            List<QaDesignRuleTestCaseDesc> testCaseDescNodes = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleTestCaseDesc.class);
            List<QaDesignRuleTestCaseData> testCaseDataNodes = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleTestCaseData.class);
            List<QaDesignRuleTestCaseStep> testCaseStepNodes = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleTestCaseStep.class);
            List<QaDesignRuleTestCaseExpect> testCaseExpectNodes = PsiTreeUtil.getChildrenOfTypeAsList(myElement, QaDesignRuleTestCaseExpect.class);
            List<TreeElement> treeElements = new ArrayList<>();
            for (QaDesignRuleTestCaseDesc testCaseDesc : testCaseDescNodes) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleTestCaseDescImpl)testCaseDesc));
            }
            for (QaDesignRuleTestCaseData testCaseData : testCaseDataNodes) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleTestCaseDataImpl)testCaseData));
            }
            for (QaDesignRuleTestCaseStep testCaseStep : testCaseStepNodes) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleTestCaseStepImpl)testCaseStep));
            }
            for (QaDesignRuleTestCaseExpect testCaseExpect : testCaseExpectNodes) {
                treeElements.add(new QaDesignStructureViewElement((QaDesignRuleTestCaseExpectImpl)testCaseExpect));
            }
            return treeElements.toArray(new TreeElement[0]);
        }
        return EMPTY_ARRAY;
    }

    @Override
    public void navigate(boolean requestFocus) {
        myElement.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return myElement.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return myElement.canNavigateToSource();
    }
}
