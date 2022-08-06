package ru.aslcraft.api.expression;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>EnumUnaryOperation class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@SuppressWarnings("serial")
public enum EnumUnaryOperation {
	UnMinus, Undefined;

	/** Constant <code>stringByOp</code> */
	public static Map<EnumUnaryOperation, String> stringByOp = new HashMap<EnumUnaryOperation, String>() {{
		put(UnMinus,   "-");
		put(Undefined, "");
	}};

	/** Constant <code>opByString</code> */
	public static Map<String, EnumUnaryOperation> opByString = new HashMap<String, EnumUnaryOperation>() {{
		put("-",     UnMinus);
		put("",      Undefined);
	}};

	/**
	 * <p>Getter for the field <code>opByString</code>.</p>
	 *
	 * @param op a {@link java.lang.String} object
	 * @return a {@link ru.aslcraft.api.expression.EnumUnaryOperation} object
	 */
	public static EnumUnaryOperation getOpByString(final String op) {
		return opByString.getOrDefault(op, Undefined);
	}

	/**
	 * <p>Getter for the field <code>stringByOp</code>.</p>
	 *
	 * @param op a {@link ru.aslcraft.api.expression.EnumUnaryOperation} object
	 * @return a {@link java.lang.String} object
	 */
	public static String getStringByOp(final EnumUnaryOperation op) {
		return stringByOp.get(op);
	}
}
