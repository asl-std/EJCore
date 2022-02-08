package ru.asl.api.ejcore.value.util;

import java.util.Random;

public class MathUtil {

	public static boolean randomBoolean() {
		int rnd = new Random().nextInt(100);

		if (rnd > 50)
			return true;
		else
			return false;
	}

	public static int getIntRandomRange(int min, int max) {
		return (int) getRandomRange(min, min);
	}

	public static double getRandomRange(double min, double max) {
		if (min == max) return min;

		if (min >= max) {
			double temp = max;
			max = min;
			min = temp;
		}

		return min + (max - min) * new Random().nextDouble();
	}

	public static double incrementByPercents(double value, double inrease) {
		return (value + (value * (inrease / 100)));
	}

	public static double decrementByPercents(double value, double decrement) {
		return (value + (value * (decrement / 100)));
	}

	public static double getPercentsOfValue(double value, double percents) {
		return (value * (percents / 100));
	}



	public static String incrementRangeValue(String value, String increment) {
		String[] incSplit = increment.split("-");

		if (ValueUtil.isNumber(incSplit[0])) {
			if (incSplit.length == 1)
				return incrementRangeValue(value, ValueUtil.parseDouble(incSplit[0]));

			double[] values = ValueUtil.parseDouble(value.split("-"));
			double[] increments = ValueUtil.parseDouble(incSplit);

			for (int i = 0 ; i < values.length && i < increments.length ; i++) {
				values[i] += increments[i];
			}

			return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
		}

		return value;
	}

	public static String incrementRangeByPercents(String value, double percents) {
		double[] values = ValueUtil.parseDouble(value.split("-"));
		if (values.length == 1) return value;

		for (int i = 0 ; i < values.length ; i++)
			values[i] = incrementByPercents(values[i], percents);

		return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
	}

	public static String incrementRangeValue(String value, double increment) {
		double[] values = ValueUtil.parseDouble(value.split("-"));
		if (values.length == 1) return value;

		for (int i = 0 ; i < values.length ; i++)
			values[i] += increment;

		return Math.min(values[0], values[1]) + "-" + Math.max(values[0], values[1]);
	}

}
