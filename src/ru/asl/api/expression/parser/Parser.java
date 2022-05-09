package ru.asl.api.expression.parser;

import ru.asl.api.expression.Expression;
import ru.asl.api.expression.exceptions.ParsingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
	Expression parse(String expression) throws ParsingException;
}
