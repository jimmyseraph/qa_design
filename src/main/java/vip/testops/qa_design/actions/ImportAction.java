package vip.testops.qa_design.actions;

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ImportAction extends AnAction {
    private final ExportAndImportSettings settings = ExportAndImportSettings.getSettings();
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ImportDialog dialog = new ImportDialog(e.getProject());
        Object obj = e.getData(CommonDataKeys.PSI_ELEMENT);
        PsiDirectory targetDirectory = null;
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
                List<List<String>> data = excelUtils.read(fieldMappings);
                excelUtils.close();
                if (data.size() == 0) {
                    return;
                }
                generatePsiFiles(data, targetDirectory);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void generatePsiFiles(List<List<String>> data, PsiDirectory targetDirectory) throws IOException {
        List<QaDesignEntity> entities = parseExcelData(data);
        for (QaDesignEntity entity : entities) {
            int startNum = 1;
            String filename = entity.getRequirementId().isBlank() ? "qa_design.qd" : entity.getRequirementId() + ".qd";
            filename = FileUtil.getNonRepetitiveFilename(filename, targetDirectory, "qd");
//            if (targetDirectory.findFile(filename) != null) {
//                filename = filename.substring(0, filename.lastIndexOf(".")) + "_" + startNum + ".qd";
//                startNum++;
//            }
//            while (targetDirectory.findFile(filename) != null) {
//                filename = filename.substring(0, filename.lastIndexOf("_")) + "_" + startNum + ".qd";
//                startNum++;
//            }
            File file = new File(targetDirectory.getVirtualFile().getPath(), filename);

            try (FileWriter writer = new FileWriter(file)) {
                String firstLineContent = QaDesignKeyword.REQUIREMENT.getName() + ": " + entity.getRequirement().replace("\n", "\\\n");
                writer.write(firstLineContent);
                writer.write("\n\n");
                for (QaDesignTestPoint testPoint : entity.getTestPoints()) {
                    String testPointLineContent = QaDesignKeyword.TEST_POINT.getName() + ": " + testPoint.getName().replace("\n", "\\\n");
                    writer.write(testPointLineContent);
                    writer.write("\n");
                    for (QaDesignTestCase testCase : testPoint.getTestCases()) {
                        String testCaseLineContent = "\t" + "测试案例" + ": " + testCase.getName().replace("\n", "\\\n");
                        writer.write(testCaseLineContent);
                        writer.write("\n");
                        String testCaseDescLineContent = "\t\t" + QaDesignKeyword.TEST_CASE_DESC.getName() + ": " + testCase.getDesc().replace("\n", "\\\n");
                        writer.write(testCaseDescLineContent);
                        writer.write("\n");
                        String testCaseDataLineContent = "\t\t" + QaDesignKeyword.TEST_CASE_DATA.getName() + ": " + testCase.getData().replace("\n", "\\\n");
                        writer.write(testCaseDataLineContent);
                        writer.write("\n");
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
    }

    private List<QaDesignEntity> parseExcelData(List<List<String>> data) {
        List<QaDesignEntity> entities = new ArrayList<>();
        for(List<String> row : data) {
            if(row.size() < QaDesignKeyword.values().length) {
                QaDesignNotifier.notifyError(null, "Cannot get required data from import excel!");
                return new ArrayList<>();
            }
            int req_index = 0;
            for(; req_index < entities.size(); req_index++) {
                if(entities.get(req_index).getRequirement().equals(row.get(QaDesignKeyword.REQUIREMENT.getIndex()))) {
                    break;
                }
            }
            QaDesignEntity entity;
            if(req_index < entities.size()) {
                entity = entities.get(req_index);
            } else {
                entity = new QaDesignEntity();
                entity.setRequirementId(row.get(QaDesignKeyword.REQUIREMENT_ID.getIndex()));
                entity.setRequirement(row.get(QaDesignKeyword.REQUIREMENT.getIndex()));
                entities.add(entity);
            }

            if(entity.getTestPoints() == null) {
                entity.setTestPoints(new ArrayList<>());
            }
            int i = 0;
            for(; i < entity.getTestPoints().size(); i++) {
                if(entity.getTestPoints().get(i).getName().equals(row.get(QaDesignKeyword.TEST_POINT.getIndex()))) {
                    break;
                }
            }
            QaDesignTestCase testcase = new QaDesignTestCase(
                    row.get(QaDesignKeyword.TEST_CASE.getIndex()),
                    row.get(QaDesignKeyword.TEST_CASE_DESC.getIndex()),
                    row.get(QaDesignKeyword.TEST_CASE_DATA.getIndex()),
                    row.get(QaDesignKeyword.TEST_CASE_STEP.getIndex()),
                    row.get(QaDesignKeyword.TEST_CASE_EXPECT.getIndex())
            );
            if(i < entity.getTestPoints().size()) {
                entity.getTestPoints().get(i).getTestCases().add(testcase);
            } else {
                QaDesignTestPoint testPoint = new QaDesignTestPoint();
                testPoint.setName(row.get(QaDesignKeyword.TEST_POINT.getIndex()));
                List<QaDesignTestCase> testCases = new ArrayList<>();
                testCases.add(testcase);
                testPoint.setTestCases(testCases);
                entity.getTestPoints().add(testPoint);
            }
        }
        return entities;
    }

}
