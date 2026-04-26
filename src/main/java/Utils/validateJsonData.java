package Utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class validateJsonData {

    YamlReader ymlReader = new YamlReader("config\\YmlFiles\\local.yml");
    private final String inputExcelPath = System.getProperty("user.dir") + ymlReader.getParamValue("inputExcelPath");
    private final String sheetName = ymlReader.getParamValue("sheetName");

    public Map<String, Map<String,String>> getExcelData() throws IOException {
        Map<String, Map<String,String>> excelData = new HashMap<>();
        File file = new File(inputExcelPath);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName);

        // Map the headers to their column indices
        Row headerRow = sheet.getRow(0);
        Map<String, Integer> columnMap = new HashMap<>();
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            columnMap.put(headerRow.getCell(i).getStringCellValue(), i);
        }

        int columnForId = columnMap.get("id");
        int columnForName = columnMap.get("name");
        int columnForEmail = columnMap.get("email");
        int columnForCity = columnMap.get("city");
        int columnForZip = columnMap.get("zip");
        int columnForRole = columnMap.get("role");

        String id,name,email,city,zip,role;
        int lastRow = sheet.getLastRowNum();
        for(int i =1; i<lastRow; i++){
            Map<String,String> temp = new HashMap<>();
            id = sheet.getRow(i).getCell(columnForId).getStringCellValue();
            name = sheet.getRow(i).getCell(columnForName).getStringCellValue();
            email = sheet.getRow(i).getCell(columnForEmail).getStringCellValue();
            city = sheet.getRow(i).getCell(columnForCity).getStringCellValue();
            zip = sheet.getRow(i).getCell(columnForZip).getStringCellValue();
            role = sheet.getRow(i).getCell(columnForRole).getStringCellValue();
            temp.put("name", name);
            temp.put("email", email);
            temp.put("city", city);
            temp.put("zip", zip);
            temp.put("role", role);
            excelData.put(id, temp);
        }

        return excelData;
    }

    public void validate(Map<String, Map<String,String>> excelData ){

    }
}
