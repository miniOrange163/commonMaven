package priv.linjb.common.util.json;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	/**
	 * 将对象序列化成出字符串
	 * @param object
	 * @return
	 */
	public static String convertToString(Object object)
	{
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	public static <T> T convertToObject(String json, Class<T> classT)
	{
		ObjectMapper mapper = new ObjectMapper();  
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        T object = null;
		try {
			object = (T)mapper.readValue(json, classT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return object;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T convertToObject(String json, TypeReference valueTypeRef)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        T object = null;
		try {
			object = (T)mapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return object;
	}
	
	
}
