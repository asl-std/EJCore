package ru.asl.api.ejcore.value.util;

import java.util.Random;

public class ValueUtil {

	public static boolean isNegative(String value) {
		if (isNumber(value) && Double.parseDouble(value) < 0) return true;

		if (value.startsWith("-")) return true;
		return false;
	}

	public static boolean isNumber(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	public static boolean isTrue(double chance, int random) {
		return chance >= new Random().nextInt(random);
	}

	public static boolean isString(String value) {
		return !isNumber(value);
	}

	public static boolean isPercent(String value) {
		if (isString(value)) return value.contains("%");
		return false;
	}

	public static Long parseLong(String value) {
		try {
			return new Long(Long.parseLong(value));
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			return new Long(0L);
		}
	}

	public static Double parseDouble(String value) {
		try {
			return new Double(Double.parseDouble(value));
		} catch (final NumberFormatException e) {
			e.printStackTrace();
			return new Double(0.D);
		}
	}

	public static double[] parseDouble(String... values) {
		if (values.length < 1 || values[0] == null || !isNumber(values[0])) return new double[] { 0d };
		final double[] result = new double[values.length];

		for (int i = 0 ; i < values.length ; i++) {
			result[i] = parseDouble(values[i]);
		}

		return result;
	}

	public static Integer parseInteger(String value) {
		final Long req = parseLong(value);

		return new Integer((req < Integer.MIN_VALUE ? Integer.MIN_VALUE :
			req > Integer.MAX_VALUE ? Integer.MAX_VALUE :
				req.intValue()));
	}

	public static Short parseShort(String value) {
		final Integer req = parseInteger(value);

		return new Short((req < Short.MIN_VALUE ? Short.MIN_VALUE :
			req > Short.MAX_VALUE ? Short.MAX_VALUE :
				req.shortValue()));
	}

	public static Float parseFloat(String value) {
		final Double req = parseDouble(value);

		return new Float((req < Float.MIN_VALUE ? Float.MIN_VALUE :
			req > Float.MAX_VALUE ? Float.MAX_VALUE :
				req.floatValue()));
	}

}
