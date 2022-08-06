package ru.aslcraft.api.expression.exceptions;

/**
 * <p>OverflowException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public class OverflowException extends ArithmeticException {
	/**
	 * <p>Constructor for OverflowException.</p>
	 */
	public OverflowException() {
		super("Overflow");
	}
}