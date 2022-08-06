package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.subtract;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;
import ru.aslcraft.api.expression.exceptions.OverflowException;

/**
 * <p>Subtract class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Subtract extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Subtract.</p>
	 *
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
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
