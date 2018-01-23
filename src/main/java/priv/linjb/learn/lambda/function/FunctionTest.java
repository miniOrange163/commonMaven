package priv.linjb.learn.lambda.function;

import java.util.function.Function;
import java.util.function.Supplier;


public class FunctionTest {

	public static void main(String[] args) {
		
	}
	
	public static void test(Supplier<Integer> func){
		int x=5,y=1,z=2;
		int i = 0;
		
		int val = func.get();
		
		System.out.println(val);
		
		
	}
	
	
	
}
