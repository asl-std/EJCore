package ru.asl.api.expression.operations;

import java.util.Arrays;
import java.util.List;

import ru.asl.api.expression.CommonExpression;

/**
 * <p>Variable class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Variable implements CommonExpression {
	private final String name;
	private final int num;
	/**
	 * <p>Constructor for Variable.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 * @param num a int
	 */
	public Variable(final String name, int num) {
		this.name = name;
		this.num = num;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name;
	}

	/** {@inheritDoc} */
	@Override
	public int getPriority() {
		return 99;
	}

	/** {@inheritDoc} */
	@Override
	public double evaluate(double ...args) {
		return args[num];
	}

	/** {@inheritDoc} */
	@Override
	public List<String> getVariables() {
		return Arrays.asList(name);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object b) {
		if (b == this) {
			return true;
		}
		if (b == null || b.getClass() != this.getClass()) {
			return false;
		}
		return name.equals(((Variable) b).name);
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
