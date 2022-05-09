package ru.asl.api.expression;
import java.util.ArrayList;
import java.util.List;

import ru.asl.api.expression.exceptions.ParsingException;

public abstract class AbstractUnaryOperation implements CommonExpression {
	private final EnumUnaryOperation op;
	private final CommonExpression expr;
	private final List<String> variables;

	public AbstractUnaryOperation(final CommonExpression expr, final EnumUnaryOperation op) {
		this.expr = expr;
		this.op = op;
		variables = expr.getVariables();
	}

	@Override
	public List<String> getVariables() {
		return new ArrayList<>(variables);
	}
	public abstract double doubleCalculate(double x) throws ArithmeticException;

	public String getOperation() {
		return EnumUnaryOperation.getStringByOp(op);
	}

	@Override
	public abstract int getPriority();

	@Override
	public String toString() {
		//return toMiniString();
		return "(" + getOperation() + expr.toString() + ")";
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
		sb.append(getOperation());
		sb.append(getBrackets(expr.toMiniString(), getPriority() > expr.getPriority()));
		return sb.toString();
	}

	@Override
	public double evaluate(double...args) throws ArithmeticException, ParsingException {
		return doubleCalculate(expr.evaluate(args));
	}

	@Override
	public boolean equals(Object b) {
		if (b == this) {
			return true;
		}
		if (b == null || b.getClass() != this.getClass()) {
			return false;
		}
		return expr.equals(((AbstractUnaryOperation) b).expr)
				&&  op == ((AbstractUnaryOperation) b).op;

	}

	@Override
	public int hashCode() {
		return expr.hashCode() + 31 * getOperation().hashCode();
	}
}
