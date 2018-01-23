package priv.linjb.common.util.ftp;
import java.io.IOException;  
import java.util.concurrent.ArrayBlockingQueue;  
  

  
/** 
 * 连接工厂 
 * @author chuer 
 * @date 2015年1月7日 下午2:32:22 
 */  
public class FtpFactory {  
    private final ArrayBlockingQueue<FtpConnection> arrayBlockingQueue = new ArrayBlockingQueue<FtpConnection>(FtpConfig.ftpConnectionSize);  
      
    protected FtpFactory(){  
        System.out.println("init  FtpFactory");  
        for(int i=0;i< FtpConfig.ftpConnectionSize; i++){  
            arrayBlockingQueue.offer(new FtpConnection());  
        }  
    }  
      
    /** 
     * 获取连接 
     * @return 
     */  
    public  FtpConnection getFtp(){  
        FtpConnection poll = null;  
        try {  
            poll = arrayBlockingQueue.take();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        return poll;  
    }  
      
  
    /** 
     * 释放连接 
     * @param ftp 
     * @return 
     */  
    public boolean relase(FtpConnection ftp){  
        return arrayBlockingQueue.offer(ftp);  
    }  
      
      
    /** 
     * 删除连接 
     * @param ftp 
     */  
    public void remove(FtpConnection ftp){  
        arrayBlockingQueue.remove(ftp);  
          
    }  
      
    public void close(){  
        for(FtpConnection connection : arrayBlockingQueue){  
            try {  
                connection.disconnect();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
          
    }  
      
}