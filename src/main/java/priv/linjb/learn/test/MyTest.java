package priv.linjb.learn.test;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import priv.linjb.learn.test.entity.Person;
import priv.linjb.learn.test.entity.Student;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest {



	public static void main(String[] args) throws ParseException {

		SimpleDateFormat sdf_Day = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf_Month = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat sdf_Year = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf_Month.parse("201805"));
		cal.add(Calendar.MONTH,1);
		cal.add(Calendar.DAY_OF_YEAR,-1);

		System.out.println(sdf_Day.format(cal.getTime()));

	}
	@Test
	public void test() throws ParseException {


	}

	public String moneyToCapital(long sum){

		Map map = new HashedMap();
		map.put(0, "零");
		map.put(1, "壹");
		map.put(2, "贰");
		map.put(3, "叁");
		map.put(4, "肆");
		map.put(5, "伍");
		map.put(6, "陆");
		map.put(7, "柒");
		map.put(8, "捌");
		map.put(9, "玖");

		Map bitMap = new HashedMap();
		bitMap.put(2, "拾");
		bitMap.put(3, "佰");
		bitMap.put(4, "仟");
		bitMap.put(5, "万");

		StringBuffer sb = new StringBuffer();
		String sumString = String.valueOf(sum);
		int length = sumString.length();
		int base = 1;
		for (int i = 0; i < length - 1; i++) {
			base = base * 10;
		}

		for (int i = 0; i < length; i++) {
			if (i > 0) {
				if (sumString.substring(i - 1, i).equals("0")) {
					continue;
				}
			}
			int t = Integer.parseInt(sumString.substring(i,i+1));
			int bit = length - i;

			if (t == 0) {
				String end = sumString.substring(i);
				Pattern pattern = Pattern.compile("^0{0,}0$");
				Matcher matcher = pattern.matcher(end);
				if (matcher.matches()) {
					if (bit > 5) {
						sb.append("万");
					}
					continue;
				}
			}
			sb.append(map.get(t));
			if (bit > 5) {
				bit = bit - 4;
			}
			sb.append(bitMap.get(bit));
		}

		sb.append("元整");

		return sb.toString();
	}
}
