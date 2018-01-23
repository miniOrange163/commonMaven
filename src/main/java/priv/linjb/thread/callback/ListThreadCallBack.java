package priv.linjb.thread.callback;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ListThreadCallBack implements Runnable{

	private int num;
	private HandleList handList;
	private CountDownLatch countDownLatch;
	
	
	


	public ListThreadCallBack(int num, HandleList handList, CountDownLatch countDownLatch) {
		super();
		this.num = num;
		this.handList = handList;
		this.countDownLatch = countDownLatch;
	}





	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		handList.handle(num + "");
		
		countDownLatch.countDown();
		
	}

}
