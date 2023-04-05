package org.aslstd.api.bukkit.value.random;

import org.aslstd.api.bukkit.value.Value;

/**
 * <p>RandomValue interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface RandomVal {

	/**
	 * <p>roll.</p>
	 *
	 * @param lvl a double
	 * @return a {@link ru.aslcraft.api.ejcore.value.random.Value} object
	 */
	Value roll(double lvl);

}
