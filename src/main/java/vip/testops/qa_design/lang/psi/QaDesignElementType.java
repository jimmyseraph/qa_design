package vip.testops.qa_design.lang.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignLanguage;

public class QaDesignElementType extends IElementType {
    public QaDesignElementType(@NotNull @NonNls String debugName) {
        super(debugName, QaDesignLanguage.INSTANCE);
    }
}
