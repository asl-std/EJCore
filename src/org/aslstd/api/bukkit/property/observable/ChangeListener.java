package org.aslstd.api.bukkit.property.observable;

/**
 * <p>ChangeListener interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface ChangeListener<T> {

	/**
	 * <p>changed.</p>
	 *
	 * @param observable a {@link org.aslstd.api.bukkit.property.observable.ObservableObject} object
	 * @param oldValue a T object
	 * @param newValue a T object
	 */
	void changed(ObservableObject<? extends T> observable, T oldValue, T newValue);

}
