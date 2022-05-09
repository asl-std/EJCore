package ru.asl.api.expression.exceptions;

public class DivideByZeroException extends ArithmeticException {
	public DivideByZeroException() {
		super("Division by zero");
	}
}
