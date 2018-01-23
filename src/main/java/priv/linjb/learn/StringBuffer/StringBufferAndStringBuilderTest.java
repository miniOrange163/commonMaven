package priv.linjb.learn.StringBuffer;

import java.util.concurrent.CountDownLatch;
/**
 * 测试StringBuffer 和 StringBuilder线程安全性的示范例子
 * @author max
 *
 */
public class StringBufferAndStringBuilderTest {

	 private static final int THREAD_NUM = 3;  
	  
	    public static void main(String[] args){  
	        long startTime = System.currentTimeMillis();  
	        String strToReverse = "AAAABBBB";  
	  
	        StringBuffer stringBuffer = new StringBuffer(strToReverse);  
	        StringBuilder stringBuilder = new StringBuilder(strToReverse);
	        //CountDownLatch是线程同步辅助类，通过设置计数来控制线程的运行次数
	        CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);  
	        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUM);  
	        //生成THREAD_NUM*2个线程
	        for(int i=0; i<THREAD_NUM; i++) {  
	            new StringBufferTaskThread(stringBuilder, countDownLatch).start();  
	            new StringBufferTaskThread(stringBuffer, countDownLatch2).start();  
	        
	        }  
	  
	        try {  
	        	//countDownLatch和countDownLatch2的计数为0之前，等待
	            countDownLatch.await();  
	            countDownLatch2.await();  
	            //countDownLatch和countDownLatch2的计数为0之前，下面的程序不会被执行
	            System.out.println("StringBuffer toString: " + stringBuffer.toString());  
	            System.out.println("StringBuilder toString: " + stringBuilder.toString());  
	            long endTime = System.currentTimeMillis();  
	            System.out.println("Running time: " + (endTime-startTime));  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
}
