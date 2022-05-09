package ru.asl.api.expression.operations;

import static ru.asl.api.expression.DoubleOperations.divide;

import ru.asl.api.expression.AbstractBinaryOperation;
import ru.asl.api.expression.CommonExpression;
import ru.asl.api.expression.EnumBinaryOperation;
import ru.asl.api.expression.exceptions.DivideByZeroException;
import ru.asl.api.expression.exceptions.OverflowException;

public class Divide extends AbstractBinaryOperation {

	public Divide(CommonExpression left, CommonExpression right) {
		super(left, right, EnumBinaryOperation.Div);
	}

	@Override
	public double doubleCalculate(double x, double y) throws OverflowException, DivideByZeroException {
		return divide(x,y);
	}

	@Override
	public int getPriority() {
		return 2;
	}

}
