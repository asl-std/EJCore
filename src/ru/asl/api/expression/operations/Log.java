package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.log;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;

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
	 * @param left a {@link ru.asl.api.expression.CommonExpression} object
	 * @param right a {@link ru.asl.api.expression.CommonExpression} object
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
