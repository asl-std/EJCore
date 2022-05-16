package ru.asl.api.ejcore.value.random;

import lombok.Getter;
import lombok.Setter;
import ru.asl.api.ejcore.value.abstrakt.ModifierType;
import ru.asl.api.ejcore.value.util.ValueUtil;

/**
 * <p>Value class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Value {

	@Getter private String value;

	@Getter @Setter private ModifierType type = ModifierType.POSITIVE;

	@Getter @Setter private String keyName;

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 */
	public void setValue(String value) {
		this.value = value.replaceAll("%", "");
	}

	/**
	 * <p>Setter for the field <code>value</code>.</p>
	 *
	 * @param value a {@link java.lang.Number} object
	 */
	public void setValue(Number value) {
		this.value = String.valueOf(value);
	}

	/**
	 * <p>getAndScale.</p>
	 *
	 * @param scale a double
	 * @param lvl a double
	 * @return a {@link java.lang.String} object
	 */
	public String getAndScale(double scale, double lvl) {
		if (value == null) return null;

		return String.valueOf((ValueUtil.parseDouble(value) + (scale * lvl)));
	}

	/**
	 * <p>getAndScale.</p>
	 *
	 * @param scale a {@link java.lang.String} object
	 * @param lvl a double
	 * @return a {@link java.lang.String} object
	 */
	public String getAndScale(String scale, double lvl) {
		if (value == null) return null;

		final String[] sc = scale.split("-");
		final String[] val = value.split("-");

		if (sc.length < 2)
			return (ValueUtil.parseDouble(val[0]) + ValueUtil.parseDouble(sc[0]) * lvl) + "-" + (ValueUtil.parseDouble(val[1]) + ValueUtil.parseDouble(sc[0]) * lvl);
		else
			return (ValueUtil.parseDouble(val[0]) + ValueUtil.parseDouble(sc[0]) * lvl) + "-" + (ValueUtil.parseDouble(val[1]) + ValueUtil.parseDouble(sc[1]) * lvl);
	}

	/**
	 * <p>Constructor for Value.</p>
	 */
	public Value() {}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 */
	public Value(String value) {
		setValue(value);
	}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link java.lang.Number} object
	 */
	public Value(Number value) {
		setValue(value);
	}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link java.lang.String} object
	 * @param type a {@link ru.asl.api.ejcore.value.abstrakt.ModifierType} object
	 */
	public Value(String value, ModifierType type) {
		this(value);
		setType(type);
	}

	/**
	 * <p>Constructor for Value.</p>
	 *
	 * @param value a {@link java.lang.Number} object
	 * @param type a {@link ru.asl.api.ejcore.value.abstrakt.ModifierType} object
	 */
	public Value(Number value, ModifierType type) {
		this(value);
		setType(type);
	}

}
