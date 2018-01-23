package priv.linjb.thread.threadPool;


import java.util.HashMap;
import java.util.Map;
/**
 * 1.以键值对的方式，生成线程池(包含指定数量的线程)并存入内存中
 * 2.createNewJobThreadPool用于新建线程池
 * 3.execute_job用于向指定线程池存入线程，并执行
 * @author Admin
 *
 */
public class WorkThreadJonPoolControl {
	
	private static Map<String, WorkThreadJonPool> THREADPOOLS=new HashMap<String, WorkThreadJonPool>();
	/**
	 * 生成新的线程池,并存入线程池map
	 * @param thread_poolkey	键
	 * @param thread_poolname	线程池名称
	 * @param threadCount		线程池中的线程数量
	 */
	public static void createNewJobThreadPool(String thread_poolkey,String thread_poolname,Integer threadCount){
		if(!THREADPOOLS.containsKey(thread_poolkey)){
			THREADPOOLS.put(thread_poolkey, new WorkThreadJonPool(threadCount,thread_poolname));
		}
	}
	/**
	 * 1.向指定键的线程池传入任务线程
	 * 2.放入线程池中的阻塞式队列中
	 * 3.由线程池中的线程轮洵队列中的任务线程，并执行
	 * @param thread_poolkey	键
	 * @param job				任务线程
	 * @return
	 */
	public static boolean execute_job(String thread_poolkey,Runnable job){
		if(THREADPOOLS.containsKey(thread_poolkey)){
			THREADPOOLS.get(thread_poolkey).execute(job);
		}
		return false;
	}
	public static void stopAll(String thread_poolkey){
		if(THREADPOOLS.containsKey(thread_poolkey)){
			THREADPOOLS.get(thread_poolkey).stopAll();
		}
	}
	public static int getExcuteThread(String thread_poolkey){
		if(THREADPOOLS.containsKey(thread_poolkey)){
			return THREADPOOLS.get(thread_poolkey).getExcuteThread();
		}
		return 0;
	}

}