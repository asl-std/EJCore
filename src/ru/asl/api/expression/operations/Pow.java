package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.pow;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

public class Pow extends AbstractBinaryOperation {
	public Pow(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Pow);
	}

	@Override
	public double doubleCalculate(double x, double y) throws OverflowException, ArithmeticException {
		return pow(x, y);
	}

	@Override
	public int getPriority() {
		return 3;
	}
}
