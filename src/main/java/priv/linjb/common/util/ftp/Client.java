package priv.linjb.common.util.ftp;

import java.io.File;  
import java.io.IOException;  
import java.util.List;  
  
/** 
 * 客户端 
 * @author chuer 
 * @date 2015年1月7日 下午2:32:41 
 */  
public class Client {  
  
    public static void main(String[] args) throws IOException {  
        String loalPath1 = "D:/police_data_copy_heyuan/cardata/cardata_send/txt";  
        String ftpPath1 = "/cardata/txt";
        String loalPath2 = "D:/police_data_copy_heyuan/cardata/cardata_send/img";  
        String ftpPath2 = "/cardata/data";  
        FtpConfig.load(null);
        File parentFile1 = new File(loalPath1);  
        List<UploadResult> resultLists = FtpUtil.upload(ftpPath1,parentFile1.listFiles());  
       /* File parentFile2 = new File(loalPath2); 
        List<UploadResult> resultLists2 = FtpUtil.upload(ftpPath2,parentFile2.listFiles());  */
          
        for(UploadResult result : resultLists){  
            System.out.println(result);  
        }  
        /*for(UploadResult result : resultLists2){  
            System.out.println(result);  
        }  */
    }  
  
}  