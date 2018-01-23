package priv.linjb.common.parse.importDBF;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;


import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
public class DbfImport {

	
	private String table;			//表格名称;
	private StringBuffer params;		//记录表格各字段
	private StringBuffer values;	//记录每一行数据
	private List sql;				//记录所有行的数据
	
	private static Connection con = null;
	private static Statement stm = null;
	private static ResultSet res = null; 
	 
	 
	public DbfImport() {
		// TODO Auto-generated constructor stub
		table ="ks_view";
		params  = new StringBuffer("(");
		values = new StringBuffer("(");
		sql = new ArrayList<String>();
	}
	public String typeParser(byte type){
	
		switch(type){
			case 67:return String.class.getName();
			case 78:return Float.class.getName();
			default: return "";
		}
	}
	public void impOracle(String path) throws ClassNotFoundException, IOException, SQLException{
		Map propertyMap = new LinkedHashMap();
		
		
		InputStream fis = null;
       
            //读取文件的输入流 
            fis  = new FileInputStream(path);
            //根据输入流初始化一个DBFReader实例，用来读取DBF文件信息
            DBFReader reader = new DBFReader(fis);  
            //设置字符，避免中文乱码
            reader.setCharactersetName("GBK");
            
            //获取dbf数据字段数量
            int fieldsCount = reader.getFieldCount();
   
         
            String fieldName;
            byte type;
            for( int i=0; i<fieldsCount; i++)    
            {   
              //依次获取字段名称
              DBFField field = reader.getField(i); 
               fieldName = field.getName();
               //连接每个字段名称 
               params.append(fieldName+",");
               type = field.getDataType();
             // String typeStr = typeParser(type);
             // propertyMap.put(fieldName, Class.forName(typeStr));
         
            }
            params.deleteCharAt(params.length()-1);
            params.append(")");
         
           
            Object[] rowValues;
            for(int i=0;(rowValues = reader.nextRecord()) != null;i++) 
            {   
            	
              for( int j=0; j<rowValues.length; j++) 
              {   
            	  if(rowValues[j] == null)
            		  values.append("null"+",");
            	  else
            		  values.append("'"+rowValues[j].toString().trim()+"',");
              }   
              values.deleteCharAt(values.length()-1);
              values.append(")");

              //生成插入语句字符串		
              sql.add("insert into "+table+params.toString()+" values"+values.toString());
            	
              //重置每一行的数据
        	  values.delete(0, values.length());
        	  values.append("(");
        	 

            }
            //传递插入语句，导入数据
            dbf_Import(sql);
            
            //关闭输入流
            fis.close();
            //关闭数据库连接
            DBConnection.close(null, stm, con);
            
		
	}
	public void dbf_Import(List sql) throws SQLException{
		//打开数据库连接
		con = DBConnection.getConnection();
	
		stm = con.createStatement();
		int i=0;
		for(;i<sql.size();i++){
			//将sql语句加入批处理中
			stm.addBatch((String)sql.get(i));
		}
		//执行批处理
		stm.executeBatch();
		
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
		DbfImport dbf = new DbfImport();
		//DBF文件路径
		String path ="D:/易联众/数据库文件/score.DBF";
		
		dbf.impOracle(path);
		
	}
}
