package priv.linjb.common.parse.word;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/8
 *  
 * @name: 多个word文件合并，采用poi实现,兼容图片的迁移
 *
 * @Description: 
 */
public class MergeDoc {


    public static void main (String[] args) throws Exception {
        InputStream in1 = null;
        InputStream in2 = null;
        InputStream in3 = null;
        OPCPackage src1Package = null;
        OPCPackage src2Package = null;
        OPCPackage src3Package = null;
        OutputStream dest = new FileOutputStream("d:\\merge.docx");
        try {
            in1 = new FileInputStream("my/word/图片表格测试模板.docx");
            in2 = new FileInputStream("my/word/图片.docx");
            in3 = new FileInputStream("my/word/图片.docx");
            src1Package = OPCPackage.open(in1);
            src2Package = OPCPackage.open(in2);
            src3Package = OPCPackage.open(in3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        XWPFDocument src1Document = new XWPFDocument(src1Package);
        XWPFDocument src2Document = new XWPFDocument(src2Package);
        XWPFDocument src3Document = new XWPFDocument(src2Package);

        appendBody(src1Document, src2Document);
        appendBody(src1Document, src3Document);

        src1Document.write(dest);


    }

    public static void appendBody(XWPFDocument src, XWPFDocument append) throws Exception {
        CTBody src1Body = src.getDocument().getBody();
        CTBody src2Body = append.getDocument().getBody();

        List<XWPFPictureData> allPictures = append.getAllPictures();
        // 记录图片合并前及合并后的ID
        Map<String,String> map = new HashMap();
        for (XWPFPictureData picture : allPictures) {
            String before = append.getRelationId(picture);
            //将原文档中的图片加入到目标文档中
            String after = src.addPictureData(picture.getData(), Document.PICTURE_TYPE_PNG);
            map.put(before, after);
        }

        appendBody(src1Body, src2Body,map);

    }

    private static void appendBody(CTBody src, CTBody append,Map<String,String> map) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);

        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));

        if (map != null && !map.isEmpty()) {
            //对xml字符串中图片ID进行替换
            for (Map.Entry<String, String> set : map.entrySet()) {
                addPart = addPart.replace(set.getKey(), set.getValue());
            }
        }
        //将两个文档的xml内容进行拼接
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);

        src.set(makeBody);
    }
}
