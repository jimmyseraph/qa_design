package vip.testops.qa_design.actions;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.PsiErrorElementUtil;
import org.jetbrains.annotations.NotNull;
import vip.testops.qa_design.QaDesignBundle;
import vip.testops.qa_design.QaDesignNotifier;
import vip.testops.qa_design.actions.entities.QaDesignEntity;
import vip.testops.qa_design.actions.entities.QaDesignTestCase;
import vip.testops.qa_design.actions.entities.QaDesignTestPoint;
import vip.testops.qa_design.actions.ui.ExportDialog;
import vip.testops.qa_design.lang.psi.QaDesignRuleLinkedMethod;
import vip.testops.qa_design.lang.psi.QaDesignRuleTag;
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
                    QaDesignEntity data = parseQaDesignElementTree(file);
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

    private QaDesignEntity parseQaDesignElementTree(PsiElement root) {
        QaDesignEntity result = new QaDesignEntity();
        String filename = root.getContainingFile().getName();
        String feature = "";
        ASTNode firstLineNode = root.getNode().findChildByType(QaDesignTypes.RULE_FIRST_LINE);
        if(firstLineNode != null) {
            feature = ((QaDesignRuleFirstLineImpl)firstLineNode.getPsi()).getContent();
            result.setFeature(feature);
        }
        ASTNode[] testPointDesignNodes = root.getNode().getChildren(TokenSet.create(QaDesignTypes.RULE_TEST_POINT_DESIGN));
        List<QaDesignTestPoint> testPoints = new ArrayList<>();
        result.setTestPoints(testPoints);
        for(ASTNode testPointDesignNode : testPointDesignNodes) {
            String testPoint = ((QaDesignRuleTestPointDesignImpl)testPointDesignNode.getPsi()).getContent();
            QaDesignTestPoint qaDesignTestPoint = new QaDesignTestPoint(testPoint);
            testPoints.add(qaDesignTestPoint);
            ASTNode[] testCaseDesignNodes = testPointDesignNode.getChildren(TokenSet.create(QaDesignTypes.RULE_TEST_CASE_DESIGN));
            List<QaDesignTestCase> testCases = new ArrayList<>();
            qaDesignTestPoint.setTestCases(testCases);
            for(ASTNode testCaseDesignNode : testCaseDesignNodes) {
                QaDesignTestCase testCase = new QaDesignTestCase();
                PsiElement linkElement = getPrevElement(testCaseDesignNode.getPsi(), testCaseDesignNode.getElementType(), QaDesignTypes.RULE_LINKED_METHOD);
                PsiElement tagElement = getPrevElement(testCaseDesignNode.getPsi(), testCaseDesignNode.getElementType(), QaDesignTypes.RULE_TAG);
                if (linkElement != null) {
                    String linkValue = ((QaDesignRuleLinkedMethod)linkElement).getValue();
                    testCase.setLink(linkValue);
                }
                if (tagElement != null) {
                    String tagValues = ((QaDesignRuleTag)tagElement).getValue();
                    for (String tagValue : tagValues.split(",")) {
                        testCase.addTag(tagValue);
                    }
                }

                testCase.setName(((QaDesignRuleTestCaseDesignImpl)testCaseDesignNode.getPsi()).getContent());
                ASTNode testCaseDescNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_DESC);
                if(testCaseDescNode != null) {
                    String name = ((QaDesignRuleTestCaseDescImpl)testCaseDescNode.getPsi()).getName();
                    String testCaseDesc = (testCaseDescNode.getPsi()).getOriginalElement().getText();
                    assert name != null;
                    testCaseDesc = testCaseDesc.substring(testCaseDesc.indexOf(name)+name.length());
                    testCaseDesc = testCaseDesc.substring(testCaseDesc.indexOf(":")+1);
                    testCaseDesc = testCaseDesc.replace("\\", "");
                    testCase.setDesc(testCaseDesc);
                } else {
                    testCase.setDesc("");
                }

                ASTNode testCaseDataNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_DATA);
                if(testCaseDataNode != null) {
                    String name = ((QaDesignRuleTestCaseDataImpl)testCaseDataNode.getPsi()).getName();
                    String testCaseData = (testCaseDataNode.getPsi()).getOriginalElement().getText();
                    assert name != null;
                    testCaseData = testCaseData.substring(testCaseData.indexOf(name)+name.length());
                    testCaseData = testCaseData.substring(testCaseData.indexOf(":")+1);
                    testCaseData = testCaseData.replace("\\", "");
                    testCase.setData(testCaseData);
                } else {
                    testCase.setData("");
                }

                ASTNode testCaseStepNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_STEP);
                if(testCaseStepNode != null) {
                    String name = ((QaDesignRuleTestCaseStepImpl)testCaseStepNode.getPsi()).getName();
                    String testCaseStep = (testCaseStepNode.getPsi()).getOriginalElement().getText();
                    assert name != null;
                    testCaseStep = testCaseStep.substring(testCaseStep.indexOf(name)+name.length());
                    testCaseStep = testCaseStep.substring(testCaseStep.indexOf(":")+1);
                    testCaseStep = testCaseStep.replace("\\", "");
                    testCase.setStep(testCaseStep);
                } else {
                    testCase.setStep("");
                }

                ASTNode testCaseExpectNode = testCaseDesignNode.findChildByType(QaDesignTypes.RULE_TEST_CASE_EXPECT);
                if(testCaseExpectNode != null) {
                    String name = ((QaDesignRuleTestCaseExpectImpl)testCaseExpectNode.getPsi()).getName();
                    String testCaseExpect = (testCaseExpectNode.getPsi()).getOriginalElement().getText();
                    assert name != null;
                    testCaseExpect = testCaseExpect.substring(testCaseExpect.indexOf(name)+name.length());
                    testCaseExpect = testCaseExpect.substring(testCaseExpect.indexOf(":")+1);
                    testCaseExpect = testCaseExpect.replace("\\", "");
                    testCase.setExpect(testCaseExpect);
                } else {
                    testCase.setExpect("");
                }
                testCases.add(testCase);
            }
        }
        return result;
    }

    private PsiElement getPrevElement(PsiElement element, IElementType stopType, IElementType findType) {
        PsiElement prev = element.getPrevSibling();
        while (prev != null && prev.isValid() && prev.getNode().getElementType() != stopType) {
            if (prev.getNode().getElementType() == findType) {
                return prev;
            }
            prev = prev.getPrevSibling();
        }
        return null;
    }

}
