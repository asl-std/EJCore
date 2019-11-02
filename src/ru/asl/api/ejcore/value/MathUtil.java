package ru.asl.api.ejcore.value;

import java.util.Random;

public class MathUtil {

	public static double decreasePercent(double value, double decrease) {
		return (value - (value * (decrease / 100)));
	}

	public static int getIntRandomRange(int min, int max) {
		return (int) getRandomRange(Double.valueOf(min), Double.valueOf(min));
	}

	public static double getRandomRange(double min, double max) {
		if (min >= max) return min;
		return min + (max - min) * new Random().nextDouble();
	}

	public static double inreasePercent(double value, double inrease) {
		return (value + (value * (inrease / 100)));
	}

	public static double percentOfValue(double value, double percents) {
		return (value * (percents / 100));
	}

}
