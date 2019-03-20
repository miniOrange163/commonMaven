package priv.linjb.learn.date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: 林嘉宝
 *  
 * @Date: 2019/3/16
 *  
 * @name: 
 *
 * @Description: 
 */
public class TestLocalDateTime {


        public static DateTimeFormatter formatYYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter formatYYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 日期转String
     */
    @Test
    public void DateToString(){
        LocalDateTime now = LocalDateTime.now();

        System.out.println("yyyy-MM-dd HH:mm:ss---->" + now.format(formatYYYYMMDDHHMMSS));
        System.out.println("yyyy-MM-dd HH:mm:ss.SSS---->" + now.format(formatYYYYMMDDHHMMSSSSS));

    }

    /**
     * String转日期
     */
    @Test
    public void StringToDate(){

        String strDate1 = "2019-03-16 14:00:00";
        LocalDateTime time = LocalDateTime.parse(strDate1,formatYYYYMMDDHHMMSS);
        System.out.println(time);

    }

    @Test
    public void DateToLocalDateTime(){

        // LocalDateTime 转 Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());

        System.out.println(sdf.format(date));

        // Date 转 LocalDateTime

        Date date2 = new Date();
        Instant instant = date2.toInstant();
        ZoneId zoneId2 = ZoneId.systemDefault();

        LocalDateTime localDateTime2 = instant.atZone(zoneId).toLocalDateTime();

        System.out.println(localDateTime2.format(formatYYYYMMDDHHMMSS));

    }

    /**
     * 获取当前毫秒值
     */
    @Test
    public void getTimeInMillis(){

        //java1.7
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(System.currentTimeMillis());

        //java1.8
        System.out.println(Clock.systemDefaultZone().millis());
        //使用localDateTime获取当前毫秒值
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * 日期的修改
     */
    @Test
    public void dateChange(){

        String timeString = "2019-03-16 15:10:30";
        LocalDateTime time = LocalDateTime.parse(timeString, formatYYYYMMDDHHMMSS);
        // 获取当天的起始时间 00:00:00
        LocalDateTime begin = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 0, 0, 0);
        System.out.println("当天的起始时间 : "+begin.format(formatYYYYMMDDHHMMSS));

        // 获取当前的最后时间 23:59:59
        LocalDateTime end = LocalDateTime.of(time.getYear(), time.getMonth(), time.getDayOfMonth(), 23, 59, 59);
        System.out.println("当前的最后时间 : " + end.format(formatYYYYMMDDHHMMSS));

    }

    /**
     * 获取年月日时分秒
     * @throws ParseException
     */
    @Test
    public void getYearMonthDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeString = "2019-03-16 15:10:30";
        System.out.println("Calendar方式 ：");
        //java1.7
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(timeString));
        //年
        System.out.println("年: " +calendar.get(Calendar.YEAR));
        //月 0-11
        System.out.println("月: " + (calendar.get(Calendar.MONTH) + 1));
        //日(以年计算)1-365
        System.out.println("日-当年的第几天: " + calendar.get(Calendar.DAY_OF_YEAR));
        //日(以月计算)1-30
        System.out.println("日-当月的第几天: " + calendar.get(Calendar.DATE));
        //时 0-12
        System.out.println("时-12时制: " +calendar.get(Calendar.HOUR));
        //时 0-24
        System.out.println("时-24时制: " +calendar.get(Calendar.HOUR_OF_DAY));
        //分
        System.out.println("分: " +calendar.get(Calendar.MINUTE));
        //秒
        System.out.println("秒: " +calendar.get(Calendar.SECOND));
        System.out.println("LocalDateTime方式 ：");


        //java 1.8
        LocalDateTime time = LocalDateTime.parse(timeString,formatYYYYMMDDHHMMSS);
        //年
        System.out.println("年: " + time.getYear());
        //月(英文)1-12
        System.out.println("月-英文: " + time.getMonth());
        //月(数值)1-12
        System.out.println("月-数值: " + time.getMonthValue());
        //日(以年计算)1-365
        System.out.println("日-当年的第几天: " + time.getDayOfYear());
        //日(以月计算)1-30
        System.out.println("日-当月的第几天: " + time.getDayOfMonth());
        //时
        System.out.println("时: " + time.getHour());
        //分
        System.out.println("分: " + time.getMinute());
        //秒
        System.out.println("秒: " + time.getSecond());
    }


}
