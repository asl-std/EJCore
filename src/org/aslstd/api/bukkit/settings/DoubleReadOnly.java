package org.aslstd.api.bukkit.settings;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * <p>DoubleReadOnly class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@Deprecated /*Not required*/
public class DoubleReadOnly {

	private DoubleSettings settings;

	/**
	 * <p>Constructor for DoubleReadOnly.</p>
	 *
	 * @param settings a {@link ru.aslcraft.api.ejcore.value.DoubleSettings} object
	 */
	public DoubleReadOnly(DoubleSettings settings) {
		this.settings = settings;
	}

	/**
	 * Checks if settings has a custom key
	 *
	 * @return true if {@link java.util.Map} has a key
	 * @param key a {@link java.lang.String} object
	 */
	public boolean hasKey(String key) {
		return settings.hasKey(key);
	}

	/**
	 * Checks if settings has a BASE and SCALE
	 *
	 * @return true if {@link java.util.Map} has a key.base &amp; key.scale
	 * @param key a {@link java.lang.String} object
	 */
	public boolean hasValue(String key) {
		return settings.hasValue(key);
	}

	/**
	 * Checks if settings has a BASE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if {@link java.util.Map} has a key
	 */
	public boolean hasBase(String key) {
		return settings.hasBase(key);
	}

	/**
	 * Checks if settings has a SCALE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if Map has a key.scale
	 */
	public boolean hasScale(String key) {
		return settings.hasScale(key);
	}

	/**
	 * Checks if settings has a BASE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getBase(String key) {
		return settings.getBase(key);
	}

	/**
	 * Checks if settings has a SCALE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getScale(String key) {
		return settings.getScale(key);
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double get(String key) {
		return settings.get(key);
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or def if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or def
	 * @param def a double
	 */
	public double getValue(String key, double def) {
		return settings.getValue(key, def);
	}

	/**
	 * Checks if settings has a BASE <br>
	 * if this true, returns value or def if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @param def returns if value not found
	 * @return value or def
	 */
	public double getBase(String key, double def) {
		return settings.getBase(key, def);
	}

	/**
	 * Checks if settings has a SCALE <br>
	 * if this true, returns value or  if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @param def returns if value not found
	 * @return value or def
	 */
	public double getScale(String key, double def) {
		return settings.getScale(key, def);
	}

	/**
	 * Checks if settings has a BASE and SCALE <br>
	 * if this true, returns value or  if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @param modifier for scale
	 * @return base + (scale * modifier)
	 */
	public double getAndScale(String key, double modifier) {
		return settings.getAndScale(key, modifier);
	}

	/**
	 * <p>dumpToFile.</p>
	 */
	public void dumpToFile(JavaPlugin plugin) {
		settings.dumpToFile(plugin);
	}

	/**
	 * <p>dumpToConsole.</p>
	 */
	public void dumpToConsole() {
		settings.dumpToConsole();
	}

}
