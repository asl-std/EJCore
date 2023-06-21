package org.aslstd.core.manager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.aslstd.api.bukkit.message.Text;
import org.aslstd.api.openlib.module.OpenAddon;
import org.aslstd.api.openlib.module.OpenModule;
import org.aslstd.core.OpenLib;

import lombok.Getter;

/**
 * <p>ModuleManager class.</p>
 *
 * @author Snoop1CattZ69
 */
public class ModuleManager {

	@Getter private static final HashMap<String, OpenModule> eJModules = new HashMap<>();
	private static final ArrayList<String> loadedJars = new ArrayList<>();

	/**
	 * <p>enableModules.</p>
	 */
	public static void enableModules() {
		for (final OpenModule module : ModuleManager.getEJModules().values()) {
			module.load();
			Text.fine("&aModule: &5'" + module.name() + "' &aloaded, version: &5" + module.version());
		}
	}

	/**
	 * <p>loadModules.</p>
	 *
	 * @param loader a {@link java.lang.ClassLoader} object
	 */
	public static void loadModules(ClassLoader loader) {
		final File folder = new File(OpenLib.instance().getDataFolder(), "modules");
		folder.mkdirs();

		final List<URL> urls = new ArrayList<>();
		final List<File> toLoad = new ArrayList<>();

		if (folder.exists() && folder.isDirectory()) {
			final File[] addons = folder.listFiles();
			if (addons != null)
				for (final File addon : addons)
					if (!addon.isDirectory() && addon.getName().endsWith(".jar")) {
						final String name = addon.getName().replace(".jar", "").substring(0, addon.getName().indexOf("-"));

						if (!loadedJars.contains(addon.getName().toLowerCase())) {
							if (!OpenLib.config().contains("modules.enable-" + name.toLowerCase()))
								Text.warn("New module &a'" + addon.getName() + "'&4 founded, enable it in config if you need this");

							if (OpenLib.config().getBoolean("modules.enable-" + name.toLowerCase(), OpenLib.config().MODULES_BY_DEFAULT, true)) {
								try {
									urls.add(new URL("jar:file:" + addon.getPath() + "!/"));
									toLoad.add(addon);
								} catch (final MalformedURLException e) { Text.send("Module " + name + " cannot be loaded correctly"); }
							}
						}
					}

		}

		toLoad.forEach(file -> loadModule(file, URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]), loader)));
	}

	private static void loadModule(File addon, ClassLoader loader) {
		try(final JarFile jAddon = new JarFile(addon);) {
			final Enumeration<JarEntry> entry = jAddon.entries();

			while (entry.hasMoreElements()) {
				final JarEntry je = entry.nextElement();

				if (!je.isDirectory() && !je.getName().endsWith("module-info.class") && je.getName().endsWith(".class")) {

					final String className = getClassName(je);

					try {
						final Class<?> c = Class.forName(className, true, loader);
						if (OpenAddon.class.isAssignableFrom(c)) {
							final Object instance = c.getConstructor().newInstance();
							final String name = (String)c.getMethod("getModuleName").invoke(instance);

							eJModules.put(name.toLowerCase(), (OpenModule) instance);
							loadedJars.add(addon.getName().toLowerCase());
						}
					} catch (final Exception e) {
						Text.warn("Unable to load module from file: " + addon.getName());
						e.printStackTrace();
					}
				}
			}
		} catch (final Exception e) {
			Text.warn("Unable to load module from file: " + addon.getName());
			e.printStackTrace();
			return;
		}
	}

	private static String getClassName(JarEntry entry) {
		return getClassName(entry.getName());
	}

	public static String getClassName(String name) {
		return name.substring(0, name.length()-6).replace("/", ".");
	}

	/**
	 * <p>isRegistered.</p>
	 *
	 * @param moduleName a {@link String} object
	 * @return a boolean
	 */
	public static boolean isRegistered(String moduleName) {
		return eJModules.containsKey(moduleName.toLowerCase());
	}

	/**
	 * <p>reloadModules.</p>
	 */
	public static void reloadModules() {
		for (final OpenModule module : eJModules.values())
			module.reload();
	}

}
