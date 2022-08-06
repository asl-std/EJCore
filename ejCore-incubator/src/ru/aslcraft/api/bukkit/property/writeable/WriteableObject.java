package ru.aslcraft.api.bukkit.property.writeable;

import ru.aslcraft.api.bukkit.property.binding.Bindable;
import ru.aslcraft.api.bukkit.property.observable.ObservableObject;

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