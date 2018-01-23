package priv.linjb.common.util.ftp;

import java.io.BufferedInputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.net.UnknownHostException;  
  
import org.apache.commons.net.ftp.FTPClient;  
import org.apache.commons.net.ftp.FTPReply;  
  
  
/** 
 * FTP 连接 
 * @author chuer 
 * @date 2015年1月7日 下午2:19:48 
 */  
public class FtpConnection {  
    public static final String ANONYMOUS_LOGIN = "anonymous";  
    private FTPClient ftp = new FTPClient();  
    private boolean is_connected = false;  
      
    /** 
     * 构造函数 
     */  
    public FtpConnection(){  
        is_connected = false;  
        ftp.setDefaultTimeout(FtpConfig.defaultTimeoutSecond * 1000);  
        ftp.setConnectTimeout(FtpConfig.connectTimeoutSecond * 1000);  
        ftp.setDataTimeout(FtpConfig.dataTimeoutSecond * 1000);  
          
        try {  
            initConnect(FtpConfig.host,FtpConfig.port,FtpConfig.user,FtpConfig.password);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
      
    /** 
     * 初始化连接 
     * @param host 
     * @param port 
     * @param user 
     * @param password 
     * @throws IOException 
     */  
    private void initConnect(String host, int port, String user, String password) throws IOException {  
        try {  
            ftp.connect(host, port);  
        } catch (UnknownHostException ex) {  
            throw new IOException("Can't find FTP server '" + host + "'");  
        }  
  
        int reply = ftp.getReplyCode();  
        if (!FTPReply.isPositiveCompletion(reply)) {  
            disconnect();  
            throw new IOException("Can't connect to server '" + host + "'");  
        }  
  
        if (user == "") {  
            user = ANONYMOUS_LOGIN;  
        }  
  
        if (!ftp.login(user, password)) {  
            is_connected = false;  
            disconnect();  
            throw new IOException("Can't login to server '" + host + "'");  
        } else {  
            is_connected = true;  
        }  
    }  
  
    /** 
     * 上传文件 
     * @param path 
     * @param ftpFileName 
     * @param localFile 
     * @throws IOException 
     */  
    public void upload(String path,String ftpFileName, File localFile) throws IOException {  
        //检查本地文件是否存在  
        if (!localFile.exists()) {  
            throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");  
        }  
        //设置工作路径  
        setWorkingDirectory(path);  
        //上传  
        InputStream in = null;  
        try {  
            //被动模式  
            ftp.enterLocalPassiveMode();  
  
            in = new BufferedInputStream(new FileInputStream(localFile));  
            //保存文件  
            if (ftp.storeFile(ftpFileName, in)) {  
                localFile.delete();
            }
            else{
            	throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path."); 
            }
        } finally {  
            try {  
                in.close();  
            } catch (IOException ex) {  
            }  
        }  
    }  
  
    /** 
     * 关闭连接 
     * @throws IOException 
     */  
    public void disconnect() throws IOException {  
        if (ftp.isConnected()) {  
            try {  
                ftp.logout();  
                ftp.disconnect();  
                is_connected = false;  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
        }  
    }  
  
  
    /** 
     * 设置工作路径 
     * @param dir 
     * @return 
     */  
    private boolean setWorkingDirectory(String dir) {  
        if (!is_connected) {  
            return false;  
        }  
        //如果目录不存在创建目录  
        try {  
            if(createDirecroty(dir)){  
                return ftp.changeWorkingDirectory(dir);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        return false;  
    }  
      
    /** 
     * 是否连接 
     * @return 
     */  
    public boolean isConnected(){  
        return is_connected;  
    }  
      
    /** 
     * 创建目录 
     * @param remote 
     * @return 
     * @throws IOException 
     */  
    private boolean createDirecroty(String remote) throws IOException {  
        boolean success = true;  
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);  
        // 如果远程目录不存在，则递归创建远程服务器目录  
        if (!directory.equalsIgnoreCase("/") && !ftp.changeWorkingDirectory(new String(directory))) {  
            int start = 0;  
            int end = 0;  
            if (directory.startsWith("/")) {  
                start = 1;  
            } else {  
                start = 0;  
            }  
            end = directory.indexOf("/", start);  
            while (true) {  
                String subDirectory = new String(remote.substring(start, end));  
                if (!ftp.changeWorkingDirectory(subDirectory)) {  
                    if (ftp.makeDirectory(subDirectory)) {  
                        ftp.changeWorkingDirectory(subDirectory);  
                    } else {  
                        System.out.println("mack directory error :/"+subDirectory);  
                        return false;  
                    }  
                }  
                start = end + 1;  
                end = directory.indexOf("/", start);  
                // 检查所有目录是否创建完毕  
                if (end <= start) {  
                    break;  
                }  
            }  
        }  
        return success;  
    }  
      
  
}