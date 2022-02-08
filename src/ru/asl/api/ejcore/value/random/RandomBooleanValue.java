package ru.asl.api.ejcore.value.random;

import ru.asl.api.ejcore.value.util.MathUtil;

public class RandomBooleanValue implements RandomValue {

	@Override
	public Value roll(double lvl) {
		return new Value(String.valueOf(MathUtil.randomBoolean()));
	}

}
