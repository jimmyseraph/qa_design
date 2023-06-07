package vip.testops.qa_design.lang;

import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vip.testops.qa_design.QaDesignIcons;

import javax.swing.*;
import java.util.Map;

public class QaDesignColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Key", QaDesignSyntaxHighlighter.KEY),
            new AttributesDescriptor("Separator", QaDesignSyntaxHighlighter.SEPARATOR),
            new AttributesDescriptor("Value", QaDesignSyntaxHighlighter.CONTENT),
            new AttributesDescriptor("Bad value", QaDesignSyntaxHighlighter.BAD_CHARACTER)
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return QaDesignIcons.FILE_TYPE_ICON;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new QaDesignSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "# 测试一下\n" +
                "Requirement: 用户需求条目1\n" +
                "TestPoint: 测试要点1\n" +
                "    TestCase: case1" +
                "        TestCaseDesc: 这是一条测试案例\n" +
                "        TestCaseData: a=10;b=20;c=20\n" +
                "        TestCaseStep: Step1 操作步骤1 \\\n" +
                "            Step2 操作步骤2 \\\n" +
                "            Step3 操作步骤3\n" +
                "        TestCaseExpect: 结果符合预期";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Qa Design";
    }

}
