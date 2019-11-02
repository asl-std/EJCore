package ru.asl.api.ejcore.value;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.yaml.YAML;

public class Settings {
	public static final String base = "base";
	public static final String scale = "scale";

	public ConcurrentHashMap<String, Double> settings = new ConcurrentHashMap<>();

	/**
	 * Checks if settings has a custom key
	 *
	 * @return true if {@link java.util.Map} has a key
	 */
	public boolean hasKey(String key) {
		return settings.containsKey(key);
	}

	/**
	 * Checks if settings has a range custom key
	 *
	 * @return true if {@link java.util.Map} has a key
	 */
	public boolean hasRange(String key) {
		return settings.containsKey(key+".first");
	}

	/**
	 * Checks if settings has a BASE and SCALE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if {@link java.util.Map} has a key.base & key.scale
	 */
	public boolean hasValue(String key) {
		return hasBase(key) && hasScale(key);
	}

	/**
	 * Checks if settings has a BASE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if {@link java.util.Map} has a key
	 */
	public boolean hasBase(String key) {
		return hasKey(key+".base");
	}

	/**
	 * Checks if settings has a SCALE
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return true if Map has a key.scale
	 */
	public boolean hasScale(String key) {
		return hasKey(key+".scale");
	}

	/**
	 * Checks if settings has a BASE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getBase(String key) {
		return this.getBase(key, 0D);
	}

	/**
	 * Checks if settings has a SCALE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getScale(String key) {
		return this.getScale(key,0D);
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double get(String key) {
		return getValue(key, 0D);
	}

	/**
	 * Checks if settings has a first and second custom key <br>
	 * if this true, returns array with 2 values or array with 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double[] getRange(String key) {
		double[] range = new double[2];

		range[0] = getValue(key+".first", 0D);
		range[1] = getValue(key+".second", 0D);

		return range;
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or def if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or def
	 */
	public double getValue(String key, double def) {
		if (hasKey(key))
			return settings.get(key);

		return def;
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
		return getValue(key + ".base", def);
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
		return getValue(key + ".scale", def);
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
		double base = 0D;
		double scale = 0D;

		if (hasBase(key))
			base = this.getBase(key);

		if (hasScale(key))
			scale = this.getScale(key);

		return base + (scale * modifier);
	}

	public void replaceRange(String key, double first, double second) {
		replaceCustom(key+".first", first);
		replaceCustom(key+".second", second);
	}

	public void replaceCustom(String key, double value) {
		settings.put(key, value);
	}

	public void replaceBase(String key, double base) {
		settings.put(key+".base", base);
	}

	public void replaceScale(String key, double scale) {
		settings.put(key+".scale", scale);
	}

	public void replaceValue(String key, double value, double scale) {
		replaceBase(key, value);
		replaceScale(key, scale);
	}

	public void addRange(String key, double first, double second) {
		addCustom(key+".first", first);
		addCustom(key+".second", second);
	}

	public void addCustom(String key, double value) {
		double val = 0D;
		if (hasKey(key))
			val = getValue(key,0D);

		settings.put(key, val + value);
	}


	public void addValue(String key, double base, double scale) {
		double vBase = 0D;
		double vScale = 0D;
		if (hasBase(key))
			vBase = this.getBase(key);

		if (hasScale(key))
			vScale = this.getScale(key);

		replaceBase(key, vBase + base);
		replaceScale(key, vScale + scale);
	}

	public void addBase(String key, double value) {
		double val = 0D;
		if (hasBase(key))
			val = this.getBase(key,0D);

		replaceBase(key, val + value);
	}

	public void addScale(String key, double scale) {
		double val = 0D;
		if (hasScale(key))
			val = this.getScale(key, 0D);

		replaceScale(key, val + scale);
	}

	public void remove(String key) {
		if (hasKey(key))
			settings.remove(key);
	}

	public void removePath(String path) {
		Enumeration<String> keys = settings.keys();
		for (String key = "NPE" ; keys.hasMoreElements() ; ) {
			key = keys.nextElement();
			EText.send(key);
			EText.send(path);

			if (key.startsWith(path))
				settings.remove(key);

			continue;
		}
	}

	public void removePathByPart(String partOfPath) {
		Enumeration<String> keys = settings.keys();
		for (String key = "NPE" ; keys.hasMoreElements() ; ) {
			key = keys.nextElement();
			EText.send(key);
			EText.send(partOfPath);

			if (key.contains(partOfPath)) {
				EText.send("removed");
				settings.remove(key);
			}

			continue;
		}
	}

	public void dumpToFile() {
		Enumeration<String> keys = settings.keys();

		File dumpFile = new File("plugins/ejCore/dump." + System.currentTimeMillis() + "." + toString() + ".yml");
		try { dumpFile.createNewFile(); }
		catch (IOException e) { dumpToConsole(); return; }

		YAML dump = new YAML(dumpFile);

		for (String path = keys.nextElement() ; keys.hasMoreElements() ; path = keys.nextElement()) {
			if (path == null || path.equalsIgnoreCase("")) continue;
			dump.set(path, get(path));
		}
	}

	public void dumpToConsole() {
		Enumeration<String> keys = settings.keys();

		for (String path = keys.nextElement() ; keys.hasMoreElements() ; path = keys.nextElement())
			EText.warn(path + ": &a" + get(path));
	}

}
