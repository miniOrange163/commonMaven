package priv.linjb.thread.threadPool;

import java.util.concurrent.CountDownLatch;

public class prA implements Runnable{

	StringBuilder builder;
	StringBuffer buffer;
	private CountDownLatch countDownLatch;
	
	prA(StringBuilder builder,StringBuffer buffer,CountDownLatch countDownLatch){
		this.builder = builder;
		this.buffer = buffer;
		this.countDownLatch = countDownLatch;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0 ;i<5;i++){
			builder.reverse();
			buffer.reverse();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}  
		}
		countDownLatch.countDown();
		
	}

}
