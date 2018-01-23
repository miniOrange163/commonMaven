package priv.linjb.learn.number;

import java.math.BigDecimal;
import java.util.Date;

import priv.linjb.common.util.number.MathExtend;

public class BigDecimalTest {

	public static void test1(){
		
		double a = 6;
		double b = 2;
		
		System.out.println("加: " + MathExtend.add(a, b));
		System.out.println("减: " + MathExtend.subtract(a, b));
		System.out.println("乘: " + MathExtend.multiply(a, b));
		System.out.println("除: " + MathExtend.divide(a, b));
		System.out.println("保留2位小数,四舍五入: "+MathExtend.round(2.245, 2,BigDecimal.ROUND_HALF_UP));
		System.out.println("保留2位小数: "+MathExtend.round(2.249, 2,BigDecimal.ROUND_FLOOR));
		
	}
	public static void test2(){
		
		long start = 2551;

		long end = 3000;
		double d = new BigDecimal(end - start ).divide(new BigDecimal(1000)).doubleValue();
		
		System.out.println(MathExtend.round(d, 2,BigDecimal.ROUND_FLOOR));
	}
	public static void main(String[] args) {

		test1();
		test2();
		
	}
}
