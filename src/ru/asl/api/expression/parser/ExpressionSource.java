package ru.asl.api.expression.parser;

public interface ExpressionSource {
	boolean hasNext();
	char next();
	ExpressionException error(final String message);
	int getPos();
	int nextVariableNum();
}
