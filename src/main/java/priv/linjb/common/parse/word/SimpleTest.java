package priv.linjb.common.parse.word;

import com.deepoove.poi.XWPFTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/6
 *  
 * @name: word模版文档中文本的替换,pot-tl实现
 *
 * @Description: 
 */
public class SimpleTest {


    public static void main(String[] args) throws IOException {
        Map<String, Object> datas = new HashMap<String, Object>(){{
            put("indexPlateNumber", "桂N68826");
            put("indexAddress", "上思收费站");
            put("indexPayTime", "2018-05-28");
            put("indexMoney", "壹仟元整");

            put("tellName", "垃圾运输公司");
            put("tellPlate", "桂N68826");
            put("tellN", "4");
            put("tellA", "2");
            put("tellC", "客");
            put("tellY1", "2018");
            put("tellM1", "4");
            put("tellD1", "12");
            put("tellY2", "2018");
            put("tellM2", "5");
            put("tellD2", "25");
            put("tellNum", "3");
            put("tellTollType", "换牌换卡");
            put("tellMoney", "1000");
            put("tellDay", "五");
            put("tellStation", "中马园区站");
            put("tellTop", "广西交通投资集团钦州高速公路运营有限公司");
            put("tellDoName", "六钦收费稽查科");
            put("tellY3", "2018");
            put("tellM3", "6");
            put("tellD3", "6");
            put("tellPhone", "15912345555");

        }};
        //render
        XWPFTemplate template = XWPFTemplate.compile("my/word/文本模板.docx").render(datas);
        //out document
        FileOutputStream out = new FileOutputStream("D:\\out.docx");
        template.write(out);
        out.close();
        template.close();
    }
}
