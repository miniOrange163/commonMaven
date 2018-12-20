package priv.linjb.common.util.http;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/12/10
 *  
 * @name: 
 *
 * @Description: 
 */
public class SimpleHttpUtils {

    private static Logger logger = LoggerFactory.getLogger(SimpleHttpUtils.class);

    /**
     * 向指定URL发送post请求
     * @param url 请求地址可带参数
     * @return 结果
     */
    public static String post(String url){
        return postHttpRequest(url,null,null,null,"utf-8");
    }

    /**
     * 向指定URL发送post请求，请求体是json格式的字符串
     * @param url 请求地址
     * @param content 内容
     * @return 结果
     */
    public static String postJson(String url,String content){
        return postJson(url,content,null,null);
    }

    /**
     * 向指定URL发送post请求，请求体是表单形式的字符串，如aaa=111&bbb=222&ccc=333
     * @param url 请求地址
     * @param content 内容
     * @return 结果
     */
    public static String postInputStream(String url,String content){
        return postInputStream(url, content, null, null);
    }

    /**
     * 向指定URL发送post请求，请求体是表单形式的字符串，内容使用Urlencoded编码，默认UTF-8
     * @param url 请求地址
     * @param map 键值对
     * @return 结果
     */
    public static String postFormUrlencoded(String url, Map<String,String> map)  {
        return postFormUrlencoded(url,map,null,null);
    }

    /**
     * 向指定URL发送post请求，请求体是多类型内容，内容使用Urlencoded编码，默认UTF-8
     * 例：
     *     Map<String, ContentBody> map = new HashedMap();
     *     map.put("text",new StringBody(json, ContentType.APPLICATION_JSON));
     *     map.put("fileImg", new FileBody(file));
     *     String post = postFormMultiPart(url,map);
     * @param url 请求地址
     * @param map 键值对
     * @return 结果
     */
    public static String postFormMultiPart(String url, Map<String, ContentBody> map) {
        return postFormMultiPart(url,map,null,null);
    }



    public static String postInputStream(String url,String content,List<Header> headers,RequestConfig requestConfig){

        String body = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String encoding = "utf-8";
        try(InputStream inputStream = new ByteArrayInputStream(content.getBytes(encoding))) {

            if (headers == null) {
                headers = new ArrayList<>();
            }
            headers.add(defaultHeader("application/x-www-form-urlencoded"));

            body = postHttpRequest(url,new InputStreamEntity(inputStream),headers,requestConfig,encoding);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
        }


        return body;

    }

    public static String postFormUrlencoded(String url, Map<String,String> map,
                                            List<Header> headers,RequestConfig requestConfig){

        String body = null;

        if (headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(defaultHeader("application/x-www-form-urlencoded"));

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        String encoding = "utf-8";
        try {

            UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(nvps, encoding);
            body = postHttpRequest(url,httpEntity,headers,requestConfig,encoding);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);

        }
        return body;

    }




    public static String postFormMultiPart(String url, Map<String, ContentBody> map,List<Header> headers,RequestConfig requestConfig) {

        String body = null;

        if (headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(defaultHeader("multipart/form-data"));

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        for (Map.Entry<String, ContentBody> param : map.entrySet()) {
            multipartEntityBuilder.addPart(param.getKey(), param.getValue());
        }
        HttpEntity httpEntity = multipartEntityBuilder.build();
        String encoding = "utf-8";

        body = postHttpRequest(url,httpEntity,headers,requestConfig,encoding);

        return body;

    }



    public static String postJson(String url,String data,List<Header> headers,RequestConfig requestConfig){

        String body = null;
        String encoding = "utf-8";
        String contentType = "application/json";

        if (headers == null) {
            headers = new ArrayList<>();
        }
        headers.add(defaultHeader(contentType));

        HttpEntity requestEntity = new StringEntity(data, ContentType.create(contentType, encoding));

        body = postHttpRequest(url,requestEntity,headers,requestConfig,encoding);

        return body;

    }

    /**
     * post形式发送http请求
     * @param url   请求地址
     * @param httpEntity    请求实体
     * @param headers       请求头
     * @param requestConfig 请求配置
     * @param encoding      编码
     * @return  响应字符串
     */
    public static String postHttpRequest(String url, HttpEntity httpEntity,
                                         List<Header> headers, RequestConfig requestConfig, String encoding) {

        String body = null;
        try {

            //创建post方式请求对象
            HttpPost http = new HttpPost(url);
            //设置参数到请求对象中
            http.setEntity(httpEntity);

            body = baseHttpRequest(http,url,headers,requestConfig,encoding);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
        }

        return body;

    }
    /**
     * delete形式发送http请求
     * @param url   请求地址
     * @param headers       请求头
     * @param requestConfig 请求配置
     * @param encoding      编码
     * @return  响应字符串
     */
    public static String deleteHttpRequest(String url, List<Header> headers, RequestConfig requestConfig, String encoding) {

        String body = null;
        try {

            //创建post方式请求对象
            HttpDelete http = new HttpDelete(url);

            body = baseHttpRequest(http,url,headers,requestConfig,encoding);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
        }

        return body;

    }
    /**
     * put形式发送http请求
     * @param url   请求地址
     * @param httpEntity    请求实体
     * @param headers       请求头
     * @param requestConfig 请求配置
     * @param encoding      编码
     * @return  响应字符串
     */
    public static String putHttpRequest(String url, HttpEntity httpEntity,List<Header> headers,
                                        RequestConfig requestConfig, String encoding) {

        String body = null;
        try {

            //创建post方式请求对象
            HttpPut http = new HttpPut(url);
            //设置参数到请求对象中
            http.setEntity(httpEntity);

            body = baseHttpRequest(http,url,headers,requestConfig,encoding);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
        }

        return body;

    }
    /**
     * get形式发送http请求
     * @param url   请求地址
     * @param httpEntity    请求实体
     * @param headers       请求头
     * @param requestConfig 请求配置
     * @param encoding      编码
     * @return  响应字符串
     */
    public static String getHttpRequest(String url, HttpEntity httpEntity,List<Header> headers,
                                        RequestConfig requestConfig, String encoding) {

        String body = null;
        try {

            //创建post方式请求对象
            HttpGet http = new HttpGet(url);

            body = baseHttpRequest(http,url,headers,requestConfig,encoding);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
        }

        return body;

    }

    private static String baseHttpRequest(HttpRequestBase http , String url, List<Header> headers,
                                         RequestConfig requestConfig, String encoding) {

        String body = null;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {

            //创建httpclient对象
            httpClient = HttpClients.createDefault();

            //设置header信息
            //指定报文头【Content-type】
            if (headers != null) {
                for (Header header : headers) {
                    http.setHeader(header);
                }
            }
            if (requestConfig == null) {
                requestConfig = defaultRequestConfig();
            }
            http.setConfig(requestConfig);
            //执行请求操作，并拿到结果（同步阻塞）
            response = httpClient.execute(http);
            //获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(),e);
        } finally {

            try {
                if (response != null) {
                    response.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString(),e);
            }
            try {

                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

        }


        return body;

    }


    public static RequestConfig defaultRequestConfig(){

        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(10000)
                .build();

        return config;
    }

    public static Header defaultHeader(String contentType){

        return new BasicHeader("Content-type",contentType);
    }
}
