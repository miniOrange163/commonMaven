package priv.linjb.thread.threadPool;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

public class AddThread implements Runnable{

	private Sum sum = null;
	private long begin;
	private long end;
	private CountDownLatch countDownLatch;
	
	AddThread(Sum sum,long begin,long end,CountDownLatch countDownLatch){
		this.sum = sum;
		this.begin = begin;
		this.end = end;
		this.countDownLatch = countDownLatch;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long littleSum = 0;
		for(long i=begin;i<=end;i++){
			littleSum += i;
		}
		synchronized (sum) {
			sum.setSum(sum.getSum() + littleSum);
		}
		System.out.println("分任务的计算结果 ："+littleSum);
		countDownLatch.countDown();
	}
	
}
