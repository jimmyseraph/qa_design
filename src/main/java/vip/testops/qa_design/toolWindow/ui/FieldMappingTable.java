package vip.testops.qa_design.toolWindow.ui;

import com.intellij.execution.util.ListTableWithButtons;
import com.intellij.icons.AllIcons;
import com.intellij.ide.ui.laf.darcula.DarculaUIUtil;
import com.intellij.openapi.ui.ComboBoxTableRenderer;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.Disposable;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.lang.QaDesignKeyword;
import vip.testops.qa_design.settings.FieldMapping;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldMappingTable extends ListTableWithButtons<FieldMappingTable.Item> {

    private static final Disposable validatorsDisposable = Disposer.newDisposable();

    private static final String[] selectOptions = Arrays.stream(QaDesignKeyword.values()).map(QaDesignKeyword::getName).toArray(String[]::new);

    private static final ColumnInfo<Item, String> EXCEL_FIELD_INDEX_COLUMN = new ColumnInfo<>("Excel Column") {
        @Override
        public @Nullable String valueOf(Item item) {
            return item.column;
        }

        @Override
        public @Nullable TableCellEditor getEditor(Item item) {
            ExtendableTextField cellEditor = new ExtendableTextField();
            cellEditor.putClientProperty(DarculaUIUtil.COMPACT_PROPERTY, Boolean.TRUE);

            return new DefaultCellEditor(cellEditor);
        }

        @Override
        public @Nullable TableCellRenderer getRenderer(Item item) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setText(item.column);
            return renderer;
        }

        @Override
        public boolean isCellEditable(Item item) {
            return true;
        }

        @Override
        public void setValue(Item item, String value) {
            item.column = value;
        }
    };

    private static final ColumnInfo<Item, String> EXCEL_FIELD_COLUMN = new ColumnInfo<>("Excel Field") {
        @Override
        public @Nullable String valueOf(Item item) {
            return item.excelFieldName;
        }

        @Override
        public @Nullable TableCellEditor getEditor(Item item) {
            ExtendableTextField cellEditor = new ExtendableTextField();
            cellEditor.putClientProperty(DarculaUIUtil.COMPACT_PROPERTY, Boolean.TRUE);

            return new DefaultCellEditor(cellEditor);
        }

        @Override
        public @Nullable TableCellRenderer getRenderer(Item item) {
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setText(item.excelFieldName);
            return renderer;
        }

        @Override
        public boolean isCellEditable(Item item) {
            return true;
        }

        @Override
        public void setValue(Item item, String value) {
            item.excelFieldName = value;
        }
    };

    private static final ColumnInfo<Item, String> QA_DESIGN_KEYWORD_COLUMN = new ColumnInfo<>("QA Design Keyword") {

        @Override
        public @Nullable String valueOf(Item item) {
            return item.qaDesignKeyword;
        }

        @Override
        public @Nullable TableCellRenderer getRenderer(Item item) {
            return new ComboBoxTableRenderer<>(selectOptions);
        }

        @Override
        public @Nullable TableCellEditor getEditor(Item item) {
            return new ComboBoxTableRenderer<>(selectOptions);
        }

        @Override
        public boolean isCellEditable(Item item) {
            return true;
        }

        @Override
        public void setValue(Item item, String value) {
            item.qaDesignKeyword = value;
        }

        @Override
        public int getAdditionalWidth() {
            return JBUIScale.scale(12) + AllIcons.General.ArrowDown.getIconWidth();
        }
    };

    public FieldMappingTable() {
        JBTable table = getTableView();
        table.getEmptyText().clear();
        table.getEmptyText().appendLine("No field mapping");
        table.getEmptyText().appendLine("Add Excel field mapping to keyword");
        table.setStriped(false);
    }

    @Override
    protected ListTableModel<Item> createListModel() {
        return new ListTableModel<>(EXCEL_FIELD_INDEX_COLUMN, EXCEL_FIELD_COLUMN, QA_DESIGN_KEYWORD_COLUMN);
    }

    @Override
    protected Item createElement() {
        return new Item("A", "", "");
    }

    @Override
    protected boolean isEmpty(Item element) {
        return element.excelFieldName.isEmpty();
    }

    @Override
    protected Item cloneElement(Item variable) {
        return new Item(variable.column, variable.excelFieldName, variable.qaDesignKeyword);
    }

    @Override
    protected boolean canDeleteElement(Item selection) {
        return true;
    }

    public void setItems(List<FieldMapping> fieldMappings) {
        List<Item> elements = new ArrayList<>();
        for (FieldMapping fieldMapping : fieldMappings) {

            elements.add(new Item(fieldMapping.getColumn(), fieldMapping.getFieldName(), fieldMapping.getQaDesignKeyword()));
        }
        setValues(elements);
        refreshValues();
    }

    public List<FieldMapping> getItems() {
        List<Item> elements = getElements();
        List<FieldMapping> fieldMappings = new ArrayList<>();
        for(Item item : elements) {
            fieldMappings.add(new FieldMapping(item.column, item.excelFieldName, item.qaDesignKeyword));
        }
        return fieldMappings;
    }

    static class Item {
        String column;
        String excelFieldName;
        String qaDesignKeyword;

        Item(String column, String excelFieldName, String qaDesignKeyword) {
            this.column = column;
            this.excelFieldName = excelFieldName;
            this.qaDesignKeyword = qaDesignKeyword;
        }
    }
}
