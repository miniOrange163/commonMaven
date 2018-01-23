package priv.linjb.common.util.ftp;


import java.io.File;  
import java.io.IOException;  
import java.util.concurrent.Callable;  
  
 
/** 
* 上传任务 
* @author chuer 
* @date 2015年1月7日 下午2:30:46 
*/  
public class UploadTask implements Callable<UploadResult>{  
   private File file;  
   private FtpConnection ftp;  
   private String path;  
   private String fileName;  
   private FtpFactory factory;  
     
   public UploadTask(FtpFactory factory,FtpConnection ftp,File file,String path,String fileName){  
       this.factory = factory;  
       this.ftp = ftp;  
       this.file = file;  
       this.path = path;  
       this.fileName = fileName;  
   }  
     
     
   @Override  
   public UploadResult call() throws Exception {  
       UploadResult result = null;  
       try{  
           if(ftp == null){  
               result = new UploadResult(file.getAbsolutePath(),false);  
               return result;  
           }  
             
           //如果连接未开启 重新获取连接  
           if(!ftp.isConnected()){  
               factory.remove(ftp);  
               ftp = new FtpConnection();  
//             factory.relase(ftp);  
           }  
             
           //开始上传  
           //LoggerUtils.upload.info(file.getName()+" is uploading ...");  
             
           FtpResult.resultList.add(file.getName()+" is uploading ...");  
           ftp.upload(path, fileName, file);  
           result = new UploadResult(file.getName(),true);  
       }catch(IOException ex){  
           result = new UploadResult(file.getName(),false);  
           ex.printStackTrace();  
       }finally{  
           factory.relase(ftp);//释放连接  
       }  
         
       FtpResult.resultList.add(result.toString());  
       //LoggerUtils.upload.info(result.toString());  
       return result;  
   }  
 
     
}  