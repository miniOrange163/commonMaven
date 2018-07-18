package priv.linjb.learn.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import priv.linjb.common.util.base64.ImageBase64;
import priv.linjb.common.util.base64.ImgBase64Util;
import priv.linjb.common.util.file.FileOperationUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/7/2
 *  
 * @name: 
 *
 * @Description: 
 */
public class FileTest {


    /**
     * 创建一个文件之前，如果文件所在目录不存在，先创建目录，再创建目标文件
     * @throws ParseException
     */
    @Test
    public void dirTest() throws ParseException {

        String path = "d:/time/su.txt";
        File file = new File(path);
        //getPath方法：文件的绝对路径
        System.out.println(file.getPath());
        //getParent方法：文件目录的绝对路径
        System.out.println(file.getParent());
        if (!file.exists()) {
            try {
                FileOperationUtil.createDir(file.getParent());
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void fileContentTest(){
        String path = "d:/a.txt";
        File file = new File(path);

        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            String t =  new String(filecontent, encoding);

            if (t != null) {
                System.out.println("判断不为空");
            }
            else{
                System.out.println("判断为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() throws IOException {
        /*String base64String = ImgBase64Util
                .imgToBase64String(
                        FileOperationUtil.downloadToByte("http://172.18.20.156:8880//group1/M00/07/11/rBIUnVsh2TCEMuefAAAAAMjddXg587.jpg"));
*/
        String base64String = ImgBase64Util
                .imgToBase64String(
                        FileOperationUtil.downloadToByte("http://172.18.20.156:8880/group1/M00/1D/A1/rBIUnVtO9TOEQqcfAAAAAMZELs0989.jpg"));

        System.out.println("base64String : " + base64String);
    }

}
