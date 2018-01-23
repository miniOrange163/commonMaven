package priv.linjb.learn.lambda.function;

import java.util.function.Supplier;

public class FunctionGroup {

	public static Supplier<Integer> func(int x,int y,int z){
		
		return ()->add(x,y,z);
	
	}
	
	public static Integer cut(int x,int y ,int z){
		return x - y - z;
	}
	public static Integer add(int x,int y ,int z){
		
		return x + y + z;
	}
}
