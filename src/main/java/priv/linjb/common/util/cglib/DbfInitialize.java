package priv.linjb.common.util.cglib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

public class DbfInitialize {

	List<String> fieldList = new ArrayList();
	DbfInitialize(){}
	public String typeParser(byte type){

		switch(type){
			case 67:return String.class.getName();
			case 78:return Float.class.getName();
			default: return "";
		}
	}
	public Object valueParser(Entry entry,Object obj){
		String type =  entry.getValue().toString();
		
		if("class java.lang.String".equals(type))
				return obj;
		else if("class java.lang.Float".equals(type)){
			if(obj==null)
				return null;
			return Float.parseFloat(obj.toString().trim());
		}
		else
			return null;
			
	}
	public void init() throws ClassNotFoundException, IOException{
		Map propertyMap = new LinkedHashMap();
		
		String path ="D:/test/score.DBF";
		InputStream fis = null;
       
            //读取文件的输入流 
            fis  = new FileInputStream(path);
            //根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);  
            reader.setCharactersetName("GBK");
            
            //调用DBFReader对实例方法得到path文件中字段的个数 
            int fieldsCount = reader.getFieldCount();
            System.out.println("字段数:"+fieldsCount);
            //取出字段信息   
            String fieldName;
            byte type;
            for( int i=0; i<fieldsCount; i++)    
            {   
              DBFField field = reader.getField(i);
               fieldName = field.getName();
               type = field.getDataType();
              String typeStr = typeParser(type);
              propertyMap.put(fieldName, Class.forName(typeStr));
              fieldList.add(typeStr);
              System.out.println(fieldName +"----" +typeParser(type));
              
            }
           System.out.println("--------------------------------------------------");
            parse(reader,propertyMap);
            fis.close();
	}
	public List parse( DBFReader reader,Map propertyMap) throws DBFException{
		
		 List objList = new ArrayList();
         
         
         Object[] rowValues;   
         
         Iterator<Entry> iter = propertyMap.entrySet().iterator();
    
         while((rowValues = reader.nextRecord()) != null) 
         {   
        	 CglibBeanUtil bean = new CglibBeanUtil(propertyMap);
           for( int i=0; i<rowValues.length; i++) 
           {   
         	  if(iter.hasNext()){
         		 Entry entry = iter.next();
	         	 Object obj = valueParser(entry, rowValues[i]);
	         	
	         	 System.out.println(obj);
	         	   
	         	 bean.setValue((String)entry.getKey(), obj);
         	  }
             
           }   
           		objList.add(bean.getObject());
         }
         System.out.println(objList.size());
 
         return objList;
	}
	public static void main(String[] args) throws Exception {
		DbfInitialize dbf = new DbfInitialize();
		
		dbf.init();
	}
}
