package priv.linjb.thread.threadPool;


import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
/**
 * 线程池的模拟
 * @author Admin
 *
 */
public class WorkThreadJonPool{
	private static Logger logger = Logger.getLogger(WorkThreadJonPool.class);
	//线程池中线程数量
	private final int threadcount;
	//阻塞式队列，用于保存业务线程
	private final LinkedBlockingQueue<Runnable> jobqueue;
	//线程池的具体实现
	private final JobWorkerTread[] jobWorkers;		
	//线程池的名称
	private String poolname;
	//volatile关键字的目的是使excute同步
	private volatile boolean excute; 
	
	public WorkThreadJonPool(int threadcount,String poolname){
		this.threadcount=threadcount;
		this.poolname=poolname;
		this.excute = true;
		//新建队列
		this.jobqueue=  new LinkedBlockingQueue<Runnable>();
		//新建线程池数组
		jobWorkers=new JobWorkerTread[this.threadcount];
		
		for(int i=0;i<threadcount;i++){
			//新建线程池数组中的线程
			jobWorkers[i]=new JobWorkerTread();
			//运行线程
			jobWorkers[i].start();
		}
	}
	public void execute(Runnable r){
			//向队列中添加业务线程，并唤醒线程池中等待的线程
			jobqueue.add(r);
	 }
	
	//内部类
	private  class JobWorkerTread extends Thread{
		public void run(){
			Runnable r;
			
			while(excute){
				try {
					
					logger.info("线程池_"+poolname+"_任务数量:"+jobqueue.size());
					/**
					 * 从队列中取线程
					 * 当队列为空时，队列内部实现会将线程状态转化为等待
					 */
					r=jobqueue.take();
					logger.info("线程池_"+poolname+"执行任务线程ID："+Thread.currentThread().getId());
					r.run();
					logger.info("线程池_"+poolname+"_执行任务完成");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return ;
				}
				
			}
			
			
		}
	}
	public void stopAll(){
		this.excute = false;
		for(int i=0;i<threadcount;i++){
		
			
			if (jobWorkers[i] != null) {  
				//jobWorkers[i].stop(); //外围调用关闭  
				jobWorkers[i].interrupt();
			}  
		}
	}
	public int getExcuteThread(){
		int num = 0 ;
		for(int i=0;i<threadcount;i++){
			if (jobWorkers[i] != null) {  
				
				if(jobWorkers[i].isInterrupted()){
					num++;
				}
				
			}  
		}
		return num;
	}

}
