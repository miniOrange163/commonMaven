package priv.linjb.common.logic.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.helpers.PatternParser;

public class Test {

	
	public static void arr(){

		String str ="abc , jife df. dfe";
		String[] arr = str.split("[. ,]+");
		for(int i=0;i<arr.length;i++)
			System.out.println(arr[i]);
	}
	public static void regex(){
		
		 /* Pattern pattern = Pattern.compile(".*(abc|ttt|ccc).*");
		  Matcher matcher = pattern.matcher("fefeabfecccttabdefe");*/
		/*Pattern pattern = Pattern.compile("^data:image/(png|gif|jpg|jpeg|bmp|tif|psd|ICO);base64,.*");
		  Matcher matcher = pattern.matcher("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAA");
		 */ 
		Pattern pattern = Pattern.compile("^.*[.](png|gif|jpg|jpeg|bmp|tif|psd|ICO)$");
		  Matcher matcher = pattern.matcher("/gxps/photo/upload/20160527175815_172.jpg");
		 
		boolean b= matcher.matches();
		  //当条件满足时，将返回true，否则返回false
		  System.out.println(b);
		                                                               
	}                                                                  
	public static void main(String[] args) {                           
	                                                                   
		//regex();                                                     
		String str = "#headInfo.html";                                 
		                                                               
		if(str.charAt(0) == '#')                                       
			System.out.println(str.substring(1,str.length()));         
                                                                       
	}                                                                  
}
