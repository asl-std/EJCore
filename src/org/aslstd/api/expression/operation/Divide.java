package org.aslstd.api.expression.operation;

import static org.aslstd.api.expression.DoubleOperations.divide;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;
import org.aslstd.api.expression.exception.DivideByZeroException;
import org.aslstd.api.expression.exception.OverflowException;

/**
 * <p>Divide class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Divide extends AbstractBinaryOperation {

	/**
	 * <p>Constructor for Divide.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Divide(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Div);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException, DivideByZeroException {
		return divide(x,y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 2;
	}

}
