package ru.asl.api.expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString {
	default String toMiniString() {
		return toString();
	}
}
