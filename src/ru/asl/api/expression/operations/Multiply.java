package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.multiply;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

public class Multiply extends AbstractBinaryOperation {
	public Multiply(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Mult);
	}

	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return multiply(x,y);
	}

	@Override
	public int getPriority() {
		return 2;
	}
}
