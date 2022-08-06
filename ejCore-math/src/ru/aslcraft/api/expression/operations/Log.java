package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.log;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;

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
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
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
