package org.aslstd.api.expression.parser;

import org.aslstd.api.expression.Expression;
import org.aslstd.api.expression.exception.ParsingException;

/**
 * <p>Parser interface.</p>
 *
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)

 */
public interface Parser {
	/**
	 * <p>parse.</p>
	 *
	 * @param expression a {@link String} object
	 * @return a {@link org.aslstd.api.expression.Expression} object
	 * @throws org.aslstd.api.expression.exception.ParsingException if any.
	 */
	Expression parse(String expression) throws ParsingException;
}
