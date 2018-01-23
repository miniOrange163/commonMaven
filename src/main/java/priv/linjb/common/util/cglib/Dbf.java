package priv.linjb.common.util.cglib;

import java.io.FileInputStream;
import java.io.InputStream;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

public class Dbf {

	private void ExportDbf(){
		
	}
	private static void ImportDbf(){
		String path ="D:/test/score.DBF";
		InputStream fis = null;
        try 
        {  
            //读取文件的输入流 
            fis  = new FileInputStream(path);
            //根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);  
            reader.setCharactersetName("GBK");
            
            //调用DBFReader对实例方法得到path文件中字段的个数 
            int fieldsCount = reader.getFieldCount();
            System.out.println("字段数:"+fieldsCount);
            //取出字段信息   
            for( int i=0; i<fieldsCount; i++)    
            {   
              DBFField field = reader.getField(i);
             
              System.out.println(field.getName());
            }   
            
            Object[] rowValues;   
            //一条条取出path文件中记录   
            while((rowValues = reader.nextRecord()) != null) 
            {   
              for( int i=0; i<rowValues.length; i++) 
              {   
                System.out.println(rowValues[i]); 
              }   
            }   
          }   
          catch(Exception e)    
          {   
          e.printStackTrace();  
          }  
          finally  
          {   
          try{   
               fis.close(); 
          }catch(Exception e){}  
          } 
	}
	
	public static void main(String[] args) {
		
		ImportDbf();
	}
}
