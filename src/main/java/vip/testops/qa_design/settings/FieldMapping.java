package vip.testops.qa_design.settings;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.RequiredElement;
import com.intellij.util.xmlb.annotations.Attribute;

public final class FieldMapping {
    public static final ExtensionPointName<FieldMapping> EP_NAME = new ExtensionPointName<>("vip.testops.qa_design.settings.fieldMapping");
    @RequiredElement
    @Attribute("column")
    public String column;

    @RequiredElement
    @Attribute("fieldName")
    public String fieldName;

    @Attribute("qaDesignKeyword")
    public String qaDesignKeyword;

    public FieldMapping(String column, String fieldName, String qaDesignKeyword) {
        this.fieldName = fieldName;
        this.qaDesignKeyword = qaDesignKeyword;
    }

    public FieldMapping() {
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getQaDesignKeyword() {
        return qaDesignKeyword;
    }

    public void setQaDesignKeyword(String qaDesignKeyword) {
        this.qaDesignKeyword = qaDesignKeyword;
    }

    public Integer getColumnNum() {
        return calcExcelColNum(column);
    }

    public static Integer calcExcelColNum(String col) {
        if(col == null || col.length() == 0) {
            return -1;
        }
        String s = col.toLowerCase();
        int result = 0;
        for(int i = col.length() - 1; i >= 0; i--) {
            if(i == col.length() - 1) {
                result += s.charAt(i) - 'a';
            } else {
                result += (s.charAt(i) - 'a' + 1) * Math.pow('z' - 'a' + 1, col.length() - 1 - i);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FieldMapping)) {
            return false;
        }
        FieldMapping fieldMapping = (FieldMapping) obj;
        if(fieldMapping.qaDesignKeyword != null) {
            return fieldMapping.column.equals(this.column) && fieldMapping.fieldName.equals(this.fieldName) && fieldMapping.qaDesignKeyword.equals(this.qaDesignKeyword);
        } else {
            return fieldMapping.column.equals(this.column) && fieldMapping.fieldName.equals(this.fieldName) && this.qaDesignKeyword == null;
        }
    }
}
