package vip.testops.qa_design.settings;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.RequiredElement;
import com.intellij.util.xmlb.annotations.Attribute;

public final class FieldMapping {
    public static final ExtensionPointName<FieldMapping> EP_NAME = new ExtensionPointName<>("vip.testops.qa_design.settings.fieldMapping");
    @RequiredElement
    @Attribute("fieldName")
    public String fieldName;

    @Attribute("qaDesignKeyword")
    public String qaDesignKeyword;

    public FieldMapping(String fieldName, String qaDesignKeyword) {
        this.fieldName = fieldName;
        this.qaDesignKeyword = qaDesignKeyword;
    }

    public FieldMapping() {
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

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FieldMapping)) {
            return false;
        }
        FieldMapping fieldMapping = (FieldMapping) obj;
        if(fieldMapping.qaDesignKeyword != null) {
            return fieldMapping.fieldName.equals(this.fieldName) && fieldMapping.qaDesignKeyword.equals(this.qaDesignKeyword);
        } else {
            return fieldMapping.fieldName.equals(this.fieldName) && this.qaDesignKeyword == null;
        }
    }
}
