package priv.linjb.common.parse.dom4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;


public class Xstream {
	
	private static String  xml;

	public static void main(String[] args) throws Exception{
		//初始化
		init();

		XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		//解析xml文本
		xstream.processAnnotations(Results.class);
		Results results = (Results) xstream.fromXML(xml);
		//将解析出来的Results类对象转化成list列表
		List<Info> list = createList(Info.class,results);
		
		for(int i=0;i<list.size();i++){
			//打印
			Info info = list.get(i);
			System.out.println(info.toString());
		}
	
	}
	public static void init(){
		//初始化xml文本
		xml ="<results name=\"list\"><row pubtime=\"2016-04-13 16:40:13\" author=\"APP\"  id=\"140\" title=\"什么是公告\" content=\"公告，是公开宣告。\" /><row pubtime=\"2016-04-13 16:36:50\" author=\"网站\" id=\"138\" title=\"12345678\" content=\"12345678\"  /><row pubtime=\"2016-04-06 15:02:44\" author=\"网站\" id=\"134\" title=\"关于网站用户注册流程说明1\" content=\"关于用户注册流程说明\"  /><row pubtime=\"2016-03-30 18:32:13\" author=\"APP\"  id=\"126\" title=\"关于网站使用说明\" content=\"测试\"  /><row pubtime=\"2016-03-30 18:29:26\" author=\"网站\" id=\"125\" title=\"关于手机App使用说明\" content=\"123\"  /></results>";
	}
	public static <T> List createList(Class<T> clz ,Results results) throws Exception{
		List list = new ArrayList();
		for(Row row :results.getRows()){
			//根据class和Row生成对象放入list
			list.add(createObject(clz,row));
			
		}
		return list;
	}
	public static <T> T createObject(Class<T> clazz ,Row row) throws Exception{
		//初始化对象
		T obj = clazz.newInstance();
		//遍历Info类中所有方法
		for (Method method : clazz.getDeclaredMethods()) {
			String methodName = method.getName();
			Class[] perams = method.getParameterTypes();
			//找到set开头，长度大于3，并且入参数量为1的方法
			if (methodName.startsWith("set") && methodName.length() > 3 && perams.length == 1) {
				
				String temp = methodName.substring(3, methodName.length());
				//拿到属性名称
				String fieldName = temp.toLowerCase();
				//根据属性名称从HashMap中拿到对应的值
				String value = row.get(fieldName);
				
				if(value != null){
					//拿到该方法入参的Class，根据入参类型来决定调用方法形式
					Class  paramClass = perams[0];
					if (String.class.equals(paramClass)) {
						method.invoke(obj, value);
					} else if (Integer.class.equals(paramClass) || int.class.equals(paramClass)) {
						method.invoke(obj, Integer.valueOf(value));
					} else if (Long.class.equals(paramClass) || long.class.equals(paramClass)) {
						method.invoke(obj, Long.valueOf(value));
					} else if (BigDecimal.class.equals(paramClass)) {
						method.invoke(obj, new BigDecimal(value));
					} else if (Boolean.class.equals(paramClass) || boolean.class.equals(paramClass)) {
						if(value.equals("true")||value.equals("TRUE"))
							method.invoke(obj, true);
						if(value.equals("false")||value.equals("FALSE"))
							method.invoke(obj, false);
					}
				}
			
			}
		}
		return obj;
	}
}
