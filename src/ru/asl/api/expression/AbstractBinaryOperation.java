package ru.asl.api.expression;

import java.util.ArrayList;
import java.util.List;

import ru.asl.api.expression.exceptions.DivideByZeroException;
import ru.asl.api.expression.exceptions.OverflowException;
import ru.asl.api.expression.exceptions.ParsingException;

public abstract class AbstractBinaryOperation implements CommonExpression {
	EnumBinaryOperation op;
	private final CommonExpression left, right;
	private final List<String> variables;
	public AbstractBinaryOperation(final CommonExpression left, final CommonExpression right, final EnumBinaryOperation op) {
		this.left = left;
		this.right = right;
		this.op = op;
		variables = new ArrayList<>();
		variables.addAll(left.getVariables());
		variables.addAll(right.getVariables());
	}

	@Override
	public List<String> getVariables() {
		return new ArrayList<>(variables);
	}

	public abstract double doubleCalculate(double x, double y) throws ArithmeticException;

	public String getOperation() {
		return EnumBinaryOperation.getStringByOp(op);
	}

	@Override
	public abstract int getPriority();

	public boolean isAssocOperation() {
		return op == EnumBinaryOperation.Add || op == EnumBinaryOperation.Mult;
	}

	@Override
	public String toString() {
		//return toMiniString();
		return "(" + left.toString() + getOperation() + right.toString() + ")";
	}

	private boolean checkRight() {
		return right instanceof AbstractBinaryOperation
				&& getPriority() == right.getPriority()
				&& (!isAssocOperation() || !((AbstractBinaryOperation) right).isAssocOperation());
	}

	private static String getBrackets(final String s, final boolean isBrackets) {
		if (isBrackets) {
			return "(" + s + ")";
		} else {
			return s;
		}
	}

	@Override
	public String toMiniString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(getBrackets(left.toMiniString(), getPriority() > left.getPriority()));
		sb.append(getOperation());
		sb.append(getBrackets(right.toMiniString(), getPriority() > right.getPriority() || checkRight()));
		return sb.toString();
	}

	@Override
	public double evaluate(double ...args) throws OverflowException, DivideByZeroException, ParsingException {
		return doubleCalculate(left.evaluate(args), right.evaluate(args));
	}

	@Override
	public boolean equals(Object b) {
		if (b == this) {
			return true;
		}
		if (b == null || b.getClass() != this.getClass()) {
			return false;
		}
		return left.equals(((AbstractBinaryOperation) b).left)
				&&  right.equals(((AbstractBinaryOperation) b).right)
				&&  op == ((AbstractBinaryOperation) b).op;
	}

	@Override
	public int hashCode() {
		return left.hashCode() + 31 * (right.hashCode() +  31 * getOperation().hashCode());
	}
}
