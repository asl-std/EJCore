package ru.asl.api.expression.exceptions;

/**
 * <p>DivideByZeroException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class DivideByZeroException extends ArithmeticException {
	/**
	 * <p>Constructor for DivideByZeroException.</p>
	 */
	public DivideByZeroException() {
		super("Division by zero");
	}
}
