package org.aslstd.api.expression.operation;

import static org.aslstd.api.expression.DoubleOperations.log;

import org.aslstd.api.expression.AbstractBinaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumBinaryOperation;

/**
 * <p>Log class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Log extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Log.</p>
	 *
	 * @param left a {@link org.aslstd.api.expression.CommonExpression} object
	 * @param right a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Log(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Log);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws ArithmeticException {
		return log(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 3;
	}
}
