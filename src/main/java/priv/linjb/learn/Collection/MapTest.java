package priv.linjb.learn.Collection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class MapTest {

	public static void main(String[] args) {
		Map map = new HashMap<>();
		Map map2 = new LinkedHashMap<>();
		Map map3 = new TreeMap<>();
		Set set = new HashSet<>();
		
		map.put("1", new Object());
		map.put("2", new Object());
		
		int a = 16;
		for(int i = 0;i<1000;i++){

			System.out.println("a & i:"+ (i &(a-1)  ));
			System.out.println("a % i:"+ (i % a));
			
		}
		
	}
}
