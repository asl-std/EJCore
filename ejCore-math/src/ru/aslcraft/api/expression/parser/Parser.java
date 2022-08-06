package ru.aslcraft.api.expression.parser;

import ru.aslcraft.api.expression.Expression;
import ru.aslcraft.api.expression.exceptions.ParsingException;

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
	 * @return a {@link ru.aslcraft.api.expression.Expression} object
	 * @throws ru.aslcraft.api.expression.exceptions.ParsingException if any.
	 */
	Expression parse(String expression) throws ParsingException;
}
