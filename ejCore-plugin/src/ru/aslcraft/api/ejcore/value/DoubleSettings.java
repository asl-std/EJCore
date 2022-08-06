package ru.aslcraft.api.ejcore.value;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * <p>DoubleSettings class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class DoubleSettings extends Settings<Double> {

	/** Constant <code>GlobalBinds</code> */
	protected static ConcurrentMap<String, List<Consumer<Double>>> GlobalBinds = new ConcurrentHashMap<>();

	/**
	 * Checks if settings has a range custom key
	 *
	 * @return true if {@link java.util.Map} has a key
	 * @param key a {@link java.lang.String} object
	 */
	public boolean hasRange(String key) {
		return settings.containsKey(key+".first");
	}

	/**
	 * {@inheritDoc}
	 *
	 * Checks if settings has a BASE and SCALE
	 */
	@Override
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
		return getBase(key, 0D);
	}

	/**
	 * Checks if settings has a SCALE <br>
	 * if this true, returns value or 0 if false <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or 0
	 */
	public double getScale(String key) {
		return getScale(key,0D);
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
		final double[] range = new double[2];

		range[0] = getValue(key+".first", 0D);
		range[1] = getValue(key+".second", 0D);

		return range;
	}

	/** {@inheritDoc} */
	@Override
	public Double getValue(String key) {
		return getValue(key, 0d);
	}

	/**
	 * Checks if settings has a custom key <br>
	 * if this true, returns value or def <br>
	 *
	 * @param key to search value in {@link java.util.Map}
	 * @return value or def
	 * @param def a double
	 */
	public double getValue(String key, double def) {
		if (hasKey(key))
			return settings.get(key);

		return def;
	}

	/**
	 * Checks if settings has a BASE <br>
	 * if this true, returns value or def <br>
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
	 * if this true, returns value or <br>
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
	 * if this true, returns value <br>
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

	/**
	 * <p>copyValueFrom.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param from a {@link ru.aslcraft.api.ejcore.value.DoubleSettings} object
	 */
	public void copyValueFrom(String key, DoubleSettings from) {
		setValue(key, from.getBase(key), from.getScale(key));
	}

	/**
	 * <p>setRange.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param first a double
	 * @param second a double
	 */
	public void setRange(String key, double first, double second) {
		setCustom(key+".first", first);
		setCustom(key+".second", second);
	}

	/**
	 * <p>setCustom.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a double
	 */
	public void setCustom(String key, double value) {
		settings.put(key, value);
	}

	/**
	 * <p>setBase.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param base a double
	 */
	public void setBase(String key, double base) {
		setCustom(key+".base", base);
	}

	/**
	 * <p>setScale.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param scale a double
	 */
	public void setScale(String key, double scale) {
		setCustom(key+".scale", scale);
	}

	/**
	 * <p>setValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a double
	 * @param scale a double
	 */
	public void setValue(String key, double value, double scale) {
		setBase(key, value);
		setScale(key, scale);
	}

	/**
	 * <p>addRange.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param first a double
	 * @param second a double
	 */
	public void addRange(String key, double first, double second) {
		addCustom(key+".first", first);
		addCustom(key+".second", second);
	}

	/**
	 * <p>addCustom.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a double
	 */
	public void addCustom(String key, double value) {
		double val = 0D;
		if (hasKey(key))
			val = getValue(key,0D);

		setCustom(key, val + value);
	}

	/**
	 * <p>addValue.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param base a double
	 * @param scale a double
	 */
	public void addValue(String key, double base, double scale) {
		double vBase = 0D;
		double vScale = 0D;
		if (hasBase(key))
			vBase = this.getBase(key);

		if (hasScale(key))
			vScale = this.getScale(key);

		setBase(key, vBase + base);
		setScale(key, vScale + scale);
	}

	/**
	 * <p>addBase.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param value a double
	 */
	public void addBase(String key, double value) {
		double val = 0D;
		if (hasBase(key))
			val = this.getBase(key,0D);

		setBase(key, val + value);
	}

	/**
	 * <p>addScale.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param scale a double
	 */
	public void addScale(String key, double scale) {
		double val = 0D;
		if (hasScale(key))
			val = this.getScale(key, 0D);

		setScale(key, val + scale);
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String key, Double value) { this.setValue(key, value, 0D); }

}
