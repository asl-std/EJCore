package org.aslstd.api.expression.exception;

/**
 * <p>UnexpectedSymbolException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public class UnexpectedSymbolException extends ParsingException {
	/**
	 * <p>Constructor for UnexpectedSymbolException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public UnexpectedSymbolException(final String message) {
		super(message);
	}
	/**
	 * <p>Constructor for UnexpectedSymbolException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param pos a int
	 */
	public UnexpectedSymbolException(final String message, final int pos) {
		super(message, pos);
	}
}
