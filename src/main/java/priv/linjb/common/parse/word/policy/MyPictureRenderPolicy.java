package priv.linjb.common.parse.word.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/6
 *  
 * @name: 自定义解析渲染策略
 *
 * @Description: 
 */
public class MyPictureRenderPolicy implements RenderPolicy {

    private TextRenderPolicy textRenderPolicy;
    private PictureRenderPolicy pictureRenderPolicy;

    public MyPictureRenderPolicy(){
        textRenderPolicy = new TextRenderPolicy();
        pictureRenderPolicy = new PictureRenderPolicy();
    }
    @Override
    public void render(ElementTemplate eleTemplate, Object renderData, XWPFTemplate doc) {
        //如果renderData为NULL，选择文本渲染
        if (renderData == null) {
            //textRenderPolicy.render(eleTemplate,renderData,doc);
            render2(eleTemplate,renderData,doc);
        }
        //如果renderData不为NULL，使用图形渲染
        else{
            pictureRenderPolicy.render(eleTemplate,renderData,doc);

        }
    }


    public void render2(ElementTemplate eleTemplate, Object renderData, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate)eleTemplate;
        XWPFRun run = runTemplate.getRun();
        if(null == renderData) {
            run.setText("", 0);
        } else {
            TextRenderData textRenderData = null;
            if(renderData instanceof TextRenderData) {
                textRenderData = (TextRenderData)renderData;
            } else {
                textRenderData = new TextRenderData(renderData.toString());
            }

            String data = textRenderData.getText();
            StyleUtils.styleRun(run, textRenderData.getStyle());
            if(null == data) {
                data = "";

            }

            String[] split = data.split("\\n");
            if(null != split) {
                run.setText(split[0], 0);

                for(int i = 1; i < split.length; ++i) {
                    run.addBreak();
                    run.setText(split[i]);
                }
            }

        }
    }

}
