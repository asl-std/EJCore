package ru.asl.api.incubator.lib.injector;

import java.nio.file.Path;

public interface ClassAppender extends AutoCloseable {

	void addDependencyToClasspath(Path path);

	@Override default void close() throws Exception {}
}
