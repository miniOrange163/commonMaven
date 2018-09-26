package priv.linjb.learn.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import priv.linjb.common.util.base64.ImageBase64;
import priv.linjb.common.util.encrypt.EncryptUtils;
import priv.linjb.common.util.encrypt.MD5Encryption;
import priv.linjb.common.util.http.HttpUtils;
import priv.linjb.common.util.json.JsonUtils;
import priv.linjb.common.util.number.MathExtend;

public class HttpTest {

	//识别服务器更新参数配置接口
	private final static String queryUpdateConfig = "RecognitionServer/QueryConfig";
		
	//识别服务器更新参数配置接口
	private final static String updateConfig = "RecognitionServer/UpdateConfig";
	//识别服务器校验人员头像接口
	private final static String verifyImage = "RecognitionServer/VerifyImage";
	//识别服务器图像识别(1对1)接口
	private final static String recognitionOneToOne = "RecognitionServer/RecognitionOneToOne";
	//识别服务器图像识别(1对多)接口
	private final static String recognitionOneToMany = "RecognitionServer/RecognitionOneToMany";
	
	private final static String faceFeature = "faceStruct/structBase64.htm";
	
	private final static String facePosition = "faceStruct/facePositionBase64.htm";

	private final static String ip = "http://172.18.10.19:9088/";			//赵鹏依本机
//	private final static String ip = "http://172.18.10.176:9088/";		//识别服务器
//	private final static String ip = "http://172.18.100.123:9088/";		//识别服务器

	public static String transcoding(String str){
		if(str == null)
			return null;
		
		try {
			return new String(str.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String queryUpdateConfig(){
		
		String url = ip + queryUpdateConfig;
		System.out.println("调用请求： " + url);
		JSONObject json = new JSONObject();
		json.put("rspc_rsrvid", "111");

		String result = HttpUtils.postJsonObject(url, json);

	
		result = transcoding(result);
		System.out.println("返回结果: " + result);
		
		JSONObject response = JsonUtils.ConvertToObject(result, JSONObject.class);
		String data = response.getString("data");
		System.out.println(data);
		
	
		
		return result;
	
	}
	public static void updateConfig(){
		
		String url = ip + updateConfig;
		System.out.println("调用请求： " + url);
		JSONObject json = new JSONObject();
		json.put("rspc_rsrvid", "111");
		String xml = readFile();
		xml = xml+"\r\n";
		json.put("rspc_config_content", xml);
		System.out.println(JsonUtils.ConvertToString(json));
		String result = HttpUtils.postJsonObject(url, json);

	
		System.out.println("返回结果: " + transcoding(result));
	
	}
	public static void verifyImage(){
		String url = ip + verifyImage;
		System.out.println("调用请求： " + url);
		JSONObject json = new JSONObject();
		String base64 = ImageBase64.imageToBase64("e:/SAVE/test/1.jpg");
		json.put("imgbase64", base64);
		json.put("ext", "jpg");
		//System.out.println("入参: " + JsonUtils.ConvertToString(json));
		String result = HttpUtils.postJsonObject(url, json);
		System.out.println("返回结果: " + transcoding(result));
	}
	public static void recognitionOneToOne(){
		String url = ip + recognitionOneToOne;
		System.out.println("调用请求： " + url);
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		json1.put("imgbase64", ImageBase64.imageToBase64("e:/SAVE/test/1.jpg"));
		json1.put("ext", "jpg");
		json2.put("imgbase64", ImageBase64.imageToBase64("e:/SAVE/test/2.jpg"));
		json2.put("ext", "jpg");
		/*List list = new ArrayList<>();
		list.add(json1);
		list.add(json2);
		String result = HttpUtils.postJsonObject(url, list);*/
		
		JSONArray array = new JSONArray();
		array.add(json1);
		array.add(json2);
		System.out.println("入参：" + array.toString());
		String result = HttpUtils.postJsonObject(url, array);
		System.out.println("返回结果: " + transcoding(result));
	}
	public static void recognitionOneToMany(){
		String url = ip + recognitionOneToMany;
		System.out.println("调用请求： " + url);
		JSONObject json = new JSONObject();
		List<String> list = new ArrayList<String>();
		list.add("111");
		list.add("222");
		String base64 = ImageBase64.imageToBase64("e:/SAVE/test/1.jpg");
		json.put("imgbase64", base64);
		json.put("ext", "jpg");
		json.put("ostridList", list);

		String result = HttpUtils.postJsonObject(url, json);
		System.out.println("返回结果: " + transcoding(result));
	}
	
	public static String readFile(){
		File file = new File("e:/SAVE/test/config2.txt");
		
		if(!file.exists()){
			System.out.println("文件不存在");
			return null;
		}
		
		BufferedReader bufin = null;       
	    try {
			bufin = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    StringBuffer buffer = new StringBuffer();
	    String s = "";
	    try {
	      while ((s = bufin.readLine())!=null) {

	    	  if(s != null){
	    		  buffer.append(s);
	    	  }

	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }finally{
	        try {
	            bufin.close();
	   
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }

	   // System.out.println(buffer.toString());
	    
	    return buffer.toString();
		
	}
	
	public static String faceFeature(){
		String url = ip + faceFeature;
		System.out.println("调用请求： " + url);
		String key = "e9143fbd4e9b5fe493dd7be4b762d056";
		String facePosition = "0,0,416,232";
//		String base64 = ImageBase64.imageToBase64("E:\\SAVE\\模拟测试\\人脸\\过人记录\\008坐标&0,0,416,232.jpg");
		String base64 = ImageBase64.imageToBase64("E:\\SAVE\\模拟测试\\人脸\\18w全国在逃库~\\qgzt0816\\part8\\part9\\542121\\198412\\1___542121198412070031_欧布__5403020000002018051023.jpg");
		JSONObject json = new JSONObject();
		json.put("facePosition", facePosition);
		json.put("bigImg", base64);
		json.put("autoKey",EncryptUtils.md5(key + facePosition));
		System.out.println("autoKey:" + json.getString("autoKey"));
		String result = HttpUtils.postJsonObject(url, json);

	
		result = transcoding(result);
		System.out.println("返回结果: " + result);
		
		JSONObject response = JsonUtils.ConvertToObject(result, JSONObject.class);
		String data = response.getString("data");
		System.out.println(data);


		
		String feature = response.getJSONObject("data").getString("feature");
		System.out.println("\n\n\nfeature: " + feature);
		String smallFacePosition = response.getJSONObject("data").getString("smallFacePosition");
		System.out.println("\n\n\nsmallFacePosition: " + smallFacePosition);
		//feaTest(feature);

		String[] feaList = feature.split(",");
		System.out.println("length:" + feaList.length);
		return result;
	}
	
	public static String facePosition(){
		String url = ip + facePosition;
		System.out.println("调用请求： " + url);
		
		String base64 = ImageBase64.imageToBase64("E:\\SAVE\\模拟测试\\人脸\\头像\\q1.jpg");
		JSONObject json = new JSONObject();
		json.put("bigImg", base64);

		String result = HttpUtils.postJsonObject(url, json);

	
		result = transcoding(result);
		System.out.println("返回结果: " + result);
		
		JSONObject response = JsonUtils.ConvertToObject(result, JSONObject.class);
		String data = response.getString("data");
		System.out.println(data);
		
		//String feature = response.getJSONObject("data").getString("feature");
	

		return result;
	}
	
	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();

//		for (int i = 0; i < 100; i++) {
			faceFeature();

//		}
//		facePosition();

		//queryUpdateConfig();
		//updateConfig();
		//verifyImage();
		//recognitionOneToOne();
		//recognitionOneToMany();
		
		
		long end = System.currentTimeMillis();
		double seconds = new BigDecimal(end - start ).divide(new BigDecimal(1000)).doubleValue();
		System.out.println("调用时间："+seconds+"秒");
		
		/*String str = "abc\r\nab";
		Pattern p = Pattern.compile("\r|\n");
        Matcher m = p.matcher(str);
        str = m.replaceAll("");
      
        
		System.out.println(str);*/
	
		
	}
	
	public static void feaTest(String str){
		//String str ="0.100974,-0.032554,-0.041175,0.026740,0.064946,0.153240,-0.006296,-0.027821,-0.088463,-0.015760,-0.017487,-0.042182,-0.014434,0.042524,-0.041200,-0.064474,0.002173,-0.033062,-0.011899,-0.001312,0.030676,0.168587,-0.017735,0.028555,-0.040372,-0.066418,-0.013129,0.005292,-0.110499,0.021746,-0.026721,0.063778,-0.057583,-0.000332,0.004796,-0.011635,0.052374,0.024991,0.139351,-0.029036,-0.097120,-0.023092,-0.002333,0.153061,0.000849,0.022032,-0.006174,0.004578,-0.043158,0.108702,0.034852,0.086363,-0.005399,-0.009154,-0.061519,-0.043803,0.031444,0.160420,-0.002549,-0.050566,-0.041461,0.155102,0.045308,0.036318,-0.024642,0.000972,-0.003939,-0.027434,0.051035,-0.032690,-0.019722,0.155169,-0.006791,0.064528,-0.039712,-0.083854,0.073886,-0.033256,0.059368,-0.049313,0.005069,-0.035521,0.031974,0.086852,-0.121600,0.009462,-0.000050,-0.027588,-0.066809,0.040153,0.077022,-0.028837,-0.032018,0.045589,0.050113,0.008041,0.007322,-0.068917,0.035381,0.032326,-0.052433,-0.006346,0.045889,0.077793,0.112961,-0.065174,0.000792,-0.004681,0.059397,0.005611,0.033958,0.111924,0.092959,-0.065558,0.102703,0.026088,0.069636,-0.001642,-0.056865,-0.080966,0.030826,-0.016383,0.036501,0.051443,0.047337,-0.010028,0.003886,0.092071,0.077326,0.000959,0.014048,-0.007484,-0.015275,-0.030478,-0.085349,-0.008141,0.052026,0.001729,-0.018568,0.090839,-0.072748,0.027037,-0.000706,-0.124704,-0.097515,-0.049700,-0.020883,0.048224,-0.001465,-0.031771,-0.008353,-0.013190,0.001482,0.014727,-0.018442,-0.090225,-0.063118,-0.000159,-0.058062,0.047239,-0.034600,0.243315,0.040271,-0.071252,-0.034357,-0.073385,-0.050539,0.033234,-0.064883,-0.085370,0.034207,0.032382,-0.096067,0.007007,0.084867,-0.050119,-0.059798,-0.114687,-0.025417,0.049123,-0.047999,-0.081666,0.015628,0.040717,0.069055,0.006817,-0.041644,0.030546,-0.015853,0.014225,0.015653,-0.099030,-0.026617,-0.045718,-0.017916,0.007335,0.040185,0.072433,-0.101302,0.052758,0.081747,0.019666,0.068124,-0.019788,-0.125582,-0.026609,0.051078,-0.004550,-0.003030,-0.028812,-0.027930,-0.045263,-0.007237,-0.009527,0.152521,0.018003,-0.098655,0.057666,0.127652,0.019986,-0.074167,-0.008162,-0.036142,-0.105000,-0.008283,-0.017360,-0.106455,-0.079382,-0.020499,-0.019846,0.078990,-0.119495,0.027686,0.184307,-0.001293,0.111704,0.013943,0.024608,0.032932,-0.052649,-0.006151,-0.054962,0.005396,0.055377,0.063308,0.006829,0.014197,0.081332,0.114059,-0.028457,-0.116195,0.045283,0.007983,0.136820,-0.019454,-0.063078,-0.037733,0.015432,0.132620,0.021246,0.041467,-0.022873,-0.027900,0.047390,0.071520,0.059750,0.000534,0.110743,0.006806,0.115469,-0.033095,0.088288,0.036300,0.070710,-0.090953,-0.081036,-0.014280,0.000838,-0.084418,-0.001943,0.059016,-0.086023,0.054227,0.032788,0.071328,-0.002146,-0.072243,0.049274,-0.027009,0.018160,-0.050489,-0.070917,0.052506,-0.003833,0.035375,0.104210,0.006396,0.033539,0.059091,0.052289,0.059164,0.019799,-0.009607,0.018581,-0.010254,0.085192,0.008418,-0.015576,-0.033294,-0.086992,0.136432,0.009140,-0.094001,0.124679,0.172492,-0.017841,0.033816,-0.051482,0.005462,0.018421,0.073188,-0.064225,-0.068251,0.068881,0.077701,-0.088512,-0.014154,-0.073712,-0.016384,-0.079632,-0.043879,0.079784,0.043264,0.034907,-0.005932,-0.043120,-0.078581,-0.005213,0.030007,0.023032,-0.047205,-0.008194,0.086493,0.061898,0.055600,-0.087471,-0.009163,-0.059624,-0.053035,-0.123112,-0.102808,0.081963,-0.002330,0.016651,0.052965,0.051665,0.023871,-0.105896,-0.046498,0.011524,-0.071177,0.027686,-0.023813,0.040351,0.052881,-0.067179,0.081431,-0.035802,0.128334,0.023200,-0.014614,-0.000749,0.022897,0.018705,-0.046607,-0.051761,0.078109,-0.102150,0.017013,0.078548,0.041620,0.096329,-0.009053,0.058588,-0.049529,-0.001724,0.141585,-0.006885,0.020546,0.015636,-0.002954,-0.080282,-0.017135,-0.003775,-0.085663,-0.067725,-0.029756,-0.004727,-0.055375,-0.107459,-0.009192,-0.108265,-0.075383,0.154344,-0.008613,0.020197,-0.028409,0.061553,0.000388,0.017556,-0.014587,-0.055523,0.030721,-0.045117,-0.036807,0.097138,-0.002845,0.007818,-0.019086,0.049178,-0.021292,0.044223,0.016894,0.022611,-0.018609,-0.035741,-0.036627,-0.032463,-0.026827,0.030288,-0.101561,0.059186,0.078205,0.071984,-0.024715,-0.014613,0.025188,0.053456,-0.038202,0.048384,0.043953,0.045695,-0.045904,0.008588,-0.005928,0.053697,0.060480,0.051258,-0.111095,-0.065766,-0.100137,0.017156,0.023915,0.000396,0.070426,-0.135338,-0.113987,-0.034083,0.021050,0.112731,0.015023,0.042625,0.033273,-0.071523,0.015701,0.005009,-0.007653,0.003603,0.048104,0.043792,0.009338,0.017758,-0.011563,0.005768,-0.010038,-0.076498,0.065655,0.034459,-0.026767,-0.058475,-0.025996,0.008999,0.155127,-0.046434,0.265557,-0.068810,0.039795,0.071339,-0.025896,0.010716,0.007017,0.150270,-0.103805,-0.139585,-0.111417,0.122841,0.015383,0.014985,-0.074675,0.052617,0.023159,0.034927,-0.008794,0.018307,-0.018093,-0.022978,0.083920,-0.029649,-0.078587,-0.025234,-0.062626,0.069430,-0.057002,-0.016257,0.144276,0.041894,0.039906,-0.098633,0.002771,-0.031051,0.002291,0.031573,0.002245,0.000941,-0.011066,-0.093578,-0.029430,0.007680,-0.039464,-0.053765,0.029347,0.030726,0.051939,-0.029619,-0.015282,0.125605,0.011507,0.020639,0.001041,-0.045088,0.017965,-0.001387,0.019894,-0.020763,0.005884,-0.032219,-0.011923,-0.023622,0.031536,-0.017494,-0.002268,0.021977,0.008029,-0.040304,0.021638,0.055864,0.004162,-0.009223,0.042446,-0.029643,-0.041267,-0.030540,0.047278,-0.030076,0.080388,-0.026631,0.008195,0.024604,-0.096933,0.000102,-0.017363,0.034408,0.013602,0.013696,-0.054098,0.000431,0.085023,0.031802,0.073034,0.033638,0.047248,0.039225,0.061832,0.075762,0.070640,0.008803,0.013643,-0.075943,-0.027240,0.031065,-0.095142,-0.002848,0.009426,0.075627,-0.027901,0.018291,-0.044977,-0.004398,-0.021525,0.035871,0.028827,-0.003987,-0.068190,-0.054350,-0.005446,0.046175,-0.070570,-0.042068,-0.072949,-0.076338,-0.029154,0.009963,-0.014544,0.042149,0.013524,0.030415,-0.067945,0.001092,0.039318,0.087478,-0.034863,0.025790,0.022893,-0.057945,0.031025,0.006361,-0.077305,-0.011103,-0.057841,-0.063302,0.085862,0.024533,-0.072902,0.085457,-0.066417,0.011717,-0.012343,-0.087644,0.050775,-0.076925,0.018180,0.030021,0.007216,-0.052300,-0.004317,0.004093,0.007376,-0.017629,-0.047665,0.068932,-0.022354,-0.024065,-0.051423,-0.021192,0.028842,0.000003,-0.011974,0.058936,-0.042507,0.106269,0.006898,-0.054007,-0.035850,-0.035531,0.008522,-0.036392,-0.025801,-0.091885,-0.048202,0.014607,-0.030394,-0.000496,-0.026675,0.053464,0.078158,0.046725,-0.025691,0.000236,0.048696,-0.015385,-0.037845,0.028652,-0.044999,-0.050012,0.024593,0.015645,-0.067736,0.000415,0.020761,0.007355,0.010117,-0.013902,-0.061492,-0.039293,-0.053259,0.037572,-0.001763,0.134021,0.074500,0.005128,0.055172,0.046986,0.063261,0.015783,-0.017958,0.005865,-0.046769,-0.009124,0.023368,0.042805,0.022467,0.034012,-0.030429,-0.047550,0.064306,0.028844,0.008472,0.002812,0.028073,-0.016326,0.045211,-0.030307,-0.005124,-0.021238,-0.076883,0.002609,-0.054719,0.000858,-0.009530,0.060216,0.035227,0.021909,0.030197,-0.038107,0.001313,0.066284,0.003328,0.028454,-0.045605,-0.031296,0.028999,0.039918,0.008880,0.012747,0.049890,0.041255,0.012721,-0.066573,-0.091504,-0.072903,-0.005537,0.006215,-0.090608,-0.012342,0.023828,0.015451,-0.001897,-0.060549,0.014101,-0.002925,0.014480,0.015315,0.005718,-0.062787,-0.055615,-0.035665,0.014181,0.000600,-0.045432,0.030783,0.022634,-0.044740,-0.003484,0.019169,-0.075725,0.011345,0.058411,-0.036796,0.006287,-0.040395,0.048643,-0.025388,0.011316,0.103340,-0.001905,-0.001377,0.023954,-0.004762,0.121863,0.018319,0.039882,-0.018552,-0.021592,0.008123,0.007359,0.043464,-0.025449,-0.010777,-0.028832,0.027574,-0.033816,0.004444,-0.029569,-0.060842,0.013238,-0.053584,0.048252,-0.017489,-0.036163,0.022083,0.001838,-0.010750,0.019816,0.007349,-0.072073,-0.050447,0.015946,-0.052783,0.067844,0.035441,-0.036024,-0.017001,-0.043595,-0.088533,0.045060,-0.065266,-0.038918,-0.045354,0.013704,-0.000717,-0.042535,-0.006739,0.037061,0.061999,-0.032416,0.019627,0.030077,0.063754,-0.005633,0.033951,-0.056809,0.051953,0.030714,-0.082129,-0.009710,0.018835,0.089451,0.044816,0.064218,0.063391,0.025510,-0.013563,-0.039590,-0.024517,0.059738,0.008104,0.118671,-0.032505,0.011270,0.139144,0.009945,0.024375,-0.050121,0.043024,-0.089147,-0.016389,-0.060086,-0.007331,0.054160,-0.061676,0.013713,0.098440,0.000586,-0.009537,0.038214,-0.062369,0.027098,-0.000409,0.066236,0.014257,0.029053,-0.008126,0.007703,0.004832,-0.046394,0.027528,0.047998,-0.054348,-0.021161,0.029926,0.028606,-0.036965,0.011551,0.079114,0.006055,0.032022,-0.016598,-0.000595,-0.019055,0.087018,0.002278,-0.012576,0.041810,-0.062957,0.061218,-0.023778,0.020433,0.085482,0.006254,-0.016548,-0.045807,-0.065779,-0.004622,-0.046259,0.024613,0.062472,-0.076627,0.058537,-0.016564,0.016840,0.031313,-0.025623,-0.041848,0.019146,-0.048266,0.043566,-0.008843,0.018650,0.079303,0.060243,0.018483,0.079920,0.035250,0.042866,-0.014706,-0.028234,0.010609,0.020178,0.034129,-0.022691,-0.070097,0.010332,-0.015388,0.044121,-0.109222,-0.008464,0.030314,0.024867,0.022996,-0.016236,-0.007659,0.007390,-0.006130,-0.028186,-0.057973,0.051283,0.040365,0.018111,0.018406,0.008014,0.008398,-0.011310,0.029914,-0.017624,0.042485,-0.033618,-0.042173,0.020454,-0.012317,0.059252,0.030546,0.002479,-0.075370,0.018592,0.026973,0.021009,0.001099,0.000250,-0.021406,0.090639,0.020454,0.062773,0.001651,0.035485,0.024219,-0.072903,-0.065035,-0.003151,0.136859,0.016413,0.039532,-0.040280,-0.000751,0.027467,0.010852,-0.000551,-0.027400,-0.104746,-0.074680,-0.056336,0.057450,0.027297,0.058107,0.081652,-0.071809,0.014191,-0.018078,0.046524,-0.032823,-0.011493,0.018667,-0.001865,0.033149,0.007425,-0.014485,-0.010337,0.016577,-0.046489,-0.042110,0.071382,-0.039360,0.068568,-0.014214,-0.024687,-0.073474,0.043471,-0.062614,0.069401,-0.080567,0.025473,0.036710,-0.052102,-0.053474,0.052797,-0.075825,-0.027362,0.080837,0.029479,-0.033563,0.111305,-0.059497,0.013671,-0.030256,0.010835,0.013318,0.006872,0.010840,-0.012688,0.053724,0.134814,-0.049652,-0.044274,0.036764,0.047533,-0.086592,0.070226,0.139261,0.044772,-0.025045,0.096412,0.001269,0.020600,0.142812,0.049300,-0.046220,-0.041067,0.001825,-0.009172,0.142392,0.011042,0.061289,0.032947,-0.022044,-0.064300,0.104513,0.013834,-0.008346,-0.054659,-0.008359,0.082962,-0.003430,0.007479,0.100058,-0.006253,-0.007099,0.097458,0.081792,-0.029616,-0.037505,-0.026108,0.122688,0.022869,-0.011524,-0.097741,-0.025611,-0.075695,0.016192,0.031887,0.115803,0.030896,-0.003598,-0.102501,0.050691,0.013740,-0.043149,0.059103,-0.100740,-0.008061,0.007493,0.026077,0.010106,0.074788,0.061116,-0.050094,-0.038794,-0.081875,0.004023,0.074155,-0.000844,-0.021496,0.063989,0.125171,0.016018,-0.011961,-0.020353,0.036944,-0.007029,0.009573,-0.093890,0.016451,0.048528,-0.137896,-0.017792,-0.105298,0.045480,-0.051792,0.121885,0.150468,0.005938,0.042426,0.020981,0.074423,-0.009410,0.062631,-0.066887,0.049600,0.006055,0.003011,-0.050547,0.126346,0.109055,-0.010895,-0.124651,-0.014627,0.120089,-0.017358,0.029624,0.110479,-0.044733,-0.094531,0.027922,0.010118,0.132506,0.104737,-0.140203,0.037898,-0.048041,-0.031589,-0.006084,-0.046152,0.013872,0.035485,-0.150278,-0.015387,-0.032868,0.018540,0.038887,0.021653,-0.007978,0.002542,0.000731,0.014665,0.099202,0.096808,-0.019168,-0.065119,0.081758,-0.048353,0.007253,0.001325,0.004488,-0.057873,-0.001704,-0.082621,0.042592,0.043813,-0.051473,-0.021604,0.158943,-0.030133,0.020512,0.058114,0.055192,-0.053811,0.019677,0.007968,-0.015804,-0.082191,-0.000986,0.020686,-0.082640,-0.011645,-0.081591,0.045614,-0.105209,-0.086080,-0.011623,0.068279,-0.094846,-0.077186,0.006807,0.016724,-0.046654,0.051524,0.028005,-0.006259,0.058932,-0.000260,-0.022502,0.063019,0.022804,0.025896,-0.084130,0.029572,-0.008677,0.101468,-0.000144,-0.020418,0.006386,-0.000574,0.026638,0.087271,0.038667,0.098716,0.041740,0.075958,-0.103097,0.064659,0.118605,-0.006152,-0.032472,-0.073727,-0.003443,-0.006638,0.012858,0.094135,0.005621,0.061144,-0.096329,0.100466,-0.015850,-0.058355,0.030366,-0.062449,-0.049591,0.020929,0.038141,-0.008342,-0.020196,0.155165,-0.113925,0.029494,0.007272,0.077442,0.033129,0.019694,-0.080490,0.054297,0.037324,0.106293";
		//String str = "0.1,0.2,0.3";
		String[] f1 = str.split(",");
		String[] f2 = str.split(",");
		float[] d1 = new float[f1.length];
		float[] d2 = new float[f2.length];
		for(int i=0;i<f1.length;i++){
			d1[i] = Float.parseFloat(f1[i]);
		}
		for(int i=0;i<f2.length;i++){
			d2[i] = Float.parseFloat(f2[i]);
		}
		System.out.println(f1.length);
		System.out.println(d1.length);
		//System.out.println("string: " +calcDist(f1,f2,f1.length));
		System.out.println("float: " +calcDist(d1,d2,d1.length));
		
	}
    public static float calcDist(float[] feat1,float[] feat2,int len){
        float dist = dotProduct(feat1,feat2,len);
        System.out.println("dist: " + dist);
        return (dist + 1.f) * 0.5f;
    }
    public static float dotProduct(float[] feat1,float[] feat2,int len){
        float val = 0.f;
        for (int i = 0; i < len; ++i) {
            val += feat1[i] * feat2[i];
        }
        return val;
    }

    public static float calcDist(String[] feat1,String[] feat2,int len){
        float dist = dotProduct(feat1,feat2,len);
        System.out.println("dist: " + dist);
        return (dist + 1.f) * 0.5f;
    }

    public static float dotProduct(String[] feat1,String[] feat2,int len){
        float val = 0.f;
        for (int i = 0; i < len; ++i) {
            //val += MathExtend.multiply(feat1[i], feat2[i]).floatValue();
        	val += Float.parseFloat(feat1[i]) * Float.parseFloat(feat2[i]);
        }
        return val;
    }
}
