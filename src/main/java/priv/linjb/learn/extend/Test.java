package priv.linjb.learn.extend;

import javax.xml.ws.Service;

import priv.linjb.learn.extend.impl.DayStatisticsServiceImpl;
import priv.linjb.learn.extend.impl.SecondDayStatisticsServiceImpl;
/**
 * 本例的目的：
 * 1.验证当父类某个接口方法名与子类的接口方法名相同时，JAVA程序运行的逻辑。
 * 2.验证静态块、静态方法、构造方法的执行顺序
 * @author Admin
 *
 */
public class Test {

	
	public static void main(String[] args) {
		
		DayStatisticsService service = new DayStatisticsServiceImpl();
		DayStatisticsService secondService = new SecondDayStatisticsServiceImpl();
		
		System.out.println("\n执行DayStatisticsServiceImpl类statisticsByAll方法:");
		service.statisticsByAll();
		System.out.println("\n执行SecondDayStatisticsServiceImpl类statisticsByAll方法:");
		secondService.statisticsByAll();
		
		Short s = null;
		switch(s){
		case 0:System.out.println(1);break;
		
		}

	}
}
