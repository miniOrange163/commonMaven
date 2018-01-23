package priv.linjb.thread.callback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import priv.linjb.thread.threadPool.WorkThreadJonPoolControl;

public class callBackTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		long start = new Date().getTime();
		test1();
		
		long end = new Date().getTime();
		double d = new BigDecimal(end - start).divide(new BigDecimal(1000)).doubleValue();
		
		System.out.println("消耗时间: " + d + "秒");
	}
	
	public static void test1() throws InterruptedException{
		
		List list = new ArrayList();
		
		int num = 100;
		CountDownLatch countDownLatch = new CountDownLatch(num);
		
		WorkThreadJonPoolControl.createNewJobThreadPool("number", "Thread", 100);
		for(int i=0;i<num;i++){
			
			WorkThreadJonPoolControl.execute_job("number", new ListThread(i, list, countDownLatch));
			//new Thread(new ListThread(i, list, countDownLatch)).start(); 
		}
		
		countDownLatch.await();
		
		list.stream().forEach(l->System.out.println("\t"+l));
		System.out.println("size:" + list.size());
	}
	
	public static void test2() throws InterruptedException{
		


		HandleList handle = new HandleListImpl(new ArrayList());
		
		int num = 100;
		CountDownLatch countDownLatch = new CountDownLatch(num);
		
		WorkThreadJonPoolControl.createNewJobThreadPool("number", "Thread", 10);
		for(int i=0;i<num;i++){
			
			WorkThreadJonPoolControl.execute_job("number", new ListThreadCallBack(i, handle, countDownLatch));
			//new Thread(new ListThreadCallBack(i, handle, countDownLatch)).start(); 
			
		}
		
		countDownLatch.await();
		
		handle.getList().stream().forEach(l->System.out.println("\t"+l));
		System.out.println("size:" + handle.getList().size());
	}

}
