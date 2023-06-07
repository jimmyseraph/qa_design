package vip.testops.qa_design.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.toolWindow.ui.FieldMappingTable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QaDesignExportAndImportConfigurable implements SearchableConfigurable {

    private final ExportAndImportSettings settings = ExportAndImportSettings.getSettings();

    private JPanel mainPanel;
    private FieldMappingTable fieldMappingTable;

    @Override
    public @NotNull @NonNls String getId() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return QaDesignBundle.message("settings.qa_design.export_import.displayName");
    }

    @Override
    public @Nullable JComponent createComponent() {
        if(mainPanel == null){
            mainPanel = new JPanel(new BorderLayout());
            fieldMappingTable = new FieldMappingTable();
            mainPanel.add(fieldMappingTable.getComponent(), BorderLayout.CENTER);
            fieldMappingTable.setItems(settings.getFieldMappings());
        }
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        List<FieldMapping> current = fieldMappingTable.getItems();
        List<FieldMapping> baseline = settings.getFieldMappings();
        if(current.size() != baseline.size()) {
            return true;
        }
        return current.stream().anyMatch(m -> !baseline.contains(m)) || baseline.stream().anyMatch(m -> !current.contains(m));
    }

    @Override
    public void reset() {
        fieldMappingTable.setItems(settings.getFieldMappings());
    }

    @Override
    public void apply() throws ConfigurationException {
        settings.getFieldMappings().clear();
        settings.getFieldMappings().addAll(fieldMappingTable.getItems());
    }

}
