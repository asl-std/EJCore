package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.log;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;

public class Log extends AbstractBinaryOperation {
	public Log(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Log);
	}

	@Override
	public double doubleCalculate(double x, double y) throws ArithmeticException {
		return log(x, y);
	}

	@Override
	public int getPriority() {
		return 3;
	}
}
