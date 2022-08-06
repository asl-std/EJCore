package ru.aslcraft.api.varsystem.tags;

/**
 * <p>TagType class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public enum TagType {
	DEFAULT, RETURN_STRING;

	/**
	 * <p>forName.</p>
	 *
	 * @param str a {@link java.lang.String} object
	 * @return a {@link ru.aslcraft.api.varsystem.tags.TagType} object
	 */
	public static TagType forName(String str) {
		switch(str.toUpperCase()) {
		case "RETURNSTRING":
		case "RETURN_STRING":
			return RETURN_STRING;
		default:
			return DEFAULT;
		}
	}
}
