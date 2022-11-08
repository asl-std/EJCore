package org.aslstd.api.ejcore.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class WorkerService {
	private static boolean lock;

	protected ScheduledExecutorService alpha;
	protected ExecutorService beta;

	public WorkerService(int poolSize) {
		if (!lock)
			throw new SecurityException("You can't create more than one worker service.");
		alpha = Executors.newSingleThreadScheduledExecutor(new WorkerFactory("ALPHA"));
		if (poolSize > 1)
			beta = Executors.newFixedThreadPool(poolSize-1);
		lock = true;
	}

	public boolean betaAvailable() {
		return beta != null;
	}

	public <V> ScheduledFuture<V> scheduleTask(Callable<V> task) {
		return alpha.schedule(task, 0, TimeUnit.NANOSECONDS);
	}

	public <T> CompletableFuture<T> submitTask(Supplier<T> task) {
		return CompletableFuture.supplyAsync(task, beta);
	}

	public CompletableFuture<Void> execute(Runnable task) {
		return CompletableFuture.runAsync(task, beta);
	}

	public void shutdown() {
		if (!alpha.isShutdown()) alpha.shutdown();
		if (!beta.isShutdown()) beta.shutdown();
	}

	static class WorkerFactory implements ThreadFactory {
		private final ThreadGroup group;
		private final AtomicInteger threadNumber = new AtomicInteger(1);
		private final String namePrefix;

		WorkerFactory(String namePrefix) {
			final SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			this.namePrefix = "pool-ejCore-" + namePrefix + "-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement());
			if (t.isDaemon()) t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
			return t;
		}
	}

}
