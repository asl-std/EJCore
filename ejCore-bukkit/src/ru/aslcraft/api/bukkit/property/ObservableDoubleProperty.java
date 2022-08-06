package ru.aslcraft.api.bukkit.property;

import ru.aslcraft.api.bukkit.property.observable.ChangeListener;
import ru.aslcraft.api.bukkit.property.observable.ObservableDouble;
import ru.aslcraft.api.bukkit.property.observable.ObservableObject;

/**
 * <p>ObservableDoubleProperty class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ObservableDoubleProperty implements ObservableDouble {

	/** {@inheritDoc} */
	@Override
	public void addListener(ChangeListener<? extends ObservableObject<Double>> listener) {

	}

	/** {@inheritDoc} */
	@Override
	public void removeListener(ChangeListener<? extends ObservableObject<Double>> listener) {
	}

	/** {@inheritDoc} */
	@Override
	public Double get() {
		return null;
	}

}
