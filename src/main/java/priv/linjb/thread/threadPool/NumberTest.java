package priv.linjb.thread.threadPool;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
/**
 * 分批次处理任务的练习
 * 计算1到10000的总和
 * 将1加到10000的任务分成10个子任务，一子任务处理1加到1000，二子任务处理1001加到2000，三子任务处理2001加到3000，以此类推
 * 
 * @author Admin
 *
 */
public class NumberTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
		
		
		long t = 10000;
		int fen = 10;
		Sum sum = new Sum();
		CountDownLatch countDownLatch = new CountDownLatch(fen);
		
		WorkThreadJonPoolControl.createNewJobThreadPool("number", "addThread", fen);
		
		for(long i=0;i<fen;i++){
			long end = t / fen;
			long begin = end * i + 1;
			end = end * (i+1);
			WorkThreadJonPoolControl.execute_job("number",new AddThread(sum, begin, end, countDownLatch));
		
			System.out.print("begin:"+ begin);
			System.out.println("\tend:"+ end);
		}
		
		countDownLatch.await();
		
		WorkThreadJonPoolControl.stopAll("number");
		Thread.sleep(500);
		System.out.println("总计："+sum.getSum());

	}
}
