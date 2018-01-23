package priv.linjb.common.util.file;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream; 
 /**
  * 文件压缩工具类 
  * 
  * 压缩多个文件或目录的方法 		compress(String zipPath,String[] pathName)	
  * 压缩单个文件或目录的方法		compress(File zipFile,String srcPathName)
  */
public class ZipCompressorUtil {     
	private static String ZIP_ENCODEING_GBK = "GBK";
    private static final int BUFFER = 8192;     
    
    /**
     * 压缩多个文件或目录的方法
     * @param zipPath	目标zip文件路径
     * @param pathName	需要压缩的文件或目录路径数组
     */
    public static void compress(String zipPath,String[] pathName) {   
        ZipOutputStream out = null;     
        try {    
        	File zipFile = new File(zipPath);
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);     
            out  = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath)));  
            out.setEncoding(ZIP_ENCODEING_GBK);
           
            String basedir = "";   
            for (int i=0;i<pathName.length;i++){  
            	System.out.println(pathName);
                compress(new File(pathName[i]), out, basedir);     
            }  
            out.close();    
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }   
    }     
    /**
     * 压缩单个文件或目录的方法
     * @param zipFile
     * @param srcPathName
     */
    public static  void compress(File zipFile,String srcPathName) {     
        File file = new File(srcPathName);     
        if (!file.exists())     
            throw new RuntimeException(srcPathName + "不存在！");     
        try {     
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);     
      
            ZipOutputStream out  = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));    
            
            String basedir = "";     
            compress(file, out, basedir);     
            out.close();     
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }     
    }     
    
    private static void compress(File file, ZipOutputStream out, String basedir) {     
        /* 判断是目录还是文件 */    
        if (file.isDirectory()) {     
            System.out.println("压缩：" + basedir + file.getName());     
            compressDirectory(file, out, basedir);     
        } else {     
            System.out.println("压缩：" + basedir + file.getName());     
            compressFile(file, out, basedir);     
        }     
    }     
    
    /** 压缩一个目录 */    
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) {     
        if (!dir.exists())     
            return;     
    
        File[] files = dir.listFiles();     
        for (int i = 0; i < files.length; i++) {     
            /* 递归 */    
            compress(files[i], out, basedir + dir.getName() + "/");     
        }     
    }     
    
    /** 压缩一个文件 */    
    private static void compressFile(File file, ZipOutputStream out, String basedir) {     
        if (!file.exists()) {     
            return;     
        }     
        try {     
            BufferedInputStream bis = new BufferedInputStream(     
                    new FileInputStream(file));     
            ZipEntry entry = new ZipEntry(basedir + file.getName());     
            out.putNextEntry(entry);     
            int count;     
            byte data[] = new byte[BUFFER];     
            while ((count = bis.read(data, 0, BUFFER)) != -1) {     
                out.write(data, 0, count);     
            }     
            bis.close();     
        } catch (Exception e) {     
            throw new RuntimeException(e);     
        }     
    }     
}   