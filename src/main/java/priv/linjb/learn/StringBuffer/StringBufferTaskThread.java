package priv.linjb.learn.StringBuffer;

import java.util.concurrent.CountDownLatch;
/**
 * 测试线程类
 * @author max
 *
 */
public class StringBufferTaskThread extends Thread{

	private static final String STARTER = "-start";  
    private static final String ENDER = "-end";  
  
    private Object s = null;  
    private CountDownLatch countDownLatch;  // 记载运行线程数  
  
    public StringBufferTaskThread(StringBuilder stringBuilder, CountDownLatch countDownLatch) {  
        super();  
        this.s = stringBuilder;  
        this.countDownLatch = countDownLatch;  
    }  
  
    public StringBufferTaskThread(StringBuffer stringBuffer, CountDownLatch countDownLatch) {  
        super();  
        this.s = stringBuffer;  
        this.countDownLatch = countDownLatch;  
    }  
  
    @Override  
    public void run() {  
        System.out.println(Thread.currentThread().getName() + STARTER);
        //循环10次，每次对字符串进行反转操作
        for(int i=0; i<10; i++) {  
            try {  
                Thread.sleep(200);  
                if(s instanceof StringBuffer){  
                    ((StringBuffer) s).reverse();  
                    System.out.println("Buffer->"+s.toString());  
                }else if(s instanceof StringBuilder){  
                    ((StringBuilder) s).reverse();  
                    System.out.println("Builder->"+s.toString());  
                }  
                Thread.sleep(200);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println(Thread.currentThread().getName() + ENDER);  
        /**
         * 每一次循环后将countDownLatch的计数减1，重复run()方法，直至为0
         * 线程数是2*THREAD_NUM，每个线程执行10次反转
         * 所以反转的执行次数是2*THREAD_NUM*10
         */
        countDownLatch.countDown();  
    }  
}
