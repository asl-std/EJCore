package ru.aslcraft.api.ejcore.property.observable;

/**
 * <p>ObservableObject interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface ObservableObject<T> {

	/**
	 * <p>addListener.</p>
	 *
	 * @param listener a {@link ru.aslcraft.api.ejcore.property.observable.ChangeListener} object
	 */
	void addListener(ChangeListener<? extends ObservableObject<T>> listener);

	/**
	 * <p>removeListener.</p>
	 *
	 * @param listener a {@link ru.aslcraft.api.ejcore.property.observable.ChangeListener} object
	 */
	void removeListener(ChangeListener<? extends ObservableObject<T>> listener);

	/**
	 * <p>get.</p>
	 *
	 * @return a T object
	 */
	T get();

}
