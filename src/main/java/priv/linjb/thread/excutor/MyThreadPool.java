package priv.linjb.thread.excutor;

import java.util.concurrent.*;

/**
 * @author: 林嘉宝
 * @Date: 2018/2/12
 * @name:
 * @Description:
 */
public class MyThreadPool {

    private String poolName;
    private ExecutorService executorService;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;


    private MyThreadPool(){

    }

    public MyThreadPool(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.poolName = poolName;

        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

    }

    private Future submit(Runnable runnable) {
        return executorService.submit(runnable);
    }
    private void shutdown(){
        executorService.shutdown();
    }


}
