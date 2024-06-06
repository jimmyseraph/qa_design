package vip.testops.qa_design.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.extensions.ExtensionPointListener;
import com.intellij.openapi.extensions.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@State(name = "ExportAndImportSettings", storages = @Storage("qa_design_excel_mapping_settings.xml"))
public final class ExportAndImportSettings implements PersistentStateComponent<ExportAndImportSettings.State> {

    private final List<FieldMapping> fieldMappings = new LinkedList<>();

    public ExportAndImportSettings() {
        fieldMappings.addAll(FieldMapping.EP_NAME.getExtensionList());
        FieldMapping.EP_NAME.addExtensionPointListener(new ExtensionPointListener<>() {
            @Override
            public void extensionAdded(@NotNull FieldMapping extension, @NotNull PluginDescriptor pluginDescriptor) {
                if(!isContain(fieldMappings, extension)) {
                    fieldMappings.add(extension);
                } else {
                    FieldMapping find = findMapping(fieldMappings, extension);
                    if(find != null) {
                        find.qaDesignKeyword = extension.qaDesignKeyword;
                    }
                }
            }

            @Override
            public void extensionRemoved(@NotNull FieldMapping extension, @NotNull PluginDescriptor pluginDescriptor) {
                fieldMappings.stream().filter(m -> m.fieldName.equals(extension.fieldName)).findFirst().ifPresent(fieldMappings::remove);
            }


        }, null);
    }

    public List<FieldMapping> getFieldMappings() {
        return fieldMappings;
    }

    public static ExportAndImportSettings getSettings() {
        return ApplicationManager.getApplication().getService(ExportAndImportSettings.class);
    }

    @Override
    public @NotNull State getState() {
        State state = new State();
        List<FieldMapping> baseline = new ArrayList<>();
        for(FieldMapping fieldMapping : FieldMapping.EP_NAME.getExtensionList()) {
            if(!isContain(baseline, fieldMapping)){
                baseline.add(fieldMapping);
            }
        }
        state.addedFieldMappings = fieldMappings.stream().filter(
                m -> !isContain(baseline, m)).collect(Collectors.toList());
        state.removedFieldMappings = baseline.stream().filter(
                m -> !isContain(fieldMappings, m)).collect(Collectors.toList());
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        fieldMappings.clear();
        for(FieldMapping fieldMapping : FieldMapping.EP_NAME.getExtensionList()) {
            if(!isContain(fieldMappings, fieldMapping)){
                fieldMappings.add(fieldMapping);
            }
        }
        for(FieldMapping fieldMapping : state.removedFieldMappings) {
            FieldMapping find = findMapping(fieldMappings, fieldMapping);
            if(find != null){
                fieldMappings.remove(find);
            }
        }
        for(FieldMapping fieldMapping : state.addedFieldMappings) {
            if(!isContain(fieldMappings, fieldMapping)){
                fieldMappings.add(fieldMapping);
            }
        }
        for(FieldMapping fieldMapping : state.modifiedFieldMappings) {
            FieldMapping find = findMapping(fieldMappings, fieldMapping);
            if(find != null){
                find.qaDesignKeyword = fieldMapping.qaDesignKeyword;
            }
        }
    }

    private boolean isContain(List<FieldMapping> fieldMappings, FieldMapping mapping){
        return fieldMappings.stream().anyMatch(m -> m.equals(mapping));
    }
    private FieldMapping findMapping(List<FieldMapping> fieldMappings, FieldMapping mapping){
        return fieldMappings.stream().filter(m -> m.equals(mapping)).findFirst().orElse(null);
    }

    public static class State {
        public List<FieldMapping> addedFieldMappings = new ArrayList<>();
        public List<FieldMapping> removedFieldMappings = new ArrayList<>();

        public List<FieldMapping> modifiedFieldMappings = new ArrayList<>();
    }
}
