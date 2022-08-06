package ru.aslcraft.core.managers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import lombok.Getter;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.ejcore.module.EJAddon;
import ru.aslcraft.api.ejcore.module.EJModule;
import ru.aslcraft.core.Core;

/**
 * <p>ModuleManager class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ModuleManager {

	@Getter private static final HashMap<String, EJModule> eJModules = new HashMap<>();
	private static final ArrayList<String> loadedJars = new ArrayList<>();

	/**
	 * <p>enableModules.</p>
	 */
	public static void enableModules() {
		for (final EJModule module : ModuleManager.getEJModules().values()) {
			module.loadModule();
			EText.fine("&aModule: &5'" + module.getModuleName() + "' &aloaded, version: &5" + module.getModuleVersion());
		}
	}

	/**
	 * <p>loadModules.</p>
	 *
	 * @param loader a {@link java.lang.ClassLoader} object
	 */
	public static void loadModules(ClassLoader loader) {
		final File folder = new File(Core.instance().getDataFolder(), "modules");
		folder.mkdirs();

		if (folder.exists() && folder.isDirectory()) {
			final File[] addons = folder.listFiles();
			if (addons != null)
				for (final File addon : addons)
					if (!addon.isDirectory() && addon.getName().endsWith(".jar")) {
						final String name = addon.getName().replace(".jar", "").substring(0, addon.getName().indexOf("-"));

						if (!loadedJars.contains(addon.getName().toLowerCase())) {
							if (!Core.getCfg().contains("modules.enable-" + name.toLowerCase()))
								EText.warn("New module &a'" + addon.getName() + "'&4 founded, enable it in config if you need this");

							if (Core.getCfg().getBoolean("modules.enable-" + name.toLowerCase(), false, true))
								loadModule(addon, loader);
						}
					}
		}
	}

	private static void loadModule(File addon, ClassLoader loader) {
		try(final JarFile jAddon = new JarFile(addon);) {
			final Enumeration<JarEntry> entry = jAddon.entries();

			final URL[] urls = { new URL("jar:file:" + addon.getPath() + "!/") };
			final ClassLoader cl = URLClassLoader.newInstance(urls, loader);

			while (entry.hasMoreElements()) {
				final JarEntry je = entry.nextElement();

				if (!je.isDirectory() && je.getName().endsWith(".class")) {

					String className = je.getName().substring(0, je.getName().length() - 6);
					className = className.replace("/", ".");

					try {
						final Class<?> c = Class.forName(className, false, cl);
						if (EJAddon.class.isAssignableFrom(c)) {
							final Object instance = c.getConstructor().newInstance();
							final String name = (String)c.getMethod("getModuleName").invoke(instance);

							eJModules.put(name.toLowerCase(), (EJModule) instance);
							loadedJars.add(addon.getName().toLowerCase());
						}
					} catch (final Exception e) {
						EText.warn("Unable to load module from file: " + addon.getName());
						e.printStackTrace();
					}
				}
			}
		} catch (final Exception e) {
			EText.warn("Unable to load module from file: " + addon.getName());
			e.printStackTrace();
			return;
		}
	}

	/**
	 * <p>isRegistered.</p>
	 *
	 * @param moduleName a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isRegistered(String moduleName) {
		return eJModules.containsKey(moduleName.toLowerCase());
	}

	/**
	 * <p>reloadModules.</p>
	 */
	public static void reloadModules() {
		for (final EJModule module : eJModules.values())
			module.reloadModule();
	}

}
