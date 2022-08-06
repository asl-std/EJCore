package ru.aslcraft.api.bukkit.property;

import java.util.ArrayList;

import ru.aslcraft.api.bukkit.property.observable.ChangeListener;
import ru.aslcraft.api.bukkit.property.observable.ObservableObject;
import ru.aslcraft.api.bukkit.property.writeable.WriteableList;

/**
 * <p>ListProperty class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ListProperty<T> extends ArrayList<T> implements WriteableList<T> {
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
