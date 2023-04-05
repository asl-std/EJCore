package org.aslstd.api.bukkit.value.random;

import org.aslstd.api.bukkit.value.Value;
import org.aslstd.api.bukkit.value.util.MathUtil;

/**
 * <p>RandomBooleanValue class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class RandomBool implements RandomVal {

	/** {@inheritDoc} */
	@Override
	public Value roll(double lvl) {
		return new Value(String.valueOf(MathUtil.randomBoolean()));
	}

}
