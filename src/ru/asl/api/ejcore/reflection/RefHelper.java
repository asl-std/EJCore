package ru.asl.api.ejcore.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.NonNull;
import ru.asl.api.bukkit.message.EText;

public final class RefHelper {

	public static void invokeMethod(@NonNull Method method, Object instance, Object... args) {
		try {
			method.invoke(instance, args);
		} catch (final IllegalAccessException e) {
			EText.warn("Cant get access to method: " + method.getDeclaringClass().getCanonicalName() + " | " + method.getName());
		} catch (final IllegalArgumentException e) {
			EText.warn("Illegal arguments is presented: " + method.getDeclaringClass().getCanonicalName() + " | " + method.getName());
		} catch (final InvocationTargetException e) {
			EText.warn("InvocationTargetException: " + method.getDeclaringClass().getCanonicalName() + " | " + method.getName());
		}
	}

	public static Class<?> getClass(@NonNull String path) {
		try {
			return Class.forName(path);
		} catch (final ClassNotFoundException e) {
			EText.warn("Cant find classpath: " + path);
			return null;
		}
	}

}
