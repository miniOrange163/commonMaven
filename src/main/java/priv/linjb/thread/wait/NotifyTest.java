package priv.linjb.thread.wait;
/**
 * 案例一：wait和notify的测试
 * 
 * 1.一类线程用于唤醒所有其他线程
 * 2.一类线程用于打印操作
 * 3.在本例中，生成了1个唤醒线程，3个等待线程
 * 4.通过锁定同一个flag对象来达到线程等待和唤醒的目的
 * 5.flag之所以选用数组是因为用字符串类型进行的赋值的话，会改变flag的引用，导致线程对flag没有控制权
 * 
 * 网址:http://longdick.iteye.com/blog/453615
 * @author Admin
 *
 */
public class NotifyTest {  
    private String flag[] = { "true" };  
  
    class NotifyThread extends Thread {  
        public NotifyThread(String name) {  
            super(name);  
        }  
  
        public void run() {  
            try {  
                sleep(3000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            synchronized (flag) {  
                flag[0] = "false";  
                flag.notifyAll();
            }  
        }  
    };  
  
    class WaitThread extends Thread {  
        public WaitThread(String name) {  
            super(name);  
        }  
  
        public void run() {  
            synchronized (flag) {  
                while (flag[0] != "false") {  
                    System.out.println(getName() + " begin waiting!");  
                    long waitTime = System.currentTimeMillis();  
                    try {  
                        flag.wait();  
  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                    waitTime = System.currentTimeMillis() - waitTime;  
                    System.out.println("wait time :" + waitTime);  
                }  
                System.out.println(getName() + " end waiting!");  
            }  
        }  
    }  
  
    public static void main(String[] args) throws InterruptedException {  
        System.out.println("Main Thread Run!");  
        NotifyTest test = new NotifyTest();  
        NotifyThread notifyThread = test.new NotifyThread("notify01");  
        WaitThread waitThread01 = test.new WaitThread("waiter01");  
        WaitThread waitThread02 = test.new WaitThread("waiter02");  
        WaitThread waitThread03 = test.new WaitThread("waiter03");  
        notifyThread.start();  
        waitThread01.start();  
        waitThread02.start();  
        waitThread03.start();  
    }  
  
}  