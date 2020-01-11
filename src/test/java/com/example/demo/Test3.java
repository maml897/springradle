package com.example.demo;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test3
{
	public static void main(String[] args) {
		AtomicInteger nextId = new AtomicInteger(1);
		int blockingQueueSize = 10;
		ThreadFactory threadFactory = new ThreadFactory()
		{
			@Override
			public Thread newThread(Runnable r)
			{
				String threadName = "test-" + nextId.getAndIncrement();
				Thread thread = new Thread(r, threadName);
				return thread;
			}
		};
		Comparator<Runnable> comparator = new Comparator<Runnable>()
		{
			@Override
			public int compare(Runnable o1, Runnable o2)
			{
				return 1;
			}
		};
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
				
//				PriorityBlockingQueue<>(blockingQueueSize,comparator);
		RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler()
		{
			
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
			{
				System.out.println("拒绝");
			}
		};
		
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30000,
				TimeUnit.SECONDS, workQueue, threadFactory, rejectedExecutionHandler);
		
		threadPoolExecutor.allowCoreThreadTimeOut(true);
		
		for (int i = 0; i < 50; i++) {
			threadPoolExecutor.submit(() -> {
                System.out.println("thread id is: " + Thread.currentThread().getName());
                System.out.println(threadPoolExecutor.getQueue().size());
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
		System.out.println("===");
    }
}
