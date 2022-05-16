package ru.asl.api.ejcore.property.observable;

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
	 * @param observable a {@link ru.asl.api.ejcore.property.observable.ObservableObject} object
	 * @param oldValue a T object
	 * @param newValue a T object
	 */
	void changed(ObservableObject<? extends T> observable, T oldValue, T newValue);

}
