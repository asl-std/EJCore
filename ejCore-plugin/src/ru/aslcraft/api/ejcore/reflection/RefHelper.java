package ru.aslcraft.api.ejcore.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.NonNull;
import ru.aslcraft.api.bukkit.message.EText;

/**
 * <p>RefHelper class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class RefHelper {

	/**
	 * <p>invokeMethod.</p>
	 *
	 * @param method a {@link java.lang.reflect.Method} object
	 * @param instance a {@link java.lang.Object} object
	 * @param args a {@link java.lang.Object} object
	 */
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

	/**
	 * <p>getClass.</p>
	 *
	 * @param path a {@link java.lang.String} object
	 * @return a {@link java.lang.Class} object
	 */
	public static Class<?> getClass(@NonNull String path) {
		try {
			return Class.forName(path);
		} catch (final ClassNotFoundException e) {
			EText.warn("Cant find classpath: " + path);
			return null;
		}
	}

}
