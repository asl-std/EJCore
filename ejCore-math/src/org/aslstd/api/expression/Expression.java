package org.aslstd.api.expression;

import java.util.List;

import org.aslstd.api.expression.exceptions.DivideByZeroException;
import org.aslstd.api.expression.exceptions.OverflowException;

/**
 * <p>Expression interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Expression extends ToMiniString {
	/**
	 * <p>evaluate.</p>
	 *
	 * @param args a double
	 * @return a double
	 * @throws org.aslstd.api.expression.exceptions.OverflowException if any.
	 * @throws org.aslstd.api.expression.exceptions.DivideByZeroException if any.
	 * @throws org.aslstd.api.expression.exceptions.ParsingException if any.
	 */
	double evaluate(double ...args) throws OverflowException, DivideByZeroException;
	/**
	 * <p>getVariables.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	List<String> getVariables();

}
