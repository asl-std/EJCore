package ru.asl.api.ejcore.value;

public class ValueParser {

	public static Long parseLong(String value) {
		try {
			return new Long(Long.parseLong(value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new Long(0L);
		}
	}

	public static Double parseDouble(String value) {
		try {
			return new Double(Double.parseDouble(value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new Double(0.D);
		}
	}

	public static Integer parseInteger(String value) {
		Long req = ValueParser.parseLong(value);

		return new Integer((req < Integer.MIN_VALUE ? Integer.MIN_VALUE :
							req > Integer.MAX_VALUE ? Integer.MAX_VALUE :
							req.intValue()));
	}

	public static Short parseShort(String value) {
		Long req = ValueParser.parseLong(value);

		return new Short((req < Short.MIN_VALUE ? Short.MIN_VALUE :
						  req > Short.MAX_VALUE ? Short.MAX_VALUE :
						  req.shortValue()));
	}

	public static Float parseFloat(String value) {
		Double req = ValueParser.parseDouble(value);

		return new Float((req < Float.MIN_VALUE ? Float.MIN_VALUE :
						  req > Float.MAX_VALUE ? Float.MAX_VALUE :
						  req.floatValue()));
	}

}
