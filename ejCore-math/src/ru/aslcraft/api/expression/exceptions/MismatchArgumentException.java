package ru.aslcraft.api.expression.exceptions;

/**
 * <p>MismatchArgumentException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public class MismatchArgumentException extends ParsingException {
	/**
	 * <p>Constructor for MismatchArgumentException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public MismatchArgumentException(final String message) {
		super(message);
	}
	/**
	 * <p>Constructor for MismatchArgumentException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param pos a int
	 */
	public MismatchArgumentException(final String message, final int pos) {
		super(message, pos);
	}
}
