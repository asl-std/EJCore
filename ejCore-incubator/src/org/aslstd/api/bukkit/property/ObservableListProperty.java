package org.aslstd.api.bukkit.property;

import java.util.ArrayList;

import org.aslstd.api.bukkit.property.observable.ChangeListener;
import org.aslstd.api.bukkit.property.observable.ObservableList;
import org.aslstd.api.bukkit.property.observable.ObservableObject;

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
