package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.add;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;
import ru.aslcraft.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
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
