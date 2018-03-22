package priv.linjb.learn.test;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyTest {

	public static void main(String[] args) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH,13);
		cal.set(Calendar.DAY_OF_MONTH, 31);

		System.out.println(cal.get(Calendar.DAY_OF_YEAR));
		
	}
	@Test
	public void test() throws ParseException {

		String str = "{\"happenTime\":\"${happenTime}\",}";
		System.out.println(str);

	}
}
