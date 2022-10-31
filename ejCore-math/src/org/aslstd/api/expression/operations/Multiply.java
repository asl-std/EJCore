package org.aslstd.api.expression.operations;

import static org.aslstd.api.expression.DoubleOperations.multiply;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;
import org.aslstd.api.expression.exceptions.OverflowException;

/**
 * <p>Multiply class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Multiply extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Multiply.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Multiply(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Mult);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return multiply(x,y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 2;
	}
}
