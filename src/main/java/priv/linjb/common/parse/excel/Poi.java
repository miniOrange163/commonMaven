package priv.linjb.common.parse.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Poi {
	
  
    private Sheet sheet;
    LinkedList[] result;

    private void loadExcel(String filePath) {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(new File(filePath));
            Workbook workBook = WorkbookFactory.create(inStream);
            sheet = workBook.getSheetAt(0);         
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(inStream!=null){
                    inStream.close();
                }                
            } catch (IOException e) {                
                e.printStackTrace();
            }
        }
    }

    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:{
                	try{
                		cellValue = String.valueOf(cell.getNumericCellValue());
                	}catch(IllegalStateException e){
                		cellValue = String.valueOf(cell.getRichStringCellValue());
                	}
                	
                }
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = "";
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        return cellValue.trim();
    }



    
    public  void init(){
    	int rowNum = sheet.getLastRowNum() + 1;
    	result = new LinkedList[rowNum];
    	for(int i=0;i<rowNum;i++){
    		Row row = sheet.getRow(i);
    		result[i] = new LinkedList();
    		for(int j=0;j<row.getLastCellNum();j++){
    			Cell cell = row.getCell(j);
    			String str = getCellValue(cell);
    			result[i].add(str);
    		}
    	}
    }
    public void show(){
    	for(int i=0;i<result.length;i++){
    		for(int j=0;j<result[i].size();j++){
    			System.out.print(result[i].get(j) + "\t");
    		}
    		System.out.println();
    	}
    }
    public static void main(String[] args) {
        Poi poi = new Poi();
        poi.loadExcel("d:/temp/2016.xls");
        poi.init();
        poi.show();
    }
	
}
