package vip.testops.qa_design.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vip.testops.qa_design.actions.entities.QaDesignEntity;
import vip.testops.qa_design.actions.entities.QaDesignTestCase;
import vip.testops.qa_design.actions.entities.QaDesignTestPoint;
import vip.testops.qa_design.lang.QaDesignKeyword;
import vip.testops.qa_design.settings.FieldMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class ExcelUtils {
    private final Workbook workbook;
    private final File file;
    private final Sheet sheet;

    private final boolean expand;

    public ExcelUtils(File file, boolean expand) throws IOException {
        this.file = file;
        if(expand && file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            this.workbook = WorkbookFactory.create(fis);
            this.sheet = workbook.getSheetAt(0);

        } else if (file.getName().toLowerCase().endsWith(".xlsx")) {
            this.workbook = new XSSFWorkbook();
            this.sheet = workbook.createSheet();
        } else {
            this.workbook = new HSSFWorkbook();
            this.sheet = workbook.createSheet();
        }
        this.expand = expand;
    }

    public void write(List<FieldMapping> fieldMappings, QaDesignEntity data) {
        int lastRowNum = this.sheet.getLastRowNum();
        if(lastRowNum == -1) {
            writeTitle(fieldMappings);
            lastRowNum++;
        }
        int rowCount = 0;
        FieldMapping featureField = getFieldMapping(QaDesignKeyword.FEATURE, fieldMappings);
        if(featureField == null) {
            throw new RuntimeException("cannot find feature keyword mapping in settings");
        }
        FieldMapping testPointField = getFieldMapping(QaDesignKeyword.TEST_POINT, fieldMappings);
        if(testPointField == null) {
            throw  new RuntimeException("cannot find test point keyword mapping in settings");
        }
        FieldMapping testCaseNameField = getFieldMapping(QaDesignKeyword.TEST_CASE, fieldMappings);
        FieldMapping testCaseDescField = getFieldMapping(QaDesignKeyword.TEST_CASE_DESC, fieldMappings);
        FieldMapping testCaseDataField = getFieldMapping(QaDesignKeyword.TEST_CASE_DATA, fieldMappings);
        FieldMapping testCaseStepField = getFieldMapping(QaDesignKeyword.TEST_CASE_STEP, fieldMappings);
        FieldMapping testCaseExpectField = getFieldMapping(QaDesignKeyword.TEST_CASE_EXPECT, fieldMappings);
        FieldMapping testCaseLinkField = getFieldMapping(QaDesignKeyword.TEST_CASE_LINK, fieldMappings);
        List<FieldMapping> testCaseTagFields = getFieldMappings(QaDesignKeyword.TEST_CASE_TAG, fieldMappings);
        String feature = data.getFeature();
        for(int i = 0; i < data.getTestPoints().size(); i++) {
            String testPoint = data.getTestPoints().get(i).getName();
            for (int j = 0; j < data.getTestPoints().get(i).getTestCases().size(); j++) {
                Row row = this.sheet.createRow(lastRowNum + rowCount + 1);
                rowCount++;
                row.createCell(featureField.getColumnNum()).setCellValue(feature);
                row.createCell(testPointField.getColumnNum()).setCellValue(testPoint);
                if (testCaseNameField != null) {
                    String value = data.getTestPoints().get(i).getTestCases().get(j).getName();
                    row.createCell(testCaseNameField.getColumnNum()).setCellValue(value == null ? "" : value);
                }
                if (testCaseDescField != null) {
                    String value = data.getTestPoints().get(i).getTestCases().get(j).getDesc();
                    row.createCell(testCaseDescField.getColumnNum()).setCellValue(value == null ? "" : value);
                }
                if (testCaseDataField != null) {
                    String value = data.getTestPoints().get(i).getTestCases().get(j).getData();
                    row.createCell(testCaseDataField.getColumnNum()).setCellValue(value == null ? "" : value);
                }
                if (testCaseStepField != null) {
                    String value = data.getTestPoints().get(i).getTestCases().get(j).getStep();
                    row.createCell(testCaseStepField.getColumnNum()).setCellValue(value == null ? "" : value);
                }
                if (testCaseExpectField != null) {
                    String value = data.getTestPoints().get(i).getTestCases().get(j).getExpect();
                    row.createCell(testCaseExpectField.getColumnNum()).setCellValue(value == null ? "" : value);
                }
                if (testCaseLinkField != null) {
                    String value = data.getTestPoints().get(i).getTestCases().get(j).getLink();
                    row.createCell(testCaseLinkField.getColumnNum()).setCellValue(value == null ? "" : value);
                }
                if(testCaseTagFields != null && !testCaseTagFields.isEmpty()) {
                    int index = 0;
                    for (String tagValue : data.getTestPoints().get(i).getTestCases().get(j).getTags()) {
                        FieldMapping tagField = testCaseTagFields.get(index++);
                        row.createCell(tagField.getColumnNum()).setCellValue(tagValue == null ? "" : tagValue);
                    }
                }
            }

        }
    }

    public void writeTitle(List<FieldMapping> fieldMappings) {
        Row row = this.sheet.createRow(0);
        for(int i = 0; i < fieldMappings.size(); i++) {
            FieldMapping fieldMapping = fieldMappings.get(i);
            Cell cell = row.createCell(i);
            cell.setCellValue(fieldMapping.getFieldName());
        }
    }

    public List<QaDesignEntity> read(List<FieldMapping> fieldMappings) {
        List<QaDesignEntity> data = new ArrayList<>();
        int lastRowNum = this.sheet.getLastRowNum();
        for(int i = 1; i <= lastRowNum; i++) {
            Row row = this.sheet.getRow(i);
            FieldMapping featureField = getFieldMapping(QaDesignKeyword.FEATURE, fieldMappings);
            if (featureField == null) {
                throw new RuntimeException("cannot find feature keyword mapping in settings");
            }
            String feature = getCellValueAsString(row.getCell(featureField.getColumnNum()));
            QaDesignEntity entity = data.stream()
                    .filter(item -> item.getFeature().equals(feature))
                    .findFirst()
                    .orElseGet(() -> {
                        QaDesignEntity qaDesignEntity = new QaDesignEntity(feature);
                        data.add(qaDesignEntity);
                        return qaDesignEntity;
                    });

            FieldMapping testPointField = getFieldMapping(QaDesignKeyword.TEST_POINT, fieldMappings);
            if (testPointField == null) {
                throw  new RuntimeException("cannot find test point keyword mapping in settings");
            }
            String testPoint = getCellValueAsString(row.getCell(testPointField.getColumnNum()));
            QaDesignTestPoint testPointEntity = entity.getTestPoints()
                    .stream()
                    .filter(item -> item.getName().equals(testPoint))
                    .findFirst()
                    .orElseGet(() -> {
                        QaDesignTestPoint qaDesignTestPoint = new QaDesignTestPoint(testPoint);
                        entity.getTestPoints().add(qaDesignTestPoint);
                        return qaDesignTestPoint;
                    });
            // begin to construct testcase entity
            QaDesignTestCase testCase = new QaDesignTestCase();
            // get test case name
            FieldMapping testCaseNameField = getFieldMapping(QaDesignKeyword.TEST_CASE, fieldMappings);
            String testCaseName = testCaseNameField == null ? "" : getCellValueAsString(row.getCell(testCaseNameField.getColumnNum()));
            testCase.setName(testCaseName);
            // get test case desc
            FieldMapping testCaseDescField = getFieldMapping(QaDesignKeyword.TEST_CASE_DESC, fieldMappings);
            String testCaseDesc = testCaseDescField == null ? "" : getCellValueAsString(row.getCell(testCaseDescField.getColumnNum()));
            testCase.setDesc(testCaseDesc);
            // get test case data
            FieldMapping testCaseDataField = getFieldMapping(QaDesignKeyword.TEST_CASE_DATA, fieldMappings);
            String testCaseData = testCaseDataField == null ? "" : getCellValueAsString(row.getCell(testCaseDataField.getColumnNum()));
            testCase.setData(testCaseData);
            // get test case step
            FieldMapping testCaseStepField = getFieldMapping(QaDesignKeyword.TEST_CASE_STEP, fieldMappings);
            String testCaseStep = testCaseStepField == null ? "" : getCellValueAsString(row.getCell(testCaseStepField.getColumnNum()));
            testCase.setStep(testCaseStep);
            // get test case expect
            FieldMapping testCaseExpectField = getFieldMapping(QaDesignKeyword.TEST_CASE_EXPECT, fieldMappings);
            String testCaseExpect = testCaseExpectField == null ? "" : getCellValueAsString(row.getCell(testCaseExpectField.getColumnNum()));
            testCase.setExpect(testCaseExpect);
            // get test case link
            FieldMapping testCaseLinkField = getFieldMapping(QaDesignKeyword.TEST_CASE_LINK, fieldMappings);
            String testCaseLink = testCaseLinkField == null ? "" : getCellValueAsString(row.getCell(testCaseLinkField.getColumnNum()));
            testCase.setLink(testCaseLink);
            // get tags
            List<FieldMapping> testCaseTagFields = getFieldMappings(QaDesignKeyword.TEST_CASE_TAG, fieldMappings);
            Set<String> caseTags = new LinkedHashSet<>();
            for (FieldMapping testCaseTagField : testCaseTagFields) {
                String v = getCellValueAsString(row.getCell(testCaseTagField.getColumnNum()));
                if (v != null && !v.equals("")) {
                    caseTags.add(v);
                }
            }
            testCase.setTags(caseTags);
            testPointEntity.getTestCases().add(testCase);
        }
        return data;
    }

    public void saveAndClose() throws IOException {
        try(FileOutputStream fos = new FileOutputStream(this.file)) {
            this.workbook.write(fos);
        } finally {
            this.workbook.close();
        }
    }

    public void close() throws IOException {
        this.workbook.close();
    }

    private FieldMapping getFieldMapping(QaDesignKeyword qaDesignKeyword, List<FieldMapping> fieldMappings) {
        return fieldMappings.stream()
                .filter(item -> item.getQaDesignKeyword().equals(qaDesignKeyword.getName()))
                .findFirst()
                .orElse(null);
    }

    private List<FieldMapping> getFieldMappings(QaDesignKeyword qaDesignKeyword, List<FieldMapping> fieldMappings) {
        return fieldMappings.stream()
                .filter(item -> item.getQaDesignKeyword().equals(qaDesignKeyword.getName()))
                .collect(Collectors.toList());
    }

    private String getCellValueAsString(Cell cell) {
        if(cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case BLANK:
            default:
                return "";
        }
    }

}
