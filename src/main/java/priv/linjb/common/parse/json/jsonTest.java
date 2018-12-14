package priv.linjb.common.parse.json;

import net.sf.json.JSONObject;
import priv.linjb.common.util.json.JsonUtils;

public class jsonTest {

	public static void main(String[] args) {
		//String result = "{\"errorcode\":\"1\"}";
		String result = "{\"errorcode\":\"0\",\"message\":\"执行成功\",\"data\":{\"verifyResult\":\"1\",\"facepos\":\"\",\"smallImages\":[{}]}}";
	
		
		JSONObject repsonse = JsonUtils.convertToObject(result, JSONObject.class);
		
		String errorcode = (String) repsonse.get("errorcode");
		if(errorcode != null && "0".equals(errorcode)){
			System.out.println(true);
		}
		else{
			System.out.println(false);
		}
	}
}
