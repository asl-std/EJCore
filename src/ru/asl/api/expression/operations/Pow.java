package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.pow;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.asl.api.expression.CommonExpression} object
	 * @param right a {@link ru.asl.api.expression.CommonExpression} object
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
