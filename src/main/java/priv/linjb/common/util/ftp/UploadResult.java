package priv.linjb.common.util.ftp;

/** 
 * 上传结果 
 * @author chuer 
 * @date 2015年1月7日 下午2:31:14 
 */  
public class UploadResult {  
  
    private String fileName; //文件名称  
    private boolean result;  //是否上传成功  
      
    public UploadResult(String fileName,boolean result){  
        this.fileName = fileName;  
        this.result = result;  
    }  
      
    public String getFileName() {  
        return fileName;  
    }  
    public void setFileName(String fileName) {  
        this.fileName = fileName;  
    }  
    public boolean isResult() {  
        return result;  
    }  
    public void setResult(boolean result) {  
        this.result = result;  
    }  
      
    public String toString(){  
        return "[fileName="+fileName+" , result="+result+"]";  
    }  
      
      
}  