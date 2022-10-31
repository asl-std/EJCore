package org.aslstd.api.bukkit.property;

import org.aslstd.api.bukkit.property.observable.ChangeListener;
import org.aslstd.api.bukkit.property.observable.ObservableDouble;
import org.aslstd.api.bukkit.property.observable.ObservableObject;

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
