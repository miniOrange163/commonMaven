package priv.linjb.learn.Collection;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

	public static void main(String[] args) {
		
		List list = null;
		
		if(list != null || !list.isEmpty()){
			System.out.println(123);//会报错
		}
	}
}
