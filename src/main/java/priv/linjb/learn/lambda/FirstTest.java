package priv.linjb.learn.lambda;

import java.util.Arrays;
import java.util.List;
/**
 * @author Admin
 *
 * filter mapToInt reduce getAsInts
 */
public class FirstTest {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("max","min","vue","java","scala");

		long sum = list.stream().filter(str -> str.startsWith("m")).count();

		System.out.println("以m开头的数量是：" + sum);

		int sum2 = list.stream().mapToInt(String::length).reduce((s,item) -> s + item).getAsInt();

		System.out.println("所有字母的长度总和:" + sum2);

		int sum3 = list.stream().filter(str -> str.startsWith("m")).mapToInt(String::length).reduce((s,item) -> s + item).getAsInt();

		System.out.println("以m开头的单词总长度:" + sum3);


	}
}
