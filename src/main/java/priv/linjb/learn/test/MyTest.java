package priv.linjb.learn.test;

import org.junit.Test;
import priv.linjb.common.util.file.FileOperationUtil;
import priv.linjb.learn.test.entity.Person;
import priv.linjb.learn.test.entity.Student;
import priv.linjb.learn.test.entity.ThreadLocalTest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest {


	/**
	 * 测试定时器的使用
	 */
	@Test
	public void timer(){
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {

				System.out.println("test: " + sdf.format(new Date()));

			}
		}, 0,2000);// 设定指定的时间time,此处为2000毫秒
		try {
			Thread.sleep(6000);
			timer.cancel();
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 结论：不同线程对被ThreadLocal包装的静态变量进行修改操作，互不影响
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void testThreadLocal() throws InterruptedException {

		new Thread(()->{
				ThreadLocalTest.local.set(20);
				ThreadLocalTest.i = 20;
				System.out.println(Thread.currentThread().getName() +",local:"+ ThreadLocalTest.local.get() + "i:" + ThreadLocalTest.i);
		}).start();

		new Thread(()->{
			ThreadLocalTest.local.set(30);
			ThreadLocalTest.i = 30;
			System.out.println(Thread.currentThread().getName() +",local:"+ ThreadLocalTest.local.get() + "i:" + ThreadLocalTest.i);

		}).start();


		ThreadLocalTest.local.set(40);
		ThreadLocalTest.i = 40;
		System.out.println(Thread.currentThread().getName() +",local:"+ ThreadLocalTest.local.get() + "i:" + ThreadLocalTest.i);

	}
}
