package priv.linjb.learn.extend.impl;

import priv.linjb.learn.extend.DayStatisticsService;

public class DayStatisticsServiceImpl extends CommonStatisticsServiceImpl implements DayStatisticsService{

	private static String staticT = init();
	
	public DayStatisticsServiceImpl(){
		System.out.println("3.类的构造方法运行");
	}
	static{
		System.out.println("2.类的static块运行");
	}
	public static void staticTest(){
		System.out.println("类的static方法执行");
	}
	public static String init(){
	
		System.out.println("1.类的static方法给静态属性赋值");
		return "static";
	}
	@Override
	public void statisticsByAll() {
		// TODO Auto-generated method stub
		super.statisticsByAll();
		System.out.println("DayStatisticsService statisticsByAll");
	}
	
}
