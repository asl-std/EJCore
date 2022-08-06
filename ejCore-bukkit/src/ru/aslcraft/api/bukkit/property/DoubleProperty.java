package ru.aslcraft.api.bukkit.property;

import ru.aslcraft.api.bukkit.property.observable.ChangeListener;
import ru.aslcraft.api.bukkit.property.observable.ObservableObject;
import ru.aslcraft.api.bukkit.property.writeable.WriteableDouble;
import ru.aslcraft.api.bukkit.property.writeable.WriteableObject;

/**
 * <p>DoubleProperty class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class DoubleProperty implements WriteableDouble {

	/** {@inheritDoc} */
	@Override
	public void set(Double value) {
	}

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

	/** {@inheritDoc} */
	@Override
	public void bind(WriteableObject<Double> to) {

	}

	/** {@inheritDoc} */
	@Override
	public void unbind(WriteableObject<Double> from) {

	}

}
