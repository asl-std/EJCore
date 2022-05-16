package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.add;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

/**
 * <p>Add class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Add extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Add.</p>
	 *
	 * @param left a {@link ru.asl.api.expression.CommonExpression} object
	 * @param right a {@link ru.asl.api.expression.CommonExpression} object
	 */
	public Add(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Add);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return add(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 1;
	}
}
