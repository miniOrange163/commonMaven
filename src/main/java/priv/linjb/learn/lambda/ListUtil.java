package priv.linjb.learn.lambda;

import priv.linjb.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	public static List<User> getUserList(){
		
		User u1 = new User("lilei",20,1);
		User u2 = new User("limei",31,1);
		User u3 = new User("min",12,0);
		User u4 = new User("max",33,0);
		User u5 = new User("test",35,0);
		User u6 = new User("test",40,0);
		
		List list = new ArrayList();
		list.add(null);
		list.add(u1);
		list.add(u2);
		list.add(u3);
		list.add(u4);
		list.add(u5);
		list.add(u6);
		list.add(null);
		return list;
	}
}
