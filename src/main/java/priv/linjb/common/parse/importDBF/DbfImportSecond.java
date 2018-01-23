package priv.linjb.common.parse.importDBF;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;



import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
public class DbfImportSecond {

	
	private  String table;			//表格名称;
	private StringBuffer params;		//记录表格各字段
	private StringBuffer values;	//记录每一行数据
	private List sql;				//记录所有行的数据
	
	private Map<String,String> paramsMap;
	private static Connection con = null;
	private static Statement stm = null;
	private static ResultSet res = null; 
	 
	 
	public DbfImportSecond() {
		// TODO Auto-generated constructor stub
		table ="AH02";
		params  = new StringBuffer("(");
		values = new StringBuffer("(");
		sql = new ArrayList<String>();
		
		paramsMap = new HashMap();
		init();
	}
	private void init(){
		
		paramsMap.put("BMD", "BMD001");
		paramsMap.put("BMDMC", "BMD002");
		paramsMap.put("ZJLB", "AAD001");
		paramsMap.put("ZJHM", "AAC002");
		paramsMap.put("KSXM", "AAC003");
		paramsMap.put("BKJB", "BMD003");
		paramsMap.put("BKJBMC", "BMD004");
		paramsMap.put("BKZY", "BMD005");
		paramsMap.put("BKZYMC", "BMD006");
		paramsMap.put("SS", "BMD007");
		
		paramsMap.put("SSMC", "BMD008");
		paramsMap.put("KQ", "BMD009");
		paramsMap.put("KQMC", "BMD010");
		paramsMap.put("XB", "AAD002");
		paramsMap.put("GZDW", "AAD003");
		paramsMap.put("DWXZ", "AAD004");
		paramsMap.put("ZYJSZW", "AAD005");
		paramsMap.put("ZYNX", "AAD006");
		paramsMap.put("GZNX", "AAD007");
		paramsMap.put("XL", "AAD008");
		
		paramsMap.put("SXZY", "AAD009");
		paramsMap.put("BYBY", "AAD010");
		paramsMap.put("BYXX", "AAD011");
		paramsMap.put("DZYX", "AAD012");
		paramsMap.put("GJDQ", "AAD013");
		paramsMap.put("MZ", "AAD014");
		paramsMap.put("ZZMM", "AAD015");
		paramsMap.put("ZYZC", "AAD016");
		paramsMap.put("ZYJSZWPRRQ", "AAD017");
		paramsMap.put("BMXH", "BMD011");
		
		paramsMap.put("DAH", "BMD012");
		paramsMap.put("ZYTG", "BMD013");
		paramsMap.put("ZSGLH", "BMD014");
		paramsMap.put("ZSYZBH", "BMD015");
		paramsMap.put("ZKZH_1", "KMA001");
		paramsMap.put("ZKZH_2", "KMA002");
		paramsMap.put("CJ_1", "KMA003");
		paramsMap.put("CJ_2", "KMA004");
		paramsMap.put("CJBZ_1", "KMA005");
		paramsMap.put("CJBZ_2", "KMA006");
		
		paramsMap.put("KMTG_1", "KMA007");
		paramsMap.put("KMTG_2", "KMA008");
		paramsMap.put("YCL_1", "KMB001");
		paramsMap.put("YCL_2", "KMB002");
		paramsMap.put("WJLBM_1", "KMB003");
		paramsMap.put("WJLBMMC_1", "KMB004");
		paramsMap.put("WJLBM_2", "KMB005");
		paramsMap.put("WJLBMMC_2", "KMB006");
		paramsMap.put("CLLBM_1", "KMB007");
		paramsMap.put("CLLBMMC_1", "KMB008");
		
		paramsMap.put("CLLBM_2", "KMB009");
		paramsMap.put("CLLBMMC_2", "KMB010");
		paramsMap.put("CLQSRQ_1", "KMB011");
		paramsMap.put("CLQSRQ_2", "KMB012");
		paramsMap.put("CLJSRQ_1", "KMB013");
		paramsMap.put("CLJSRQ_2", "KMB014");
		paramsMap.put("CLWH_1", "KMB015");
		paramsMap.put("CLWH_2", "KMB016");
		paramsMap.put("KM_1", "KMA009");
		paramsMap.put("KM_2", "KMA010");
		
	}
	public String typeParser(byte type){

		switch(type){
			case 67:return String.class.getName();
			case 78:return Float.class.getName();
			default: return "";
		}
	}
	public void impOracle(String path) throws ClassNotFoundException, IOException, SQLException{
		
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
               fieldName = paramsMap.get(fieldName);
               if(fieldName == null) return;
               params.append(fieldName+",");
               type = field.getDataType();
             // String typeStr = typeParser(type);
             // propertyMap.put(fieldName, Class.forName(typeStr));
         
            }
            params.deleteCharAt(params.length()-1);
            params.append(",KMA011)");
         
           
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
              values.append(",'0')");

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
		DbfImportSecond dbf = new DbfImportSecond();
		//DBF文件路径
		String path ="D:/易联众/数据库文件/score.DBF";
		
		dbf.impOracle(path);
		
	}
}
