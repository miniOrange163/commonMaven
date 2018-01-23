package priv.linjb.common.util.ftp;
import java.io.File;  
import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;  
  
  
/** 
 * ftp上传工具包 
 * @author chuer 
 * @date 2015年1月7日 下午2:31:39 
 */  
public class FtpUtil {  
  
    /** 
     * 上传文件 
     * @param ftpPath 
     * @param listFiles 
     * @return 
     */  
    public static synchronized List<UploadResult> upload(String ftpPath,File [] listFiles) {  
    	 final int THREADS = 5;   
        ExecutorService newFixedThreadPool = new ThreadPoolExecutor(THREADS, THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        List<Future<UploadResult>> results = new ArrayList<>();  
          
        FtpFactory factory = new FtpFactory();  
        for (File file : listFiles) {  
            FtpConnection ftp = factory.getFtp();  
              
            UploadTask upload = new UploadTask(factory,ftp, file, ftpPath, file.getName());  
            Future<UploadResult> submit = newFixedThreadPool.submit(upload);  
            results.add(submit);  
        }  
  
          
        List<UploadResult> listResults = new ArrayList<>();  
        for (Future<UploadResult> result : results) {  
            try {  
                UploadResult uploadResult = result.get(30, TimeUnit.MINUTES);  
                listResults.add(uploadResult);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        factory.close();  
        newFixedThreadPool.shutdown();  
        return listResults;  
    }  
}  