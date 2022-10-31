package org.aslstd.api.bukkit.property.writeable;

import org.aslstd.api.bukkit.property.binding.Bindable;
import org.aslstd.api.bukkit.property.observable.ObservableObject;

/**
 * <p>WriteableObject interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface WriteableObject<T> extends ObservableObject<T>, Bindable<T> {

	/**
	 * <p>set.</p>
	 *
	 * @param value a T object
	 */
	void set(T value);

}
