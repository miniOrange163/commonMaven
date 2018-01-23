package priv.linjb.common.util.base64;

import java.io.*;

import org.apache.commons.codec.binary.Base64;



public class ImageBase64 {
	 public static void main(String[] args) {
         String imgFile = "d:\\dog.jpg";//待处理的图片
         String imgbese=imageToBase64Head(imgFile);
         System.out.println(imgbese.length());
         System.out.println(imgbese);
         String imgFilePath = "d:\\test.jpg";//新生成的图片
         base64ToImage(imgbese,imgFilePath);
     }
	 /**
      * 将图片转换成Base64编码 ,带头文件
      * @param imgFile 待处理图片
      * @return
      */
     public static String imageToBase64Head(String imgFile){
         //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
    	 String type = imgFile.substring(imgFile.length()-3,imgFile.length());
         String head = "data:image/"+type+";base64,";
        
         
         return head + imageToBase64(imgFile);
     }
     /**
      * 将图片转换成Base64编码
      * @param imgFile 待处理图片
      * @return
      */
     public static String imageToBase64(String imgFile){
         //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
   
         
         InputStream in = null;
         byte[] data = null;
         //读取图片字节数组
         try 
         {
             in = new FileInputStream(imgFile);        
             data = new byte[in.available()];
             in.read(data);
             in.close();
         } 
         catch (IOException e) 
         {
             e.printStackTrace();
         }
         
         return new String(Base64.encodeBase64(data));
     }
     
     /**
      * 对字节数组字符串进行Base64解码并生成图片
      * @param imgStr 图片数据
      * @param imgFilePath 保存图片全路径地址
      * @return
      */
     public static boolean base64ToImage(String imgStr,String imgFilePath){
         //
         if (imgStr == null) //图像数据为空
             return false;
         
         try 
         {
         
             //Base64解码
             byte[] b = Base64.decodeBase64(imgStr);
       
             for(int i=0;i<b.length;++i)
             {
                 if(b[i]<0)
                 {//调整异常数据
                     b[i]+=256;
                 }
             }
             //生成jpeg图片
 
             OutputStream out = new FileOutputStream(imgFilePath);    
             out.write(b);
             out.flush();
             out.close();
             return true;
         } 
         catch (Exception e) 
         {
             return false;
         }
   
     }
}
