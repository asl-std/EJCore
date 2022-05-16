package ru.asl.api.expression.parser;

/**
 * <p>ExpressionSource interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface ExpressionSource {
	/**
	 * <p>hasNext.</p>
	 *
	 * @return a boolean
	 */
	boolean hasNext();
	/**
	 * <p>next.</p>
	 *
	 * @return a char
	 */
	char next();
	/**
	 * <p>error.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @return a {@link ru.asl.api.expression.parser.ExpressionException} object
	 */
	ExpressionException error(final String message);
	/**
	 * <p>getPos.</p>
	 *
	 * @return a int
	 */
	int getPos();
	/**
	 * <p>nextVariableNum.</p>
	 *
	 * @return a int
	 */
	int nextVariableNum();
}
