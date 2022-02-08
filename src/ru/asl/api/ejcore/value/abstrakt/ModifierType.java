package ru.asl.api.ejcore.value.abstrakt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ModifierType {
	POSITIVE,
	POSITIVE_PERCENTS,

	NEGATIVE,
	NEGATIVE_PERCENTS;

	public static final Pattern check = Pattern.compile("^([-+]?)(\\d*\\.?\\d*)\\-?\\d*\\.?\\d*([%]?)$");

	public static Matcher matcher;

	public static ModifierType getFromValue(String val) {
		boolean positive, isPercents;

		matcher = check.matcher(val);

		if (matcher.find()) {
			String[] params = new String[3];
			for (int i = 0 ; i < params.length ; i++)
				params[i] = matcher.group(i+1);

			positive = (params[0].equalsIgnoreCase("-") ? false : true);
			isPercents = (params[2].equalsIgnoreCase("%") ? true : false);

			if (positive) {
				if (isPercents) return POSITIVE_PERCENTS;
				return POSITIVE;
			} else {
				if (isPercents) return NEGATIVE_PERCENTS;
				return NEGATIVE;
			}
		}

		throw new NumberFormatException("Can't get number type from value: " + val);
	}

	public static ModifierType getFromValue(double value, boolean percents) {
		if (value > 0)
			if (percents)
				return POSITIVE_PERCENTS;
			else
				return POSITIVE;
		else
			if (percents)
				return NEGATIVE_PERCENTS;
			else
				return NEGATIVE;
	}

	public boolean isNegative() {
		return this == NEGATIVE || this == NEGATIVE_PERCENTS;
	}

	public boolean isPositive() {
		return this == POSITIVE || this == POSITIVE_PERCENTS;
	}

	public boolean isPercents() {
		return this == POSITIVE_PERCENTS || this == ModifierType.NEGATIVE_PERCENTS;
	}

	public ModifierType reverse() {
		switch(this) {
			case NEGATIVE:
				return POSITIVE;
			case NEGATIVE_PERCENTS:
				return POSITIVE_PERCENTS;
			case POSITIVE:
				return NEGATIVE;
			case POSITIVE_PERCENTS:
				return NEGATIVE_PERCENTS;
		}

		return POSITIVE; // UNREACHABLE
	}
}
