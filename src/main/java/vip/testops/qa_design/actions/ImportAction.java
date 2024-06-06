package vip.testops.qa_design.actions;

import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignNotifier;
import vip.testops.qa_design.actions.entities.QaDesignEntity;
import vip.testops.qa_design.actions.entities.QaDesignTestCase;
import vip.testops.qa_design.actions.entities.QaDesignTestPoint;
import vip.testops.qa_design.actions.ui.ImportDialog;
import vip.testops.qa_design.lang.QaDesignKeyword;
import vip.testops.qa_design.lang.psi.QaDesignFile;
import vip.testops.qa_design.lang.psi.QaDesignRuleFirstLine;
import vip.testops.qa_design.lang.psi.impl.QaDesignRuleFirstLineImpl;
import vip.testops.qa_design.settings.ExportAndImportSettings;
import vip.testops.qa_design.settings.FieldMapping;
import vip.testops.qa_design.utils.ExcelUtils;
import vip.testops.qa_design.utils.FileUtil;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ImportAction extends AnAction {
    private final ExportAndImportSettings settings = ExportAndImportSettings.getSettings();
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ImportDialog dialog = new ImportDialog(e.getProject());
        Object obj = e.getData(CommonDataKeys.PSI_ELEMENT);
        PsiDirectory targetDirectory;
        if (obj instanceof PsiDirectory) {
            targetDirectory = (PsiDirectory) obj;
        } else if (obj instanceof PsiFile) {
            targetDirectory = ((PsiFile) obj).getParent();
        } else {
            return;
        }
        if(dialog.showAndGet()) {
            String excelFileFullName = dialog.getImportPath();
            File excelFile = new File(excelFileFullName);
            if(!excelFile.exists()) {
                QaDesignNotifier.notifyError(e.getProject(), "File not found: " + excelFileFullName);
                return;
            } else if(!excelFile.isFile()) {
                QaDesignNotifier.notifyError(e.getProject(), "Not a file: " + excelFileFullName);
                return;
            } else if(!excelFile.getName().endsWith(".xlsx") && !excelFile.getName().endsWith(".xls")) {
                QaDesignNotifier.notifyError(e.getProject(), "Not a xlsx or xls file: " + excelFileFullName);
                return;
            }
            try {
                ExcelUtils excelUtils = new ExcelUtils(excelFile, true);
                List<FieldMapping> fieldMappings = settings.getFieldMappings();
                List<QaDesignEntity> data = excelUtils.read(fieldMappings);
                excelUtils.close();
                if (data.size() == 0) {
                    return;
                }
                generatePsiFiles(data, targetDirectory, excelFile.getName());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void generatePsiFiles(List<QaDesignEntity> data, PsiDirectory targetDirectory, String excelFileName) throws IOException {
        for (QaDesignEntity entity : data) {
            String filename = entity.getFeature().isBlank() ? "qa_design.qd" : entity.getFeature() + ".qd";
            filename = FileUtil.getNonRepetitiveFilename(filename, targetDirectory, "qd");
            File file = new File(targetDirectory.getVirtualFile().getPath(), filename);

            try (FileWriter writer = new FileWriter(file)) {
                String firstLineContent = QaDesignKeyword.FEATURE.getName() + ": " + entity.getFeature().replace("\n", "\\\n");
                writer.write(firstLineContent);
                writer.write("\n\n");
                for (QaDesignTestPoint testPoint : entity.getTestPoints()) {
                    String testPointLineContent = QaDesignKeyword.TEST_POINT.getName() + ": " + testPoint.getName().replace("\n", "\\\n");
                    writer.write(testPointLineContent);
                    writer.write("\n");
                    for (QaDesignTestCase testCase : testPoint.getTestCases()) {
                        if(testCase.getLink() != null && !testCase.getLink().equals("")) {
                            String testCaseLink = "\t@@"+QaDesignKeyword.TEST_CASE_LINK.getName()+"(\""+testCase.getLink()+"\")";
                            writer.write(testCaseLink);
                            writer.write("\n");
                        }
                        if (testCase.getTags() != null && testCase.getTags().size() != 0) {
                            StringBuilder sb = new StringBuilder("\t@@");
                            sb.append(QaDesignKeyword.TEST_CASE_TAG.getName());
                            sb.append("(");
                            for(String tag: testCase.getTags()) {
                                if (tag != null && !tag.equals("")) {
                                    sb.append("\"").append(tag).append("\",");
                                }
                            }
                            if(sb.toString().endsWith(",")){
                                sb.deleteCharAt(sb.toString().length()-1);
                            }
                            sb.append(")");
                            writer.write(sb.toString());
                            writer.write("\n");
                        }
                        String testCaseLineContent = "\t" + QaDesignKeyword.TEST_CASE + ": " + testCase.getName().replace("\n", "\\\n");
                        writer.write(testCaseLineContent);
                        writer.write("\n");
                        if (testCase.getDesc() != null && !testCase.getDesc().equals("")) {
                            String testCaseDescLineContent = "\t\t" + QaDesignKeyword.TEST_CASE_DESC.getName() + ": " + testCase.getDesc().replace("\n", "\\\n");
                            writer.write(testCaseDescLineContent);
                            writer.write("\n");
                        }
                        if (testCase.getData() != null && !testCase.getData().equals("")) {
                            String testCaseDataLineContent = "\t\t" + QaDesignKeyword.TEST_CASE_DATA.getName() + ": " + testCase.getData().replace("\n", "\\\n");
                            writer.write(testCaseDataLineContent);
                            writer.write("\n");
                        }

                        String testCaseStepLineContent = "\t\t" + QaDesignKeyword.TEST_CASE_STEP.getName() + ": " + testCase.getStep().replace("\n", "\\\n");
                        writer.write(testCaseStepLineContent);
                        writer.write("\n");
                        String testCaseExpectLineContent = "\t\t" + QaDesignKeyword.TEST_CASE_EXPECT.getName() + ": " + testCase.getExpect().replace("\n", "\\\n");
                        writer.write(testCaseExpectLineContent);
                        writer.write("\n");
                    }
                    writer.write("\n");
                }
                writer.flush();
            }

        }
        JDialog dialog = new JDialog();
        dialog.setTitle("Import Information");
        dialog.setSize(400,100);
        dialog.setLocationRelativeTo(null);
        JPanel contentPanel = new JPanel();
        dialog.add(contentPanel, BorderLayout.CENTER);
        String text = excelFileName + "has imported successfully " + data.size() + "item case design.";
        JLabel label = new JLabel(text);
        contentPanel.add(label, BorderLayout.NORTH);

        JButton closeButton = new JButton("OK");
        closeButton.setBounds(10, 50, 100, 25);
        contentPanel.add(closeButton, BorderLayout.NORTH);
        closeButton.addActionListener(e -> dialog.setVisible(false));
        dialog.setVisible(true);
    }

}
