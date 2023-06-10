package vip.testops.qa_design.actions;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.PsiErrorElementUtil;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.QaDesignNotifier;
import vip.testops.qa_design.actions.ui.ExportDialog;
import vip.testops.qa_design.lang.psi.QaDesignTypes;
import vip.testops.qa_design.lang.psi.impl.*;
import vip.testops.qa_design.settings.ExportAndImportSettings;
import vip.testops.qa_design.settings.FieldMapping;
import vip.testops.qa_design.utils.ExcelUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ExportAction extends AnAction {

    private final ExportAndImportSettings settings = ExportAndImportSettings.getSettings();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        List<PsiFile> qaDesignFiles = new ArrayList<>();
        Object obj = e.getData(CommonDataKeys.PSI_ELEMENT);
        String defaultExportFileName = "unnamed.xlsx";
        if (obj instanceof PsiDirectory directory) {
            String dirName = directory.getName();
            defaultExportFileName = dirName + ".xlsx";
            qaDesignFiles = getPsiFiles(directory, psiFile -> psiFile.getFileType().getName().equals(QaDesignBundle.message("fileType.qa_design.name")));

        } else if (obj instanceof PsiFile file) {
            if (file.getFileType().getName().equals(QaDesignBundle.message("fileType.qa_design.name"))) {
                qaDesignFiles.add(file);
                String suffix = "." + QaDesignBundle.message("fileType.qa_design.extension");
                defaultExportFileName = file.getName().substring(0,file.getName().length()-suffix.length()) + ".xlsx";
            }
        }
        if(qaDesignFiles.size() == 0) {
            return;
        }

        List<FieldMapping> fieldMappings = settings.getFieldMappings();
        ExportDialog dialog = new ExportDialog(e.getProject());

        if(dialog.showAndGet()) {
            StringBuilder errorMessages = new StringBuilder();
            String path = dialog.getExportPath();
            boolean isOverwrite = dialog.isOverwrite();
            if(path == null || path.isEmpty()) {
                path = e.getProject() == null ? "." : e.getProject().getBasePath();
            }
            assert path != null;
            File export_file;
            if (new File(path).isDirectory()) {
                export_file = new File(path, defaultExportFileName);
            } else if (path.toLowerCase().endsWith(".xlsx")) {
                export_file = new File(path);
            }else if (path.toLowerCase().endsWith(".xls")) {
                export_file = new File(path);
            } else {
                export_file = new File(path + ".xlsx");
            }

            try {
                ExcelUtils excelUtils = new ExcelUtils(export_file, !isOverwrite);
                for(PsiFile file : qaDesignFiles) {
                    if(PsiErrorElementUtil.hasErrors(Objects.requireNonNull(e.getProject()), file.getVirtualFile())) {
                        errorMessages.append(file.getVirtualFile().getPath()).append("\n");
                        continue;
                    }
                    List<List<String>> data = parseQaDesignElementTree(file);
                    excelUtils.write(fieldMappings, data);

                }
                excelUtils.saveAndClose();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } finally {
                if(errorMessages.length() > 0) {
                    errorMessages.append("\nThese files were skipped.");
                    errorMessages.insert(0,"Files in below were not exported as these files contain errors:\n");
                    QaDesignNotifier.notifyError(e.getProject(), errorMessages.toString());
                } else {
                    QaDesignNotifier.notifyInfo(e.getProject(), "Export successfully!");
                }
            }

        }
    }

    private List<PsiFile> getPsiFiles(PsiDirectory directory, Predicate<PsiFile> filter) {
        List<PsiFile> psiFiles = new ArrayList<>();
        for(PsiFile file : directory.getFiles()) {
            if(filter.test(file)) {
                psiFiles.add(file);
            }
        }
        for(PsiDirectory subDir : directory.getSubdirectories()) {
            psiFiles.addAll(getPsiFiles(subDir, filter));
        }
        return psiFiles;
    }

    private List<List<String>> parseQaDesignElementTree(PsiElement root) {
        List<List<String>> result = new ArrayList<>();
        String filename = root.getContainingFile().getName();
        String requirementId = filename.substring(0,filename.length()-3);
        String requirement = "";
        ASTNode firstLineNode = root.getNode().findChildByType(QaDesignTypes.RULE_FIRST_LINE);
        if(firstLineNode != null) {
            requirement = ((QaDesignRuleFirstLineImpl)firstLineNode.getPsi()).getContent();
        }
        ASTNode[] testPointDesignNodes = root.getNode().getChildren(TokenSet.create(QaDesignTypes.RULE_TEST_POINT_DESIGN));
        for(ASTNode testPointDesignNode : testPointDesignNodes) {
            String testPoint = ((QaDesignRuleTestPointDesignImpl)testPointDesignNode.getPsi()).getContent();
            ASTNode[] testCaseDesignNodes = testPointDesignNode.getChildren(TokenSet.create(QaDesignTypes.RULE_TEST_CASE_DESIGN));
            for(ASTNode testCaseDesignNode : testCaseDesignNodes) {
                List<String> testCase = new ArrayList<>();
                testCase.add(requirementId);
                testCase.add(requirement);
                testCase.add(testPoint);
                testCase.add(((QaDesignRuleTestCaseDesignImpl)testCaseDesignNode.getPsi()).getContent());
                ASTNode testCaseDescNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_DESC);
                if(testCaseDescNode != null) {
                    testCase.add(((QaDesignRuleTestCaseDescImpl)testCaseDescNode.getPsi()).getContent());
                } else {
                    testCase.add("");
                }

                ASTNode testCaseDataNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_DATA);
                if(testCaseDataNode != null) {
                    testCase.add(((QaDesignRuleTestCaseDataImpl)testCaseDataNode.getPsi()).getContent());
                } else {
                    testCase.add("");
                }

                ASTNode testCaseStepNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_STEP);
                if(testCaseStepNode != null) {
                    testCase.add(((QaDesignRuleTestCaseStepImpl)testCaseStepNode.getPsi()).getContent());
                } else {
                    testCase.add("");
                }

                ASTNode testCaseExpectNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_EXPECT);
                if(testCaseExpectNode != null) {
                    testCase.add(((QaDesignRuleTestCaseExpectImpl)testCaseExpectNode.getPsi()).getContent());
                } else {
                    testCase.add("");
                }
                result.add(testCase);
            }
        }
        return result;
    }
}
