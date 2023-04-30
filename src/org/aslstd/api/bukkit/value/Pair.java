package org.aslstd.api.bukkit.value;

import org.jetbrains.annotations.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Pair class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public class Pair<T1, T2> {

	@Getter @Setter @Nullable private T1 first;

	@Getter @Setter @Nullable private T2 second;

}
