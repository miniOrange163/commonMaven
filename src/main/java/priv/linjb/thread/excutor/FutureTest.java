package priv.linjb.thread.excutor;



import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: 林嘉宝
 * @Date: 2018/2/12
 * @name:
 * @Description: 测试Future
 */
public class FutureTest {



    /**
     * Future的测试用例,异步线程分发，得到结果进行汇总
     */
    @Test
    public void future(){
        int threadNum = 10;

        ExecutorService executorService = new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        List<Future<String>> futureTaskList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Callable<String> callable = new Callable<String>() {
                @Override
                public String call() throws Exception {

                    double d = Math.random();
                    return d+"";
                }
            };
            Future<String> submit = executorService.submit(callable);
            futureTaskList.add(submit);

        }

        executorService.shutdown();

        for (Future<String> future : futureTaskList) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
