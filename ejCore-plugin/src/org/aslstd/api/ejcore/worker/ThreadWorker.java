package org.aslstd.api.ejcore.worker;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.aslstd.core.Core;

import com.google.common.collect.ImmutableSet;

import lombok.Getter;

public class ThreadWorker extends Thread {

	protected BlockingQueue<WorkerTask<?>> works = new LinkedBlockingQueue<>();

	private boolean locked = false;

	@Getter private String workerName;

	public ThreadWorker(String workerName) { this.workerName = workerName; }

	public void queueTask(WorkerTask<?> task) {
		if (task == null) return;
		works.add(task);
	}

	public void queueTask(WorkerTask<?> task, boolean invokeImmediatly) {
		queueTask(task);
		if (invokeImmediatly)
			invokeAll();
	}

	public Set<CompletableFuture<?>> invokeAll() {
		final Set<CompletableFuture<?>> set = new LinkedHashSet<>();

		while (!works.isEmpty())
			set.add(invoke(works.poll()));

		return ImmutableSet.copyOf(set);
	}

	public CompletableFuture<?> invoke(WorkerTask<?> work) {
		if (locked)
			return Core.getWorkers().submitTask(() -> { while(locked) {} return true; })
					.thenApply(b -> Core.getWorkers().submitTask(work.getTask()) );

		return Core.getWorkers().submitTask(work.getTask());
	}

	public void ping(long millis) {
		if (!works.isEmpty() && !isInterrupted()) {
			locked = true;
			Core.getWorkers().execute(() -> { try { TimeUnit.MILLISECONDS.sleep(millis); } catch (final InterruptedException e) {} pong(); } );
		}
	}

	public void pong() {
		if (!works.isEmpty() && !isInterrupted())
			locked = false;
	}

	public synchronized int getTaskSize() {
		return works.size();
	}

}
