package org.aslstd.api.expression.operation;

import static org.aslstd.api.expression.DoubleOperations.subtract;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;
import org.aslstd.api.expression.exception.OverflowException;

/**
 * <p>Subtract class.</p>
 *
 * @author Snoop1CattZ69
 */
public class Subtract extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Subtract.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Subtract(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Sub);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return subtract(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 1;
	}
}
