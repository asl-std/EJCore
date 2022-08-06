package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.pow;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;
import ru.aslcraft.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
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
