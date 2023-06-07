package vip.testops.qa_design.lang;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.lang.psi.*;


public class QaDesignStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
    public QaDesignStructureViewModel(@Nullable Editor editor, PsiFile file) {
        super(file, editor, new QaDesignStructureViewElement(file));
    }

    @NotNull
    public Sorter @NotNull [] getSorters() {
        return Sorter.EMPTY_ARRAY;
    }


    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element.getValue() instanceof QaDesignRuleTestCaseDesc ||
                element.getValue() instanceof QaDesignRuleTestCaseData ||
                element.getValue() instanceof QaDesignRuleTestCaseStep ||
                element.getValue() instanceof QaDesignRuleTestCaseExpect ||
                element.getValue() instanceof QaDesignRuleFirstLine;
    }

    @Override
    protected Class<?> @NotNull [] getSuitableClasses() {
        return new Class[]{
                QaDesignRuleFirstLine.class,
                QaDesignRuleTestPointDesign.class,
                QaDesignRuleTestCaseDesign.class,
                QaDesignRuleTestCaseDesc.class,
                QaDesignRuleTestCaseData.class,
                QaDesignRuleTestCaseStep.class,
                QaDesignRuleTestCaseExpect.class,
        };
    }
}
