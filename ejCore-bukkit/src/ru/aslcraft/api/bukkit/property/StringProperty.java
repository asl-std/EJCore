package ru.aslcraft.api.bukkit.property;

import ru.aslcraft.api.bukkit.property.observable.ChangeListener;
import ru.aslcraft.api.bukkit.property.observable.ObservableObject;
import ru.aslcraft.api.bukkit.property.writeable.WriteableObject;
import ru.aslcraft.api.bukkit.property.writeable.WriteableString;

/**
 * <p>StringProperty class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class StringProperty implements WriteableString {

	/** {@inheritDoc} */
	@Override
	public void set(String value) {

	}

	/** {@inheritDoc} */
	@Override
	public void addListener(ChangeListener<? extends ObservableObject<String>> listener) {

	}

	/** {@inheritDoc} */
	@Override
	public void removeListener(ChangeListener<? extends ObservableObject<String>> listener) {

	}

	/** {@inheritDoc} */
	@Override
	public String get() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void bind(WriteableObject<String> to) {

	}

	/** {@inheritDoc} */
	@Override
	public void unbind(WriteableObject<String> from) {

	}

}
