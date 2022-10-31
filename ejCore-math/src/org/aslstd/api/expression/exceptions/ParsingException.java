package org.aslstd.api.expression.exceptions;

/**
 * <p>ParsingException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public class ParsingException extends Exception {
	/**
	 * <p>Constructor for ParsingException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public ParsingException(final String message) {
		super(message);
	}
	/**
	 * <p>Constructor for ParsingException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param pos a int
	 */
	public ParsingException(final String message, final int pos) {
		super("pos: " + pos + ", " + message);
	}
}
