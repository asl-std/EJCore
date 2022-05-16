package ru.asl.modules.attributes;

/**
 * Enum collection of attributes calculating types.
 *
 * @author Snoopy
 * @version $Id: $Id
 */
public enum AttrType {
	/**
	 * <h1>Means:</h1>Attribute uses two values for calculating (<i><b>lore view attr: 2-3</b></i>)<br><br>
	 *
	 * Every time attribute calculates (<i>for ex.Physical-Damage</i>)<br>
	 * result value will be between <b>first</b> and second <b>value</b>;
	 */
	RANGE,
	/**
	 * <h1>Means:</h1>Attribute uses first value as base, and second for per-level scaling<br><br>
	 *
	 * This type allows to connect global attributes like Fist-Damage between N amount of modules/plugins<br>
	 * The main type for <b>player-scoped</b> attributes.
	 */
	PER_LEVEL,
	/**
	 * <h1>Means:</h1>Attribute will be used flatly without random or scaling.
	 */
	SINGLE;
}
