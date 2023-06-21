package org.aslstd.api.expression;

import java.util.List;

import org.aslstd.api.expression.exception.DivideByZeroException;
import org.aslstd.api.expression.exception.OverflowException;

/**
 * <p>Expression interface.</p>
 *
 * @author Snoop1CattZ69
 */
public interface Expression extends ToMiniString {
	/**
	 * <p>evaluate.</p>
	 *
	 * @param args a double
	 * @return a double
	 * @throws org.aslstd.api.expression.exception.OverflowException if any.
	 * @throws org.aslstd.api.expression.exception.DivideByZeroException if any.
	 * @throws org.aslstd.api.expression.exception.ParsingException if any.
	 */
	double evaluate(double ...args) throws OverflowException, DivideByZeroException;
	/**
	 * <p>getVariables.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	List<String> getVariables();

}
