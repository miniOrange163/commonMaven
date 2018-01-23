package priv.linjb.thread.threadPool;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
/**
 * 用线程池测试StringBuilder和StringBuffer类的线程安全情况
 * @author Admin
 *
 */
public class StringTest {

	public static void main(String[] args) {
		//线程不安全的
		StringBuilder builder = new StringBuilder("AAAAABBBBB");
		//线程安全的
		StringBuffer buffer = new StringBuffer("AAAAABBBBB");
		CountDownLatch countDownLatch = new CountDownLatch(6);
		
		WorkThreadJonPoolControl.createNewJobThreadPool("string", "chane", 6);
		
		WorkThreadJonPoolControl.execute_job("string", new prA(builder,buffer,countDownLatch));
		WorkThreadJonPoolControl.execute_job("string", new prA(builder,buffer,countDownLatch));
		WorkThreadJonPoolControl.execute_job("string", new prA(builder,buffer,countDownLatch));
		WorkThreadJonPoolControl.execute_job("string", new prA(builder,buffer,countDownLatch));
		WorkThreadJonPoolControl.execute_job("string", new prA(builder,buffer,countDownLatch));
		WorkThreadJonPoolControl.execute_job("string", new prA(builder,buffer,countDownLatch));
		
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WorkThreadJonPoolControl.stopAll("string");
		System.out.println("builder: "+ builder);
		System.out.println("buffer: "+ buffer);
	}
}
