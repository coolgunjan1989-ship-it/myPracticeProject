package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExcelWrite {

    private static final String inputSheet = System.getProperty("user.dir") + "\\config\\ExcelFiles\\inputData.xlsx";
    private static final String sheetName = "Response";

    public static void writeInputData(Map<String, String> inputData) throws IOException {
        File file = new File(inputSheet);

        // 1. READ phase
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        // Map the headers to their column indices
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> columnMap = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            columnMap.put(headerRow.getCell(i).getStringCellValue(), i);
        }

        // 2. MODIFY phase (Create a NEW row at the end)
        int nextRowIndex = sheet.getLastRowNum() + 1;
        Row newRow = sheet.createRow(nextRowIndex);

        inputData.forEach((key, value) -> {
            if (columnMap.containsKey(key)) {
                int colIndex = columnMap.get(key);
                newRow.createCell(colIndex).setCellValue(value);
            }
        });

        // Close input stream before writing
        inputStream.close();

        // 3. WRITE phase
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);

        // Cleanup
        workbook.close();
        outputStream.close();

        System.out.println("Data appended to row index: " + nextRowIndex);
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> data = new HashMap<>();
        data.put("id", "4"); // Assuming "id" is a header in your Excel
        writeInputData(data);
    }
}