package ru.aslcraft.api.expression.parser;

/**
 * <p>ExpressionException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public class ExpressionException extends RuntimeException {
	/**
	 * <p>Constructor for ExpressionException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public ExpressionException(final String message) {
		super(message);
	}
}
