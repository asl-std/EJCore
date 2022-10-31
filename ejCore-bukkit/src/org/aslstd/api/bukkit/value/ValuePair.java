package org.aslstd.api.bukkit.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>ValuePair class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public class ValuePair<T> {

	@Getter @Setter public String key;

	@Getter @Setter public T first, second;

}
