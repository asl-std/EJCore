package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.divide;

import ru.aslcraft.api.expression.AbstractBinaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumBinaryOperation;
import ru.aslcraft.api.expression.exceptions.DivideByZeroException;
import ru.aslcraft.api.expression.exceptions.OverflowException;

/**
 * <p>Divide class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Divide extends AbstractBinaryOperation {

	/**
	 * <p>Constructor for Divide.</p>
	 *
	 * @param left a {@link ru.aslcraft.api.expression.CommonExpression} object
	 * @param right a {@link ru.aslcraft.api.expression.CommonExpression} object
	 */
	public Divide(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Div);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x, double y) throws OverflowException, DivideByZeroException {
		return divide(x,y);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 2;
	}

}
