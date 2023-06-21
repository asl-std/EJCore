package org.aslstd.api.expression.operation;

import static org.aslstd.api.expression.DoubleOperations.mod;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;
import org.aslstd.api.expression.exception.OverflowException;

/**
 * <p>Mod class.</p>
 *
 * @author Snoop1CattZ69
 */
public class Mod extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Mod.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Mod(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Mod);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return mod(x,y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 2;
	}
}
