package vip.testops.qa_design.actions.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.components.panels.VerticalLayout;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignBundle;

import javax.swing.*;
import java.awt.*;

public class ExportDialog extends DialogWrapper {

    private final Project project;
    private TextFieldWithBrowseButton textFieldWithBrowseButton;
    private JCheckBox checkBox;

    public ExportDialog(Project project) {
        super(true);
        setTitle(QaDesignBundle.message("export_dialog.qa_design.title"));
        this.project = project;
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JTextField textField = new JTextField();
        textField.setText(project.getBasePath());
        Dimension dimension = textField.getPreferredSize();
        dimension.width = 300;
        textField.setPreferredSize(dimension);
        JPanel panel = new JPanel(new VerticalLayout(2));
        textFieldWithBrowseButton = new TextFieldWithBrowseButton(textField, e -> {
            FileChooserDescriptor descriptor = FileChooserDescriptorFactory
                    .createSingleFolderDescriptor()
                    .withTitle(QaDesignBundle.message("export_dialog.fileChooser.qa_design.title"))
                    .withDescription(QaDesignBundle.message("export_dialog.fileChooser.qa_design.description"));
            VirtualFile file = FileChooser.chooseFile(descriptor, project, null);
            assert file != null;
            String path = file.getCanonicalPath();
            textField.setText(path);
        });

        checkBox = new JCheckBox(QaDesignBundle.message("export_dialog.qa_design.overwrite"), false);

        panel.add(textFieldWithBrowseButton);
        panel.add(checkBox);

        return panel;
    }

    public String getExportPath() {
        return textFieldWithBrowseButton.getText();
    }

    public boolean isOverwrite() {
        return checkBox.isSelected();
    }
}
