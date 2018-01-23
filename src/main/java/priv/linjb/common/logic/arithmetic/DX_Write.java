package priv.linjb.common.logic.arithmetic;

import java.util.HashMap;
import java.util.Map;
//数字转换成大小写练习
public class DX_Write {
	
	private  Map<String,String> num_out;
	private  Map<String,String> postion;
	
	DX_Write(){
		
		num_out = new HashMap<String,String>();
		postion = new HashMap<String,String>();
		
		
		num_out.put("0", "零");
		num_out.put("1", "壹");
		num_out.put("2", "贰");
		num_out.put("3", "叁");
		num_out.put("4", "肆");
		num_out.put("5", "伍");
		num_out.put("6", "陆");
		num_out.put("7", "柒");
		num_out.put("8", "捌");
		num_out.put("9", "玖");
		
		postion.put("-2", "分");
		postion.put("-1", "角");
		postion.put("0", "");
		postion.put("1", "元");
		postion.put("2", "拾");
		postion.put("3", "佰");
		postion.put("4", "仟");
		postion.put("5", "万");
		postion.put("6", "拾");
		postion.put("7", "佰");
		postion.put("8", "仟");
		postion.put("9", "亿");
	}
	//输出小数点前的字符串
	public String pre(String num){
		
			StringBuffer result = new StringBuffer();
		

			int length = num.length();
			

			char tmp[] = num.toCharArray();
			
			int zero = 0;			//记录连续出现0的个数
			for(int i=0;i<length;i++){
				
				if(tmp[i] == '0'){
					zero ++ ;
				}
				else{
					zero = 0;
				}
				//当出现如10000，0连续出现到个位的情况，删除第一个'零'字
				if(length-i==1&&tmp[i]=='0'&&zero > 1){
					result.deleteCharAt(result.length()-1);
				}
				//避免出现重复的'零'
				if(zero <=1){
					//当个位是0时
					if(i==length-1&&tmp[i]=='0'){
						
					}
					//当最高位是0时
					else if(i==0&&tmp[i]=='0'){
						
					}
					//当最高位是0时
					else if(length-5==i&&tmp[i]=='0'){
						
					}
					//排除以上的可能
					else
						result.append(num_out.get(String.valueOf(tmp[i])));
				}
				//当某位是0时，不输出仟佰拾这样的单位
				if(tmp[i] !='0'||length-i==1||length-i==5){
					result.append(postion.get(String.valueOf(length-i)));
					
				}
	
			
			}

		return result.toString();
	}
	//输出小数点后的字符串
	public String next(String num){
		
		StringBuffer result = new StringBuffer();
		
		char tmp[] = num.toCharArray();
		
		result.append("零");
		for(int i=1;i<=tmp.length;i++){
			
			if(tmp[i-1]!='0'){
				result.append(num_out.get(String.valueOf(tmp[i-1])));
				result.append(postion.get(String.valueOf(i-i-i)));
			}
			
		}
		
		
		return result.toString();
	}
	public  String change(String num){
		
		StringBuffer result = new StringBuffer();
		
		String value[];
		String num1,num2;
		
		if(num.indexOf(".")<0){
			result.append(pre(num));
		}
		else{
			value = num.split("\\.");
			num1 = value[0];
			num2 = value[1];
			result.append(pre(num1));
			result.append(next(num2));
		}
		
		result.append("整");
		return result.toString();
	}
	public static void main(String[] args) {
		
		
		DX_Write DX = new DX_Write();
		String num = "102301001.50";
	
		String result = DX.change(num);
		
		System.out.println(result);
		
	}
}
