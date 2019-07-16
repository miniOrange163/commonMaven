package priv.linjb.learn.lambda;

import org.junit.Test;
import priv.linjb.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FourTest {
	
	public static void findFirst(){
		
		List<User> list = ListUtil.getUserList();
		
		User user = list.stream().filter(u->u!=null).findFirst().get();
	}
	@Test
	public void filter(){

		List<User> list = null;
		list =ListUtil.getUserList();

		//结论：即使经过filter过滤后没有任何元素，调用获取Integer的方法也不会报错
		int sum = list.stream()
				.filter(u->u!=null)
				.filter(u->"aa".equals(u.getName()))
				.mapToInt(u-> u.getAge())
				.sum();
		//结论：如何filter过滤后没有任何元素，则map中的表达式不会被执行
		List collect = list.stream()
				.filter(u -> u != null)
				.filter(u -> "aa".equals(u.getName()))
				.map(u -> {
					System.out.println("abc:" + u.getName());
					return u;
				}).collect(Collectors.toList());

		System.out.println(sum);
	}

	/**
	 * 字符串拼接
	 */
	@Test
	public void string(){
		List<User> userList = ListUtil.getUserList();

//		userList.stream().map(o->o.getName()).collect(new StringCollector())

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
