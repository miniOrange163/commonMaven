package priv.linjb.thread.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Linjb on 2018/7/21.
 * 一个测试线程死锁的示例
 */
public class DeadLockSample extends Thread {

    public static String obj1 = "obj1";
    public static String obj2 = "obj2";

    public static void main(String[] args) throws InterruptedException {
        // 进行死锁扫描的案例
        ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
        Runnable dlCheck = new Runnable() {
            @Override
            public void run() {
                long[] threadIds = mxBean.findDeadlockedThreads();
                if (threadIds != null) {
                    ThreadInfo[] threadInfos = mxBean.getThreadInfo(threadIds);
                    System.out.println("Detected deadlock threads:");
                    for (ThreadInfo threadInfo : threadInfos) {
                        System.out.println(threadInfo.getThreadName());
                    }
                }
            }
        };
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //稍等5秒，然后每10秒进行一次死锁扫描
        scheduler.scheduleAtFixedRate(dlCheck, 5l, 10l, TimeUnit.SECONDS);

        Thread a = new Thread(new Lock1());
        Thread b = new Thread(new Lock2());
        a.setName("Thread1");
        b.setName("Thread2");
        a.start();
        b.start();


    }

}

class Lock1 implements Runnable{
    @Override
    public void run(){
        try{
            System.out.println("Lock1 running");
            while(true){
                synchronized(DeadLockSample.obj1){
                    System.out.println("Lock1 lock obj1");
                    Thread.sleep(3000);//获取obj1后先等一会儿，让Lock2有足够的时间锁住obj2
                    synchronized(DeadLockSample.obj2){
                        System.out.println("Lock1 lock obj2");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
class Lock2 implements Runnable{
    @Override
    public void run(){
        try{
            System.out.println("Lock2 running");
            while(true){
                synchronized(DeadLockSample.obj2){
                    System.out.println("Lock2 lock obj2");
                    Thread.sleep(3000);
                    synchronized(DeadLockSample.obj1){
                        System.out.println("Lock2 lock obj1");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

/**
 * 死锁后通过命令行jstack得到的堆栈信息：
 */
/*
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.121-b13 mixed mode):

        "DestroyJavaVM" #13 prio=5 os_prio=0 tid=0x000000000251a000 nid=0x2268 waiting o
        n condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Thread2" #12 prio=5 os_prio=0 tid=0x000000005a323000 nid=0x10c8 waiting for mon
        itor entry [0x000000005b04e000]
        java.lang.Thread.State: BLOCKED (on object monitor)
        at priv.linjb.thread.deadlock.Lock2.run(DeadLockSample.java:75)
        - waiting to lock <0x00000000d5b8aa80> (a java.lang.String)
        - locked <0x00000000d5b8aab0> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:745)

        "Thread1" #11 prio=5 os_prio=0 tid=0x000000005a2a6800 nid=0x1e54 waiting for mon
        itor entry [0x000000005af1f000]
        java.lang.Thread.State: BLOCKED (on object monitor)
        at priv.linjb.thread.deadlock.Lock1.run(DeadLockSample.java:56)
        - waiting to lock <0x00000000d5b8aab0> (a java.lang.String)
        - locked <0x00000000d5b8aa80> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:745)

        "Service Thread" #10 daemon prio=9 os_prio=0 tid=0x000000005a238800 nid=0x2114 r
        unnable [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "C1 CompilerThread2" #9 daemon prio=9 os_prio=2 tid=0x0000000059838800 nid=0x1fb
        8 waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "C2 CompilerThread1" #8 daemon prio=9 os_prio=2 tid=0x0000000059835800 nid=0x22d
        0 waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "C2 CompilerThread0" #7 daemon prio=9 os_prio=2 tid=0x0000000059824000 nid=0x1f4
        8 waiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Monitor Ctrl-Break" #6 daemon prio=5 os_prio=0 tid=0x00000000597e2000 nid=0x214
        8 runnable [0x0000000059e7f000]
        java.lang.Thread.State: RUNNABLE
        at java.net.SocketInputStream.socketRead0(Native Method)
        at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
        at java.net.SocketInputStream.read(SocketInputStream.java:171)
        at java.net.SocketInputStream.read(SocketInputStream.java:141)
        at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
        at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
        at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
        - locked <0x00000000d5c335c8> (a java.io.InputStreamReader)
        at java.io.InputStreamReader.read(InputStreamReader.java:184)
        at java.io.BufferedReader.fill(BufferedReader.java:161)
        at java.io.BufferedReader.readLine(BufferedReader.java:324)
        - locked <0x00000000d5c335c8> (a java.io.InputStreamReader)
        at java.io.BufferedReader.readLine(BufferedReader.java:389)
        at com.intellij.rt.execution.application.AppMainV2$1.run(AppMainV2.java:
        64)

        "Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000005780f000 nid=0x1794 w
        aiting on condition [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x0000000057805800 nid=0x1dc8
        runnable [0x0000000000000000]
        java.lang.Thread.State: RUNNABLE

        "Finalizer" #3 daemon prio=8 os_prio=1 tid=0x00000000577ee800 nid=0xbe8 in Objec
        t.wait() [0x00000000594df000]
        java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d5808ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
        - locked <0x00000000d5808ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)

        "Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x00000000577a5000 nid=0x222
        c in Object.wait() [0x000000005932e000]
        java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x00000000d5806b68> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x00000000d5806b68> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)

        "VM Thread" os_prio=2 tid=0x000000005779d800 nid=0x1ad4 runnable

        "GC task thread#0 (ParallelGC)" os_prio=0 tid=0x000000000252f800 nid=0x1b94 runn
        able

        "GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000002531000 nid=0x11a8 runn
        able

        "GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002532800 nid=0x2354 runn
        able

        "GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002534000 nid=0x11b4 runn
        able

        "VM Periodic Task Thread" os_prio=2 tid=0x000000005a2a3000 nid=0x1fcc waiting on
        condition

        JNI global references: 33


        Found one Java-level deadlock:
        =============================
        "Thread2":
        waiting to lock monitor 0x00000000577a95e8 (object 0x00000000d5b8aa80, a java.
        lang.String),
        which is held by "Thread1"
        "Thread1":
        waiting to lock monitor 0x00000000577abe78 (object 0x00000000d5b8aab0, a java.
        lang.String),
        which is held by "Thread2"

        Java stack information for the threads listed above:
        ===================================================
        "Thread2":
        at priv.linjb.thread.deadlock.Lock2.run(DeadLockSample.java:75)
        - waiting to lock <0x00000000d5b8aa80> (a java.lang.String)
        - locked <0x00000000d5b8aab0> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:745)
        "Thread1":
        at priv.linjb.thread.deadlock.Lock1.run(DeadLockSample.java:56)
        - waiting to lock <0x00000000d5b8aab0> (a java.lang.String)
        - locked <0x00000000d5b8aa80> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:745)

        Found 1 deadlock.*/
