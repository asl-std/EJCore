package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.mod;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.OverflowException;

public class Mod extends AbstractBinaryOperation {
	public Mod(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Mod);
	}

	@Override
	public double doubleCalculate(double x, double y) throws OverflowException {
		return mod(x,y);
	}

	@Override
	public int getPriority() {
		return 2;
	}
}
