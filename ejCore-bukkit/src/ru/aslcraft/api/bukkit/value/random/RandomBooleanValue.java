package ru.aslcraft.api.bukkit.value.random;

import ru.aslcraft.api.bukkit.value.util.MathUtil;

/**
 * <p>RandomBooleanValue class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class RandomBooleanValue implements RandomValue {

	/** {@inheritDoc} */
	@Override
	public Value roll(double lvl) {
		return new Value(String.valueOf(MathUtil.randomBoolean()));
	}

}
