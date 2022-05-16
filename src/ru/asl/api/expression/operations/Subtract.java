package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.subtract;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.asl.api.expression.CommonExpression} object
	 * @param right a {@link ru.asl.api.expression.CommonExpression} object
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
