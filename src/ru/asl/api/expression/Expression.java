package ru.asl.api.expression;

import java.util.List;

import ru.asl.api.expression.exceptions.DivideByZeroException;
import ru.asl.api.expression.exceptions.OverflowException;
import ru.asl.api.expression.exceptions.ParsingException;

public interface Expression extends ToMiniString {
	double evaluate(double ...args) throws OverflowException, DivideByZeroException, ParsingException;
	List<String> getVariables();

}
