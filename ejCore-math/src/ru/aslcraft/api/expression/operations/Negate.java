package ru.aslcraft.api.expression.operations;

import static ru.aslcraft.api.expression.DoubleOperations.negate;

import ru.aslcraft.api.expression.AbstractUnaryOperation;
import ru.aslcraft.api.expression.CommonExpression;
import ru.aslcraft.api.expression.EnumUnaryOperation;
import ru.aslcraft.api.expression.exceptions.OverflowException;

/**
 * <p>Negate class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Negate extends AbstractUnaryOperation {
	/**
	 * <p>Constructor for Negate.</p>
	 *
	 * @param expr a {@link ru.aslcraft.api.expression.CommonExpression} object
	 */
	public Negate(CommonExpression expr) {
		super(expr, EnumUnaryOperation.UnMinus);
	}

	/** {@inheritDoc} */
	@Override
	public double doubleCalculate(double x) throws OverflowException {
		return negate(x);
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 4;
	}
}
