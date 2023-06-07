package vip.testops.qa_design.lang.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignLanguage;

public class QaDesignTokenType extends IElementType {
    public QaDesignTokenType(@NotNull @NonNls String debugName) {
        super(debugName, QaDesignLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "QaDesignTokenType." + super.toString();
    }
}
