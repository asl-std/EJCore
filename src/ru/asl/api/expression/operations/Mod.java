package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.mod;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

/**
 * <p>Mod class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Mod extends AbstractBinaryOperation {
	/**
	 * <p>Constructor for Mod.</p>
	 *
	 * @param left a {@link ru.asl.api.expression.CommonExpression} object
	 * @param right a {@link ru.asl.api.expression.CommonExpression} object
	 */
	public Mod(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Mod);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return mod(x,y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 2;
	}
}
