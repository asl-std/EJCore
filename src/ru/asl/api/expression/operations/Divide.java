package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.divide;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.DivideByZeroException;
import ru.asl.api.expression.exceptions.OverflowException;

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
	 * @param left a {@link ru.asl.api.expression.CommonExpression} object
	 * @param right a {@link ru.asl.api.expression.CommonExpression} object
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
