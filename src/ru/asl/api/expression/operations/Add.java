package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.add;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

public class Add extends AbstractBinaryOperation {
	public Add(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Add);
	}

	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return add(x, y);
	}

	@Override
	public int getPriority() {
		return 1;
	}
}
