package ru.asl.api.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum EnumBinaryOperation {
	Add, Sub, Mult, Div, Mod, Pow, Log, Undefined;

	public static Map<EnumBinaryOperation, String> stringByOp = new HashMap<EnumBinaryOperation, String>() {{
		put(Add,       "+");
		put(Sub,       "-");
		put(Mult,      "*");
		put(Div,       "/");
		put(Mod,       "%");
		put(Pow,       "^");
		put(Log,       "//");
		put(Undefined, "");
	}};

	public static Map<String, EnumBinaryOperation> opByString = new HashMap<String, EnumBinaryOperation>() {{
		put("+",    Add);
		put("-",    Sub);
		put("*",    Mult);
		put("/",    Div);
		put("%",    Mod);
		put("^",    Pow);
		put("//",   Log);
		put("",     Undefined);
	}};

	public static List<Set<EnumBinaryOperation>> levels = Arrays.asList(
			new HashSet<>(Arrays.asList(EnumBinaryOperation.Add, EnumBinaryOperation.Sub)),
			new HashSet<>(Arrays.asList(EnumBinaryOperation.Mult, EnumBinaryOperation.Div, EnumBinaryOperation.Mod)),
			new HashSet<>(Arrays.asList(EnumBinaryOperation.Pow, EnumBinaryOperation.Log))
			);

	public static EnumBinaryOperation getOpByString(final String op) {
		return opByString.getOrDefault(op, Undefined);
	}

	public static String getStringByOp(final EnumBinaryOperation op) {
		return stringByOp.get(op);
	}
}
