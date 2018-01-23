package priv.linjb.common.util.ftp;

import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
  
/** 
 * ftp 配置类 
 * @author chuer 
 * @date 2015年1月7日 下午4:36:50 
 */  
public class FtpConfig {  
      
    public static  int defaultTimeoutSecond;  
    public static  int connectTimeoutSecond;  
    public static  int dataTimeoutSecond;  
    public static  String host;  
    public static  int port;  
    public static  String user;  
    public static  String password;  
      
      
    public static int threadPoolSize;  
    public static int ftpConnectionSize;  
      
    public static  String rootPath;  
      
    /** 
     * @param path 
     */  
    public static void load(String path) {  
        path += "ftpConfig.xml";  
        try{ 
        	host = "172.18.10.245";
        	port = 21;
        	user = "admin";
        	password = "admin";
        	defaultTimeoutSecond = 10;
        	connectTimeoutSecond = 5;
        	threadPoolSize = 10;
        	ftpConnectionSize = 10;

        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
      
      
}