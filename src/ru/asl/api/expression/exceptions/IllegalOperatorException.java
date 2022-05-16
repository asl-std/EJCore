package ru.asl.api.expression.exceptions;

/**
 * <p>IllegalOperatorException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class IllegalOperatorException extends ParsingException {

	/**
	 * <p>Constructor for IllegalOperatorException.</p>
	 *
	 * @param op a {@link java.lang.String} object
	 */
	public IllegalOperatorException(final String op) {
		super("Illegal operation: " + op);
	}

	/**
	 * <p>Constructor for IllegalOperatorException.</p>
	 *
	 * @param op a {@link java.lang.String} object
	 * @param pos a int
	 */
	public IllegalOperatorException(final String op, final int pos) {
		super("Illegal operation: " + op, pos);
	}

}
