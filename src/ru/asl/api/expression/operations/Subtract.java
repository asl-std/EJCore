package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.subtract;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

public class Subtract extends AbstractBinaryOperation {
	public Subtract(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Sub);
	}

	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return subtract(x, y);
	}

	@Override
	public int getPriority() {
		return 1;
	}
}
