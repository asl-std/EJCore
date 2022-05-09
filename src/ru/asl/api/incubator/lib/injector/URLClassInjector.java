package ru.asl.api.incubator.lib.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

public abstract class URLClassInjector {
	protected final URLClassLoader classLoader;

	private static URLClassInjector injector;

	public static URLClassInjector getInjector(URLClassLoader classLoader) {
		if (injector == null) {
			if (ReflectionClassInjector.isSupported())
				injector = new ReflectionClassInjector(classLoader);
			if (UnsafeClassInjector.isSupported())
				injector = new UnsafeClassInjector(classLoader);
		}

		return injector;
	}

	private static void error(Throwable exception) {
		throw new UnsupportedOperationException("ejCore can't inject libs into the plugin ClassLoader\n"
				+ "You may be able to fix this problem by adding the following command-line argument directly after the 'java' command in your start script: \n"
				+ "'--add-opens java.base/java.lang=ALL-UNNAMED'", exception);
	}

	public URLClassInjector(URLClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public abstract void addURL(URL path);

	public static class ReflectionClassInjector extends URLClassInjector {

		private static final Method ADD_URL;

		static {
			Method addUrlMethod;
			try {
				addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
				addUrlMethod.setAccessible(true);
			} catch (final Exception e) {
				addUrlMethod = null;
			}
			ADD_URL = addUrlMethod;
		}

		private static boolean isSupported() {
			return (ADD_URL != null);
		}

		public ReflectionClassInjector(URLClassLoader classLoader) {
			super(classLoader);
		}

		@Override
		public void addURL(URL url) {
			try {
				ADD_URL.invoke(classLoader, url);
			} catch (final ReflectiveOperationException e) {
				URLClassInjector.error(e);
			}
		}

	}

	@SuppressWarnings({"restriction","unchecked"})
	public static class UnsafeClassInjector extends URLClassInjector {

		private static final sun.misc.Unsafe UNSAFE;

		private final Collection<URL> unopened;

		private final Collection<URL> paths;

		static {
			sun.misc.Unsafe unsafe;
			try {
				final Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
				unsafeField.setAccessible(true);
				unsafe = (sun.misc.Unsafe) unsafeField.get(null);
			} catch (final Throwable t) {
				unsafe = null;
			}
			UNSAFE = unsafe;
		}

		private static boolean isSupported() {
			return UNSAFE != null;
		}

		public UnsafeClassInjector(URLClassLoader classLoader) {
			super(classLoader);
			Collection<URL> unopened, paths;
			try {
				final Object ucp = fetch(URLClassLoader.class, classLoader, "ucp");
				unopened = (Collection<URL>) fetch(ucp.getClass(), ucp, "unopened");
				paths = (Collection<URL>) fetch(ucp.getClass(), ucp, "path");
			} catch (final Throwable e) {
				unopened = null;
				paths = null;
			}
			this.unopened = unopened;
			this.paths = paths;
		}

		private static Object fetch(Class<?> clazz, Object object, String name) throws NoSuchFieldException {
			final Field field = clazz.getDeclaredField(name);
			final long offset = UNSAFE.objectFieldOffset(field);
			return UNSAFE.getObject(object, offset);
		}

		@Override
		public void addURL(URL url) {
			if (unopened == null || paths == null)
				throw new NullPointerException("Something went wrong with collections");

			unopened.add(url);
			paths.add(url);
		}
	}

	public static class Nil extends URLClassInjector {

		@Override
		public void addURL(URL url) {
			URLClassInjector.error(null);
		}
		public Nil() {
			super(null);
		}

	}

}
