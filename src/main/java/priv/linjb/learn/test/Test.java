package priv.linjb.learn.test;

import java.io.File;
import java.io.FileOutputStream;

public class Test {

	public static void main(String[] args) {
		
		String str = "bn-bin/bin";
		
		String t = str.replaceAll("bin$", "web");
		
		System.out.println(t);
		
	}
	
	public static void test(byte[] b){
		
		byte[] c = b;
		System.out.println(c);
				
	}
}
