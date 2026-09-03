package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;

public class ExcelUtils {

    public static Object[][] getExcelData(String resourcePath, String sheetName) {
        Object[][] data = null;
        try {
            // resourcePath example: "testdata/users.xlsx"
            InputStream is = ExcelUtils.class.getClassLoader()
                    .getResourceAsStream(resourcePath);

            if (is == null) {
                throw new RuntimeException("Excel file not found: " + resourcePath);
            }

            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getLastRowNum();
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        data[i - 1][j] = "";
                    } else {
                        data[i - 1][j] = cell.toString();
                    }
                }
            }
            workbook.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading Excel file", e);
        }
        return data;
    }
}