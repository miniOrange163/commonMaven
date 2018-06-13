package priv.linjb.common.parse.word;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import priv.linjb.common.parse.word.policy.MyPictureRenderPolicy;
import priv.linjb.common.parse.word.policy.PageRenderPolicy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/6
 *  
 * @name: word模版文档中图片及表格的替换,pot-tl实现
 *
 * @Description:
 *
 * 例：
 *  //本地图片
    put("localPicture", new PictureRenderData(100, 120, "~/logo.png"));
    //本地图片byte数据
    put("localBytePicture", new PictureRenderData(100, 120, ".png", BytePictureUtils.getLocalByteArray(new File("~/logo.png"))));
    //网路图片
    put("urlPicture", new PictureRenderData(100, 100, ".png", BytePictureUtils.getUrlByteArray("https://avatars3.githubusercontent.com/u/1394854?v=3&s=40")));
    // java 图片
    put("bufferImagePicture", new PictureRenderData(100, 120, ".png", BytePictureUtils.getBufferByteArray(bufferImage)));
 */
public class PicTableTest {

    public static void main(String[] args) throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                //替换图片
                put("pic1", new PictureRenderData(565, 378, ".png", BytePictureUtils.getUrlByteArray("http://172.18.90.125:8880/group1/M00/0F/F6/fwAAAVsfk62ENFRPAAAAAJn2xOg409.jpg")));
                /*put("pic2", new PictureRenderData(565, 378, ".png", BytePictureUtils.getUrlByteArray("http://172.18.90.125:8880/group1/M00/03/D6/fwAAAVsTl9mEMVNHAAAAAAyED_0991.jpg")));
                put("pic3", new PictureRenderData(565, 378, ".png", BytePictureUtils.getUrlByteArray("http://172.18.90.125:8880/group1/M00/03/D6/fwAAAVsTl9mEMVNHAAAAAAyED_0991.jpg")));
               */

                // 有表格头 有数据
                put("table", new TableRenderData(new ArrayList<RenderData>() {
                    {
                        add(new TextRenderData("", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                    }
                }, new ArrayList<Object>() {
                    {
                        add("beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing");
                        add("zhejiang;hangzhou;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing");
                    }
                }, "no datas", 0));

                put("del1", new TableRenderData(new ArrayList<RenderData>() {
                    {
                        add(new TextRenderData("", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                        add(new TextRenderData("1E915D", "province"));
                        add(new TextRenderData("1E915D", "city"));
                    }
                }, new ArrayList<Object>() {
                    {
                        add("beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing");
                        add("zhejiang;hangzhou;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing;beijing");
                    }
                }, "no datas", 0));
            }
        };

        //新增语法插件
        Configure plugin = Configure.createDefault().plugin('%', new MyPictureRenderPolicy());
        /*plugin.getGramerChars().add(Character.valueOf('&'));
        plugin.getDefaultPolicys().put(Character.valueOf('&'), new PageRenderPolicy());*/
        XWPFTemplate template = XWPFTemplate.compile("D:\\信路威\\平台\\钦州打逃平台\\我的\\word\\练习\\测试模板.docx",plugin).render(datas);
        XWPFTemplate template2 = XWPFTemplate.compile("D:\\信路威\\平台\\钦州打逃平台\\我的\\word\\练习\\测试模板.docx",plugin).render(datas);

        FileOutputStream out = new FileOutputStream("D:\\信路威\\平台\\钦州打逃平台\\我的\\word\\out_table.docx");

        MergeDoc.appendBody(template.getXWPFDocument(),template2.getXWPFDocument());

        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
