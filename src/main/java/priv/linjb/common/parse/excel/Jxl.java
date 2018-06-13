package priv.linjb.common.parse.excel;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class Jxl {
	 public   static   void  main(String args[])   {   
	        try    {


//	           Workbook book  =  Workbook.getWorkbook( new  File( "d:/temp/2016.xls" ));
				Workbook book  =  Workbook.getWorkbook( new  File( "e:/000108新兴_66套.xls" ));
				//  获得第一个工作表对象
	            Sheet sheet  =  book.getSheet( 0 );
	            //  得到第一列第一行的单元格
	            int row = sheet.getRows();
	            int col = sheet.getColumns();
	            System.out.println("行："+ row);
	            System.out.println("列："+ col);

	           String[][] result = new String[row][col];
	           for(int i =0;i<row;i++){
				   for(int j=0;j<col;j++){
					   Cell cell =  sheet.getCell(j,i);
					   result[i][j] = cell.getContents();
				   }
			   }

	           
	           for(int i =0;i<row;i++){
	        	   for(int j=0;j<col;j++){
	        		 System.out.print(result[i][j]+"\t");
	        	   
	        	   }
	        	   System.out.println();
	           }

	           
	           book.close();   
	       }   catch  (Exception e)   {   
	           //System.out.println(e);   
	        e.printStackTrace();
	       }    
	    }    
}
