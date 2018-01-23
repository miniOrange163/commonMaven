package priv.linjb.thread.wait;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random; 
/** 
* Simple Java program to demonstrate How to use wait, notify and notifyAll() 
* method in Java by solving producer consumer problem.
* 
* @author Javin Paul 
*/
/**
 * 案例二：notify和wait的线程测试
 * 
 * 1.两类线程，一类是生产者，一类是消费者
 * 2.锁定同一个队列Queue<Integer>来执行操作
 * 3.buffer队列有最大值
 * 4.生产者往队列中添加数据，并唤醒其他线程
 * 5.队列满时，线程等待
 * 4.消费者从队列中取数据，并唤醒其他线程
 * 6.队列为空时，线程等待
 * @author Admin
 *
 */
public class Test { 
	
    public static void main(String args[]) { 
    	
        System.out.println("How to use wait and notify method in Java"); 
        System.out.println("Solving Producer Consumper Problem"); 
        Queue<Integer> buffer = new LinkedList<>(); 
        int maxSize = 10; 
        //生产者
        Thread producer = new Producer(buffer, maxSize, "PRODUCER");
        //消费者
        Thread consumer = new Consumer(buffer, maxSize, "CONSUMER"); 
        producer.start();
        consumer.start(); 
        
    } 
} 
    
    