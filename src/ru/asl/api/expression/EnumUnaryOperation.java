package ru.asl.api.expression;

import java.util.HashMap;
import java.util.Map;

public enum EnumUnaryOperation {
	UnMinus, Undefined;

	public static Map<EnumUnaryOperation, String> stringByOp = new HashMap<EnumUnaryOperation, String>() {{
		put(UnMinus,   "-");
		put(Undefined, "");
	}};

	public static Map<String, EnumUnaryOperation> opByString = new HashMap<String, EnumUnaryOperation>() {{
		put("-",     UnMinus);
		put("",      Undefined);
	}};

	public static EnumUnaryOperation getOpByString(final String op) {
		return opByString.getOrDefault(op, Undefined);
	}

	public static String getStringByOp(final EnumUnaryOperation op) {
		return stringByOp.get(op);
	}
}
