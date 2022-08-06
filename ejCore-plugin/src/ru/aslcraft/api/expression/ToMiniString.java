package ru.aslcraft.api.expression;

/**
 * <p>ToMiniString interface.</p>
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @version $Id: $Id
 */
public interface ToMiniString {
	/**
	 * <p>toMiniString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	default String toMiniString() {
		return toString();
	}
}
