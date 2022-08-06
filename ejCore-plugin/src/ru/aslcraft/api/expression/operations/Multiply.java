package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.multiply;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;
import ru.aslcraft.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
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
