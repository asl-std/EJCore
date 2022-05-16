package ru.asl.api.expression;

import java.util.List;

import ru.asl.api.expression.exceptions.DivideByZeroException;
import ru.asl.api.expression.exceptions.OverflowException;
import ru.asl.api.expression.exceptions.ParsingException;

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
	 * @throws ru.asl.api.expression.exceptions.OverflowException if any.
	 * @throws ru.asl.api.expression.exceptions.DivideByZeroException if any.
	 * @throws ru.asl.api.expression.exceptions.ParsingException if any.
	 */
	double evaluate(double ...args) throws OverflowException, DivideByZeroException, ParsingException;
	/**
	 * <p>getVariables.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	List<String> getVariables();

}
