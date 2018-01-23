package priv.linjb.learn.lambda;

import java.util.List;

public class FourTest {
	
	public static void findFirst(){
		
		List<User> list = ListUtil.getUserList();
		
		User user = list.stream().filter(u->u!=null).findFirst().get();
	}

	public static void main(String[] args) {
		
		findFirst();
	}
}
