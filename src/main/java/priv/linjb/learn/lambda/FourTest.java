package priv.linjb.learn.lambda;

import priv.linjb.entity.User;

import java.math.BigDecimal;
import java.util.List;

public class FourTest {
	
	public static void findFirst(){
		
		List<User> list = ListUtil.getUserList();
		
		User user = list.stream().filter(u->u!=null).findFirst().get();
	}

	public static void main(String[] args) {
		
		//findFirst();

		for (int i = 0; i < 5; i++) {
			final int  ii = i;
			Runnable r = new Runnable() {

				@Override
				public void run() {
					System.out.println("线程"+ii);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};

			new Thread(r).start();
		}

	}
}
