package org.aslstd.api.expression.operation;

import static org.aslstd.api.expression.DoubleOperations.pow;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;
import org.aslstd.api.expression.exception.OverflowException;

/**
 * <p>Pow class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Pow extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Pow.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Pow(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Pow);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException, ArithmeticException {
		return pow(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 3;
	}
}
