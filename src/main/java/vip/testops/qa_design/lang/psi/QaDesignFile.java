package vip.testops.qa_design.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.QaDesignFileType;
import vip.testops.qa_design.QaDesignLanguage;

public class QaDesignFile extends PsiFileBase {
    public QaDesignFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, QaDesignLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return QaDesignFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return QaDesignBundle.message("fileType.qa_design.description");
    }
}
