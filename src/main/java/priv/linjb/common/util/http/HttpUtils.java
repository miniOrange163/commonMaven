package priv.linjb.common.util.http;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.linjb.common.util.json.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * http请求工具
 */
public final class HttpUtils {


    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils(){

    }
    /**
     *
     * @param url 服务器地址
     * @param header 头属性实体
     * @return 返回结果
     */
    public static String post(String url, Header header){
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置超时时间
        /*setConnectTimeout：设置连接超时时间，单位毫秒。
        setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
        setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。*/
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(7000)
                .build();

        HttpPost postMethod = new HttpPost(url);
        postMethod.setConfig(requestConfig);
        if(header != null){
            postMethod.setHeader(header);
        }

        CloseableHttpResponse response = null ;
        try {
            response = httpClient.execute(postMethod);
            HttpEntity entity = response.getEntity();
            Charset charset = ContentType.getOrDefault(entity).getCharset();
            result = EntityUtils.toString(entity,charset);

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
        } finally {
            try {
                if(response!=null) {response.close();}
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

            try {
                if(httpClient!=null) {httpClient.close();}
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

        }
        return result;
    }


    /**
     * 将对象转换成JSON，并发送JSON到服务器
     * @param url 服务器地址
     * @param data 对象数据
     * @param header 头属性实体
     * @return 返回结果
     */
    public static String postJsonObject(String url, Object data,Header header){
        String json = JsonUtils.convertToString(data);
        return  postJsonString(url,json,header);
    }

    /**
     * 发送JSON到服务器
     * @param url 服务器地址
     * @param data JSON数据
     * @param header 头属性实体
     * @return 返回结果
     */
    public static String postJsonString(String url, String data,Header header,RequestConfig requestConfig){
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();

        StringEntity requestEntity = new StringEntity(data, ContentType.create("application/json", "utf-8"));
        HttpPost postMethod = new HttpPost(url);
        postMethod.setConfig(requestConfig);
        postMethod.setEntity(requestEntity);
        if(header != null){
            postMethod.setHeader(header);
        }

        CloseableHttpResponse response = null ;
        try {
            response = httpClient.execute(postMethod);
            HttpEntity entity = response.getEntity();
            Charset charset = ContentType.getOrDefault(entity).getCharset();
            result = EntityUtils.toString(entity,charset);

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
        } finally {
            try {
                if(response!=null) {response.close();}
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

            try {
                if(httpClient!=null) {httpClient.close();}
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

        }
        return result;
    }
    
    /**
     * 发送JSON到服务器
     * @param url 服务器地址
     * @param data JSON数据
     * @param header 头属性实体
     * @return 返回结果
     */
    public static String postJsonString(String url, String data,Header header){
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置超时时间
        /*setConnectTimeout：设置连接超时时间，单位毫秒。
        setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
        setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。*/
        RequestConfig requestConfig = RequestConfig.custom()    
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(10000)
                .build();

        StringEntity requestEntity = new StringEntity(data, ContentType.create("application/json", "utf-8"));
        HttpPost postMethod = new HttpPost(url);
        postMethod.setConfig(requestConfig);
        postMethod.setEntity(requestEntity);
        if(header != null){
            postMethod.setHeader(header);
        }

        CloseableHttpResponse response = null ;
        try {
            response = httpClient.execute(postMethod);
            HttpEntity entity = response.getEntity();
            Charset charset = ContentType.getOrDefault(entity).getCharset();
            result = EntityUtils.toString(entity,charset);

        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.toString(),e);
        } finally {
            try {
                if(response!=null) {response.close();}
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

            try {
                if(httpClient!=null) {httpClient.close();}
            }catch (Exception e){
                e.printStackTrace();
                logger.error(e.toString(),e);
            }

        }
        return result;
    }


    public static String postFileMultiPart(String url, Map<String, ContentBody> reqParam) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            // 创建httpget.
            HttpPost httppost = new HttpPost(url);

            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, ContentBody> param : reqParam.entrySet()) {
                multipartEntityBuilder.addPart(param.getKey(), param.getValue());
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);

            // 执行post请求.
            CloseableHttpResponse response = httpclient.execute(httppost);


            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, Charset.forName("UTF-8"));
                }
                //System.out.println("------------------------------------");
            } finally {
                response.close();

            }
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.toString(),e);
            }
        }
        return null;


    }
}
