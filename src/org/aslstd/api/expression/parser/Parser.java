package org.aslstd.api.expression.parser;

import org.aslstd.api.expression.Expression;
import org.aslstd.api.expression.exceptions.ParsingException;

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
	 * @return a {@link org.aslstd.api.expression.Expression} object
	 * @throws org.aslstd.api.expression.exceptions.ParsingException if any.
	 */
	Expression parse(String expression) throws ParsingException;
}
