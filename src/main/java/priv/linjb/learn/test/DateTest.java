package priv.linjb.learn.test;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2018/7/2
 *  
 * @name: 
 *
 * @Description: 
 */
public class DateTest {

    /**
     * 目的：得到一个月的最后一天
     * 方式：初始化一个月份，加一个月再减一天
     * @throws ParseException
     */
    @Test
    public void date1() throws ParseException {

        SimpleDateFormat sdf_Day = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf_Month = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat sdf_Year = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf_Month.parse("201805"));
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DAY_OF_YEAR,-1);

        //结果：20180531
        System.out.println(sdf_Day.format(cal.getTime()));

    }
}
