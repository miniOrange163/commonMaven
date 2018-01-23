package priv.linjb.learn.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.util.SystemOutLogger;

public class SecondTest {
	/**
	 * collect
	 * 获取每个用户的年龄，生成新数组，并打印
	 */
	@SuppressWarnings("unchecked")
	private static void getAllUserAge(){

		List<User> list = ListUtil.getUserList();
		//获取每个用户的年龄，生成新数组，并打印
		//第一种方式
		List ageList = list.stream().mapToInt(User::getAge).
				collect(ArrayList::new, List::add, List::addAll);
		System.out.println("用户年龄的数组:");
		ageList.stream().forEach(obj -> {
				System.out.print(obj + "\t");
				
		});
		System.out.println("\n");
		//第二种方式
		List ageList2 = list.stream().map(User::getAge).collect(Collectors.toList());
		ageList2.stream().forEach(obj -> {
			System.out.print(obj + "\t");
			
		});
		System.out.println("\n");
		
		
	}
	/**
	 * groupBy
	 * 按性别分类，计算年龄总和
	 */
	@SuppressWarnings("unchecked")
	public static void getManUserAgeSum(){
		System.out.println("不同性别的年龄总和:");
		List<User> list = ListUtil.getUserList();
		
		list.forEach(user -> System.out.println(user));
		
		Map map = list.stream().collect(Collectors.groupingBy(User::getSex,Collectors.reducing(0,User::getAge,Integer::sum)));
		map.forEach((k,v)->System.out.println("k: "+ k + "  v: "+v));
	}
	public static void groupBySexSum(){
		System.out.println("不同性别的人数总和:");
		List<User> list = ListUtil.getUserList();
		list.forEach(user -> System.out.println(user));
		Map<String,Long> map1 = list.stream().collect(Collectors.groupingBy(u -> u.getSex()+"",Collectors.counting()));
		Map<String,Integer> map2 = list.stream().collect(Collectors.groupingBy(u -> u.getSex()+"",Collectors.summingInt(user->1)));
		map1.forEach((k,v)->System.out.println("k: "+ k + "  v: "+v));
		map2.forEach((k,v)->System.out.println("k: "+ k + "  v: "+v));
		
	}
	/**
	 * map ruduce
	 * 男用户的年龄总和
	 */
	public static void sumManAge(){
		List<User> list = ListUtil.getUserList();
		int sum = list.stream().
					filter(user -> user.getSex() == 1).
					mapToInt(User::getAge).reduce((s,i)->s+i).getAsInt();
		
		System.out.println("男用户的年龄总和是：" + sum);
	}
	/**
	 * 按年龄排序
	 * sort
	 */
	public static void sortByAge(){
		System.out.println("按年龄升序排序");
		List<User> list = ListUtil.getUserList();
		
		list.sort((User u1,User u2)->u1.getAge() - u2.getAge());
		list.forEach(user->System.out.println(user));
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		//获取每个用户的年龄，生成新数组
		getAllUserAge();
		//男用户的年龄总和
		sumManAge();
		//按性别分类，计算年龄总和
		getManUserAgeSum();
		//按性别分类，计算男女的人数
		groupBySexSum();
		//按年龄进行排序
		sortByAge();
		
		
		
	}
}
