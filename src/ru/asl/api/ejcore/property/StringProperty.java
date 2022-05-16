package ru.asl.api.ejcore.property;

import ru.asl.api.ejcore.property.observable.ChangeListener;
import ru.asl.api.ejcore.property.observable.ObservableObject;
import ru.asl.api.ejcore.property.writeable.WriteableObject;
import ru.asl.api.ejcore.property.writeable.WriteableString;

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
