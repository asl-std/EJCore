package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.mod;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;
import ru.aslcraft.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
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
