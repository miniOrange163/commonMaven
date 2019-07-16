package priv.linjb.common.parse.excel.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/5/27
 *  
 * @name: 
 *
 * @Description: 
 */
public class EasyExcelReadTest {

    public static void main(String[] args) throws IOException {

        FileInputStream inputStream = new FileInputStream(new File("D:\\公司资料\\模块\\检测项目v2\\2.xlsx"));


        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(0, 1,ShiDianTongModel.class));

        inputStream.close();
        for (Object obj : data) {
            System.out.println();
        }
    }




}
