package org.aslstd.api.bukkit.value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * <p>ValuePair class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@RequiredArgsConstructor
@NoArgsConstructor
public class ValuePair<T> {

	@Getter @Setter @NonNull public String key;

	@Getter @Setter public ModifierType type = ModifierType.POSITIVE;

	@Getter @Setter @NonNull public T first, second;

	public ValuePair<T> swap() {
		final T cache = first;
		first = second;
		second = cache;
		return this;
	}

	public static <V> ValuePair<V> of(V first, V second) {
		return new ValuePair<>(null, first, second);
	}

	public static <V> ValuePair<V> of(String key, V first, V second) {
		return new ValuePair<>(key, first, second);
	}

}
