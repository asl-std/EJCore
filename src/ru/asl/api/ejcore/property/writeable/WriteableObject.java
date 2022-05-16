package ru.asl.api.ejcore.property.writeable;

import ru.asl.api.ejcore.property.binding.Bindable;
import ru.asl.api.ejcore.property.observable.ObservableObject;

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
