package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.negate;

import ru.asl.api.expression.AbstractUnaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumUnaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

public class Negate extends AbstractUnaryOperation {
	public Negate(CommonExpression expr) {
		super(expr, EnumUnaryOperation.UnMinus);
	}

	@Override
	public double doubleCalculate(double x) throws OverflowException {
		return negate(x);
	}

	@Override
	public int getPriority() {
		return 4;
	}
}
