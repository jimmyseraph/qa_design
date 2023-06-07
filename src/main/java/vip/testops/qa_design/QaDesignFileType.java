package vip.testops.qa_design;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class QaDesignFileType extends LanguageFileType {
    public static final QaDesignFileType INSTANCE = new QaDesignFileType();

    private QaDesignFileType() {
        super(QaDesignLanguage.INSTANCE);
    }

    @Override
    public @NonNls @NotNull String getName() {
        return QaDesignBundle.message("fileType.qa_design.name");
    }

    @Override
    public @NotNull String getDescription() {
        return QaDesignBundle.message("fileType.qa_design.description");
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return QaDesignBundle.message("fileType.qa_design.extension");
    }

    @Override
    public @Nullable Icon getIcon() {
        return QaDesignIcons.FILE_TYPE_ICON;
    }
}
