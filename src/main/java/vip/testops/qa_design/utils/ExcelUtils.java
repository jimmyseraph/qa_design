package vip.testops.qa_design.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vip.testops.qa_design.lang.QaDesignKeyword;
import vip.testops.qa_design.settings.FieldMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void write(List<FieldMapping> fieldMappings, List<List<String>> data) {
        int lastRowNum = this.sheet.getLastRowNum();
        if(lastRowNum == -1) {
            writeTitle(fieldMappings);
            lastRowNum++;
        }
        for(int i = 0; i < data.size(); i++) {
            Row row = this.sheet.createRow(lastRowNum + i + 1);
            List<String> rowData = data.get(i);
            for(int j = 0; j < fieldMappings.size(); j++) {
                Cell cell = row.createCell(j);
                QaDesignKeyword keyword = QaDesignKeyword.getKeyword(fieldMappings.get(j).qaDesignKeyword);
                cell.setCellValue(keyword == null ? "" : rowData.get(keyword.getIndex()));
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

    public List<List<String>> read(List<FieldMapping> fieldMappings) {
        List<List<String>> data = new ArrayList<>();
        int lastRowNum = this.sheet.getLastRowNum();
        for(int i = 1; i <= lastRowNum; i++) {
            Row row = this.sheet.getRow(i);
            if(row.getLastCellNum() < fieldMappings.size()) {
                break;
            }
            String[] rowData = new String[QaDesignKeyword.values().length];
            for(int j = 0; j < fieldMappings.size(); j++) {
                if(fieldMappings.get(j).getQaDesignKeyword() == null || fieldMappings.get(j).getQaDesignKeyword().isEmpty()) {
                    continue;
                }
                QaDesignKeyword keyword = QaDesignKeyword.getKeyword(fieldMappings.get(j).getQaDesignKeyword());
                if (keyword == null) {
                    continue;
                }
                Cell cell = row.getCell(j);
                rowData[keyword.getIndex()] = getCellValueAsString(cell);
            }
            data.add(List.of(rowData));
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
