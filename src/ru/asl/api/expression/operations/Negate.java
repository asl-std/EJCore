package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.negate;

import ru.asl.api.expression.AbstractUnaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumUnaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

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
	 * @param expr a {@link ru.asl.api.expression.CommonExpression} object
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
