package priv.linjb.thread.callback;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ListThread implements Runnable{

	private int num;
	private List list;
	private CountDownLatch countDownLatch;
	
	
	
	public ListThread(int num, List list, CountDownLatch countDownLatch) {
		super();
		this.num = num;
		this.list = list;
		this.countDownLatch = countDownLatch;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		synchronized (list) {
			list.add(num);
		}
		//list.add(num);
		
		countDownLatch.countDown();
	}
	
	

}
