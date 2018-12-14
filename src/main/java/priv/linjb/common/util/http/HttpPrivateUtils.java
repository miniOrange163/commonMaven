package priv.linjb.common.util.http;


import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
import priv.linjb.common.util.json.JsonUtils;



public class HttpPrivateUtils {

    /**
     * 将对象转换成JSON，并发送JSON到服务器
     * @param url 服务器地址
     * @param data 对象数据
     * @return
     */
    public static String postJsonObject(String url, Object data){
        String json = JsonUtils.convertToString(data);
        return  postJsonString(url,json);
    }

    /**
     * 发送JSON到服务器
     * @param url 服务器地址
     * @param data JSON数据
     * @return
     */
    public static String postJsonString(String url, String data){
   
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置超时时间
        /*
        setConnectTimeout：设置连接超时时间，单位毫秒。
        setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
        setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
        */
        RequestConfig requestConfig = RequestConfig.custom()    
                .setConnectTimeout(20000).setConnectionRequestTimeout(1000)    
                .setSocketTimeout(30000).build(); 
        StringEntity requestEntity = new StringEntity(data, ContentType.create("application/json", "utf-8"));
        HttpPost postMethod = new HttpPost(url);
        postMethod.setConfig(requestConfig);
        postMethod.setEntity(requestEntity);
        
        CloseableHttpResponse response = null ;
        try {
            response = httpClient.execute(postMethod);
            HttpEntity entity = response.getEntity();
            Charset charset = ContentType.getOrDefault(entity).getCharset();
            return EntityUtils.toString(entity,charset);
        }catch (SocketTimeoutException e) {
        	//e.printStackTrace();
        	JSONObject json = new JSONObject();
        	json.put("errorcode", "3");
        	json.put("data", null);
        	try {
				json.put("message", new String("[timeout]连接超时".getBytes(), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	json.put("data", null);
        	
        	return json.toString();
			// TODO: handle exception
		}catch (HttpHostConnectException e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
        	json.put("errorcode", "3");
        	json.put("data", null);
        	try {
				json.put("message", new String("[refused]拒绝连接".getBytes(), "ISO-8859-1"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	json.put("data", null);
        	
        	return json.toString();
        	
			// TODO: handle exception
		}catch (Exception ex){
        	ex.printStackTrace();
            return  null;
        } finally {
            try {
                if(response!=null) {response.close();}
            }catch (Exception ex){}

            try {
                if(httpClient!=null) {httpClient.close();}
            }catch (Exception ex){}

        }
    }
    
}
