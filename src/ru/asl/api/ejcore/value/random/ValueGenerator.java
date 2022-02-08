package ru.asl.api.ejcore.value.random;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.asl.api.ejcore.value.abstrakt.ModifierType;
import ru.asl.api.ejcore.value.util.ValueUtil;

public class ValueGenerator {

	private static final Pattern singlePattern = Pattern.compile("\\s*(chance\\s*([0-9]*[.,]?[0-9]+)%\\s*)?from\\s*([-+]?([0-9]*[.,]?[0-9]+%?))\\s*to\\s*((([-+]?([0-9]*[.,]?[0-9]+%?))))\\s*((per\\s*level\\s*([-+]?([0-9]*[.,]?[0-9]+%?))))?\\s*");
	private static final Pattern rangePattern  = Pattern.compile("\\s*(chance\\s*([0-9]*[.,]?[0-9]+)%\\s*)?from\\s*([-+]?([0-9]*[.,]?[0-9]+)(-([0-9]*[.,]?[0-9]+)))\\s*to\\s*([-+]?([0-9]*[.,]?[0-9]+)(-([0-9]*[.,]?[0-9]+)))\\s*(per\\s*level\\s*([-+]?([0-9]*[.,]?[0-9]+)(-([0-9]*[.,]?[0-9]+))))?\\s*");

	public static RandomValue getRandomHolder(String from) {
		RandomValue val = getSingleValue(from);

		if (val == null)
			val = getRangeValue(from);

		return val;
	}

	public static RandomSingleValue getSingleValue(String from) {
		final Matcher match = singlePattern.matcher(from.toLowerCase());

		if (!match.find()) return null;
		final String[] params = new String[] { match.group(2), match.group(3), match.group(7), match.group(11) };

		final double chance = params[0] != null ? ValueUtil.parseDouble(params[0]) : 100d;
		final String firstValue = params[1];
		final String secondValue = params[2];
		final double perLevelValue = params[3] != null ? ValueUtil.parseDouble(params[3]) : 0d;

		final RandomSingleValue result = new RandomSingleValue(chance, ValueUtil.parseDouble(firstValue.replaceAll("%", "")), ValueUtil.parseDouble(secondValue.replaceAll("%", "")), perLevelValue, ModifierType.getFromValue(firstValue));

		return result;
	}

	public static RandomRangeValue getRangeValue(String from) {
		final Matcher match = rangePattern.matcher(from.toLowerCase());

		if (!match.find()) return null;
		final String[] params = new String[] { match.group(2), match.group(3), match.group(7), match.group(12) };

		final double chance = params[0] != null ? ValueUtil.parseDouble(params[0]) : 100;
		final String firstValue = params[1];
		final String secondValue = params[2];
		final String perLevelValue = params[3];

		if (perLevelValue != null && firstValue.split("-").length < 1) throw new IllegalArgumentException("Incorrect FIRST RANGE argument: " + from);

		if (perLevelValue != null && secondValue.split("-").length < 1) throw new IllegalArgumentException("Incorrect SECOND RANGE argument: " + from);

		if (perLevelValue != null && perLevelValue.split("-").length < 1) throw new IllegalArgumentException("Incorrect PER LEVEL RANGE argument" + from);

		final RandomRangeValue result = new RandomRangeValue(chance, firstValue, secondValue, perLevelValue, ModifierType.getFromValue(firstValue));

		return result;
	}

	public static boolean isTrue(double value, int to) {
		final int b = new Random().nextInt(to);
		return value >= b;
	}

}
