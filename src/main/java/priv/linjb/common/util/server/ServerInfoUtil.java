package priv.linjb.common.util.server;

import org.apache.commons.lang3.StringUtils;

import javax.management.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/1/31
 *  
 * @name: 获取服务器相关信息的工具类
 *
 * @Description: 
 */
public class ServerInfoUtil {

    public static String getLocalServerAddressPortHttp(){
        String http = "http://";
        String address = getLocalServerAddress();
        String port = getLocalServerPortHttp();

        if(StringUtils.isNotBlank(address)&&StringUtils.isNotBlank(port)){
            return http + address + ":" + port + "/";
        }
        else {
            return null;
        }
    }
    public static String getLocalServerAddressPortHttps(){
        String http = "https://";
        String address = getLocalServerAddress();
        String port = getLocalServerPortHttp();

        if(StringUtils.isNotBlank(address)&&StringUtils.isNotBlank(port)){
            return http + address + ":" + port + "/";
        }
        else {
            return null;
        }
    }
    /**
     * 获取服务器IP
     * @return
     */
    public static String getLocalServerAddress(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        String address = addr.getHostAddress();

        return address;
    }

    /**
     * 获取服务器端口-https协议
     * @return
     */
    public static String getLocalServerPortHttps(){
        try {
            return getServerPort(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取服务器端口-http协议
     * @return
     */
    public static String getLocalServerPortHttp(){
        try {
            return getServerPort(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取服务端口号
     * @return 端口号
     * @throws ReflectionException
     * @throws MBeanException
     * @throws InstanceNotFoundException
     * @throws AttributeNotFoundException
     */
    private static String getServerPort(boolean secure) throws AttributeNotFoundException, InstanceNotFoundException, MBeanException, ReflectionException {
        MBeanServer mBeanServer = null;
        if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
            mBeanServer = (MBeanServer)MBeanServerFactory.findMBeanServer(null).get(0);
        }

        if (mBeanServer == null) {

            return "";
        }

        Set<ObjectName> names = null;
        try {
            names = mBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
        } catch (Exception e) {
            return "";
        }
        Iterator<ObjectName> it = names.iterator();
        ObjectName oname = null;
        while (it.hasNext()) {
            oname = (ObjectName)it.next();
            String protocol = (String)mBeanServer.getAttribute(oname, "protocol");
            String scheme = (String)mBeanServer.getAttribute(oname, "scheme");
            Boolean secureValue = (Boolean)mBeanServer.getAttribute(oname, "secure");
            Boolean SSLEnabled = (Boolean)mBeanServer.getAttribute(oname, "SSLEnabled");
            if (SSLEnabled != null && SSLEnabled) {// tomcat6开始用SSLEnabled
                secureValue = true;// SSLEnabled=true但secure未配置的情况
                scheme = "https";
            }
            if (protocol != null && ("HTTP/1.1".equals(protocol) || protocol.contains("http"))) {
                if (secure && "https".equals(scheme) && secureValue) {
                    return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
                } else if (!secure && !"https".equals(scheme) && !secureValue) {
                    return ((Integer)mBeanServer.getAttribute(oname, "port")).toString();
                }
            }
        }
        return "";
    }

}
