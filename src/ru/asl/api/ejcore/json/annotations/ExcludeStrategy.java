package ru.asl.api.ejcore.json.annotations;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import lombok.Getter;

public final class ExcludeStrategy implements ExclusionStrategy {

	@Getter private static ExcludeStrategy strategy = new ExcludeStrategy();

	private ExcludeStrategy() {}

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(Exclude.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return clazz.getAnnotation(Exclude.class) != null;
	}

}
