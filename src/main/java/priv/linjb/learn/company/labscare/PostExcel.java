package priv.linjb.learn.company.labscare;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.linjb.common.util.http.SimpleHttpUtils;
import priv.linjb.common.util.json.GsonUtil;
import priv.linjb.common.util.json.JsonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/6/28
 *  
 * @name: 
 *
 * @Description: 
 */
public class PostExcel {

    private static Logger logger = LoggerFactory.getLogger(PostExcel.class);

    @Test
    public void query(){
        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("token", "cd71a9d79b9b4fa6b195614f30189a9b"));
        String statusUrl = "http://127.0.0.1:9999/api/public/excel/import-status/";

        String id = "";
    }
    public static void main(String[] args) {

        List<Header> headerList = new ArrayList<>();
        headerList.add(new BasicHeader("token", "cd71a9d79b9b4fa6b195614f30189a9b"));

        String url = "http://127.0.0.1:9999/api/admin/lab-gaugingv2/importExcel/v1";
        String statusUrl = "http://127.0.0.1:9999/api/public/excel/import-status/{id}/v1";

        String dir = "D:\\公司资料\\模块\\检测项目v2\\食典通检测项目数据\\res\\";

        int index = 1;
        int total = 202;

        for (int i = index; i <= total ; i++) {

            String name = "食典通_"+i+".xls";
            String fileName = dir + name;

            try {

                File file = new File(fileName);
                if (!file.exists()) {
                    logger.error("文件不存在,"+fileName);
                    continue;
                }
                Map<String, ContentBody> map = new HashMap<>();
                map.put("file", new FileBody(file));

                final String result = SimpleHttpUtils.postFormMultiPart(url, map,headerList,null);
                if (StringUtils.isBlank(result)) {
                    logger.error("上传失败，"+name+",result:"+result);
                    break;
                }
                LabsCareRsponse<String> rsp = JsonUtils.convertToObject(result, LabsCareRsponse.class);

                if (rsp.getCode() == 0) {
                    logger.error("上传失败，"+name+",result:"+result);
                    break;
                }
                else{
                    logger.info("上传成功，"+name);
                    final String id = rsp.getResult();

                    String excelStatusUrl = statusUrl.replace("{id}", id);
                    String httpRequest = SimpleHttpUtils.getHttpRequest(excelStatusUrl, null, headerList, null, "utf-8");
                    if (StringUtils.isBlank(httpRequest)) {
                        logger.error("状态获取失败，"+name+"rsp:"+httpRequest);
                        break;
                    }
                    LabsCareRsponse<ExcelStatus> statusRsp = GsonUtil.changeGsonToBean(httpRequest, LabsCareRsponse.class);
                    if (statusRsp == null) {
                        logger.error("状态信息解析失败，"+name+"rsp:"+httpRequest);
                        break;
                    }
                    if (statusRsp.getCode() == 0) {
                        logger.error("状态查询失败，"+name+"rsp:"+httpRequest);
                        break;
                    }
                    ExcelStatus status = GsonUtil.changeGsonToBean(GsonUtil.createGsonString(statusRsp.getResult()),ExcelStatus.class);
                    if (statusRsp == null) {
                        logger.error("状态信息为空，"+name+"rsp:"+httpRequest);
                        break;
                    }
                    if (2 == status.getStatus()) {
                        logger.info("文件处理完成,"+name);
                        Thread.sleep(2000);

                    }
                    if (3 == status.getStatus()) {
                        logger.info("文件处理失败,"+name+",失败原因："+status.getMessage());
                        return;
                    }
                    while (1==status.getStatus()){

                        Thread.sleep(5000);

                        httpRequest = SimpleHttpUtils.getHttpRequest(excelStatusUrl, null, headerList, null, "utf-8");
                        if (StringUtils.isBlank(httpRequest)) {
                            logger.error("状态获取失败，"+name);
                            return;
                        }
                        statusRsp = JsonUtils.convertToObject(httpRequest, LabsCareRsponse.class);
                        if (statusRsp == null) {
                            logger.error("状态信息解析失败，"+name);
                            return;
                        }
                        if (statusRsp.getCode() == 0) {
                            logger.error("状态查询失败，"+name+"rsp:"+httpRequest);
                            return;
                        }
                        status = GsonUtil.changeGsonToBean(GsonUtil.createGsonString(statusRsp.getResult()),ExcelStatus.class);
                        if (statusRsp == null) {
                            logger.error("状态信息为空，"+name);
                            return;
                        }

                        if (2 == status.getStatus()) {
                            logger.info("文件处理完成,"+name);
                            Thread.sleep(2000);
                            break;

                        }
                        if (3 == status.getStatus()) {
                            logger.info("文件处理失败,"+name+",失败原因："+status.getMessage());
                            return;
                        }
                        logger.info("导入中。。。");
                    }

                }

            } catch (Exception e) {
                logger.error(e.toString()+",name:"+name,e);
                break;
            }




        }

    }




    public class ExcelStatus{
        private String id;
        private String createTime;
        private String creator;
        private String isDel;
        private String message;
        private String reviseTime;
        private String reviser;
        private Integer status;//导入状态 1正在导入 2导入完成 3导入失败
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getIsDel() {
            return isDel;
        }

        public void setIsDel(String isDel) {
            this.isDel = isDel;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getReviseTime() {
            return reviseTime;
        }

        public void setReviseTime(String reviseTime) {
            this.reviseTime = reviseTime;
        }

        public String getReviser() {
            return reviser;
        }

        public void setReviser(String reviser) {
            this.reviser = reviser;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
