package org.aslstd.api.expression.operation;

import static org.aslstd.api.expression.DoubleOperations.negate;

import org.aslstd.api.expression.AbstractUnaryOperation;
import org.aslstd.api.expression.CommonExpression;
import org.aslstd.api.expression.EnumUnaryOperation;
import org.aslstd.api.expression.exception.OverflowException;

/**
 * <p>Negate class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Negate extends AbstractUnaryOperation {
	/**
	 * <p>Constructor for Negate.</p>
	 *
	 * @param expr a {@link org.aslstd.api.expression.CommonExpression} object
	 */
	public Negate(CommonExpression expr) {
		super(expr, EnumUnaryOperation.UnMinus);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x) throws OverflowException {
		return negate(x);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 4;
	}
}
