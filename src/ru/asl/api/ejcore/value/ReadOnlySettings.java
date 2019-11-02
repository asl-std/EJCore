package ru.asl.api.ejcore.value;

public class ReadOnlySettings {

	private Settings settings;

	public ReadOnlySettings(Settings settings) {
		this.settings = settings;
	}

	/**
	 * Checks if settings has a custom key
	 *
	 * @return true if {@link java.util.Map} has a key
	 */
	public boolean hasKey(String key) {
		return this.settings.hasKey(key);
	}

	/**
	 * Checks if settings has a BASE and SCALE
	 *
	 * @return true if {@link java.util.Map} has a key.base & key.scale
	 */
	public boolean hasValue(String key) {
		return this.settings.hasValue(key);
	}

	/**
	 * Checks if settings has a BASE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if {@link java.util.Map} has a key
	 */
	public boolean hasBase(String key) {
		return this.settings.hasBase(key);
	}

	/**
	 * Checks if settings has a SCALE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if Map has a key.scale
	 */
	public boolean hasScale(String key) {
		return this.settings.hasScale(key);
	}

	/**
	 * Checks if settings has a BASE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getBase(String key) {
		return this.settings.getBase(key);
	}

	/**
	 * Checks if settings has a SCALE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getScale(String key) {
		return this.settings.getScale(key);
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double get(String key) {
		return this.settings.get(key);
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or def if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or def
	 */
	public double getValue(String key, double def) {
		return this.settings.getValue(key, def);
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
		return this.settings.getBase(key, def);
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
		return this.settings.getScale(key, def);
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
		return this.settings.getAndScale(key, modifier);
	}

	public void dumpToFile() {
		this.settings.dumpToFile();
	}

	public void dumpToConsole() {
		this.settings.dumpToConsole();
	}

}
