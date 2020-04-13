package priv.linjb.learn.http.https;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.security.SecureRandom;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/7/30
 *  
 * @name: 调用https请求
 *
 * @Description: 信任任意证书 (简单，但不建议用于生产代码),违背了使用 HTTPS 的初衷
 */
public class HttpsTest {

    private static CloseableHttpClient client;

    public static HttpClient getHttpsClient() throws Exception {

        if (client != null) {
            return client;
        }
        SSLContext sslcontext = SSLContexts.custom().useSSL().build();
        sslcontext.init(null, new X509TrustManager[]{new HttpsTrustManager()}, new SecureRandom());
        SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        client = HttpClients.custom().setSSLSocketFactory(factory).build();

        return client;
    }

    public static void releaseInstance() {
        client = null;
    }


    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        String encoding = "utf-8";
        String contentType = "application/json";
        String url = "https://deve.labscare.com/api/web/user/login/v1";
        String data = "{\"loginName\": \"15994365920\",\"password\": \"e10adc3949ba59abbe56e057f20f883e\"}";
        String body = null;

        final HttpClient httpsClient = getHttpsClient();

        HttpResponse response = null;
        HttpRequestBase http;
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        HttpEntity requestEntity = new StringEntity(data, ContentType.create(contentType, encoding));

        //设置参数到请求对象中
        httpPost.setEntity(requestEntity);
        response = httpsClient.execute(httpPost);

        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);

        System.out.println(body);


        long end = System.currentTimeMillis();
        double length = (end - start) / (double)1000;
        System.out.println("调用时长:" + length + "秒");
    }
}
