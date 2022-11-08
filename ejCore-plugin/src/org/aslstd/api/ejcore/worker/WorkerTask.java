package org.aslstd.api.ejcore.worker;

import java.util.function.Supplier;

import lombok.Getter;
import lombok.Setter;

public class WorkerTask<V> {

	@Getter @Setter private Class<V> finishType;

	@Getter private volatile boolean finished = false;

	@Getter private Supplier<V> task;

	@Getter private V result;

	public WorkerTask(Supplier<V> task, Class<V> clazz) {
		this.finishType = clazz;
		this.task = task;
	}

	public void complete(V result) {
		this.result = result;
		this.finished = true;
	}

}
