package priv.linjb.learn.test;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import priv.linjb.common.util.base64.ImageBase64;
import priv.linjb.common.util.base64.ImgBase64Util;
import priv.linjb.common.util.file.FileOperationUtil;

import java.io.*;
import java.text.ParseException;
import java.util.Properties;

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
        Properties properties = new Properties();
        try(InputStreamReader isr=new InputStreamReader(new FileInputStream(new File("my/file/hikvision.properties")),"utf-8")){
            properties.load(isr);
            isr.close();
        }

        String property = properties.getProperty("hikvision.kkmy");

        System.out.println(property);

        properties.setProperty("hikvision.kkmy","123");

        property = properties.getProperty("hikvision.kkmy");
        System.out.println(property);


        try(OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File("my/file/hikvision.properties")),"utf-8")){

            properties.store(out,"");
        }

    }

}
