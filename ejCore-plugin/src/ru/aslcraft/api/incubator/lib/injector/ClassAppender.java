package ru.aslcraft.api.incubator.lib.injector;

import java.nio.file.Path;

/**
 * <p>ClassAppender interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface ClassAppender extends AutoCloseable {

	/**
	 * <p>addDependencyToClasspath.</p>
	 *
	 * @param path a {@link java.nio.file.Path} object
	 */
	void addDependencyToClasspath(Path path);

	/** {@inheritDoc} */
	@Override default void close() throws Exception {}
}
