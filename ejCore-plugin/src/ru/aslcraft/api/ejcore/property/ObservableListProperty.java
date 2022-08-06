package ru.aslcraft.api.ejcore.property;

import java.util.ArrayList;

import ru.aslcraft.api.ejcore.property.observable.ChangeListener;
import ru.aslcraft.api.ejcore.property.observable.ObservableList;
import ru.aslcraft.api.ejcore.property.observable.ObservableObject;

/**
 * <p>ObservableListProperty class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ObservableListProperty<T> extends ArrayList<T> implements ObservableList<T> {
	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public void addListener(ChangeListener<? extends ObservableObject<T>> listener) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public void removeListener(ChangeListener<? extends ObservableObject<T>> listener) {
		// TODO Auto-generated method stub

	}

	/** {@inheritDoc} */
	@Override
	public T get() {
		return super.get(0);
	}

}
