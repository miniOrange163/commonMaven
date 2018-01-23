package priv.linjb.learn.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * CountDownLatch同步辅助类的应用例子
 * @author max
 *
 */
public class CountDownLatchTest {
	// 模拟了100米赛跑，10名选手已经准备就绪，只等裁判一声令下。当所有人都到达终点时，比赛结束。
    public static void main(String[] args) throws InterruptedException {

        // 开始的倒数锁 
        final CountDownLatch begin = new CountDownLatch(1);  

        // 结束的倒数锁 
        final CountDownLatch end = new CountDownLatch(10);  

        // 十名选手 
        final ExecutorService exec = Executors.newFixedThreadPool(10);  

        for (int index = 0; index < 10; index++) {
            final int NO = index + 1;  
            Runnable run = new Runnable() {
                public void run() {  
                    try {  
                    	//此时begin == 1,大于0,线程进入等待状态
                        begin.await();
                        Thread.sleep((long) (Math.random() * 1000));  
                        System.out.println("No." + NO + " arrived");  
                    } catch (InterruptedException e) {  
                    } finally {  
                        // 每个选手到达终点时，end就减一
                        end.countDown();
                        //计算到达终点名次
                        long no = 10-end.getCount();
                        System.out.println("第" + no +"名");
                    }  
                }  
            };  
            exec.submit(run);
        }  
        System.out.println("Game Start");  
        // begin减1为0，等待的线程被释放，开始游戏
        begin.countDown();  
       // begin.countDown();  
        // 等待end变为0，即所有选手到达终点
        end.await();  
        //end为0前，以下语句不会被执行
        System.out.println("Game Over");  
        exec.shutdown();  
    }
}
