package ru.aslcraft.api.ejcore.json.annotations;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import lombok.Getter;

/**
 * <p>ExcludeStrategy class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class ExcludeStrategy implements ExclusionStrategy {

	@Getter private static ExcludeStrategy strategy = new ExcludeStrategy();

	private ExcludeStrategy() {}

	/** {@inheritDoc} */
	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(Exclude.class) != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return clazz.getAnnotation(Exclude.class) != null;
	}

}
