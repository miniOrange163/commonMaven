package priv.linjb.common.logic.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 正则匹配文件夹路径是否正确
 * @author Admin
 *
 */
public class CatalogTest {

	public static void main(String[] args) {
		//文件夹应该不包含字符/ \ : * ? " < > | 
		String str = "";
		
		Pattern pattern = Pattern.compile("((?![\\\\]|[:]|[*]|[?]|[\"]|[<]|[>]|[|]).)*");
		 Matcher matcher = pattern.matcher(str);
		 
		boolean b= matcher.matches();
		  //当条件满足时，将返回true，否则返回false
		  System.out.println(b);
	}
}
