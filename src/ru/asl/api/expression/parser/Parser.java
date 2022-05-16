package ru.asl.api.expression.parser;

import ru.asl.api.expression.Expression;
import ru.asl.api.expression.exceptions.ParsingException;

/**
 * <p>Parser interface.</p>
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 * @version $Id: $Id
 */
public interface Parser {
	/**
	 * <p>parse.</p>
	 *
	 * @param expression a {@link java.lang.String} object
	 * @return a {@link ru.asl.api.expression.Expression} object
	 * @throws ru.asl.api.expression.exceptions.ParsingException if any.
	 */
	Expression parse(String expression) throws ParsingException;
}
