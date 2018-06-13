package priv.linjb.common.parse.word.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.SimpleTableRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/6/6
 *  
 * @name: 自定义解析渲染策略
 *
 * @Description: 
 */
public class PageRenderPolicy implements RenderPolicy {

    private TextRenderPolicy textRenderPolicy;
    private SimpleTableRenderPolicy tableRenderPolicy;

    public PageRenderPolicy(){
        textRenderPolicy = new TextRenderPolicy();
        tableRenderPolicy = new SimpleTableRenderPolicy();
    }
    @Override
    public void render(ElementTemplate eleTemplate, Object renderData, XWPFTemplate doc) {
        //如果renderData为NULL，选择文本渲染
        if (renderData == null) {
            render2(eleTemplate,renderData,doc);

        }
        //如果renderData不为NULL，使用图形渲染
        else{
            renderTable(eleTemplate, renderData, doc);
//            textRenderPolicy.render(eleTemplate,renderData,doc);
        }
    }

    public void renderTable(ElementTemplate eleTemplate, Object renderData, XWPFTemplate template){
        RunTemplate runTemplate = (RunTemplate)eleTemplate;
        XWPFRun run = runTemplate.getRun();
        run.getParagraph().setPageBreak(true);
//        textRenderPolicy.render(eleTemplate,"行驶点",template);
        run.addBreak();
        run.addCarriageReturn();
        textRenderPolicy.render(eleTemplate,"行驶点",template);

          tableRenderPolicy.render(runTemplate,renderData,template);
    }
    public void render2(ElementTemplate eleTemplate, Object renderData, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate)eleTemplate;
        XWPFRun run = runTemplate.getRun();

        if(null == renderData) {
            //run.setText(null,0);
            run.getParagraph().setPageBreak(false);
            /*for (XWPFRun xwpfRun : run.getParagraph().getRuns()) {
                xwpfRun.setText(null,0);
            }*/
        } else {
            run.getParagraph().setPageBreak(true);
            textRenderPolicy.render(eleTemplate,"行驶点",template);
            run.addBreak();
            run.addCarriageReturn();
            //tableRenderPolicy.render(eleTemplate,renderData,template);
            /*TextRenderData textRenderData = null;
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
            }*/

        }
    }

}
