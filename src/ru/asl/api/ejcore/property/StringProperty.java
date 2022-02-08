package ru.asl.api.ejcore.property;

import ru.asl.api.ejcore.property.observable.ChangeListener;
import ru.asl.api.ejcore.property.observable.ObservableObject;
import ru.asl.api.ejcore.property.writeable.WriteableObject;
import ru.asl.api.ejcore.property.writeable.WriteableString;

public class StringProperty implements WriteableString {

	@Override
	public void set(String value) {

	}

	@Override
	public void addListener(ChangeListener<? extends ObservableObject<String>> listener) {

	}

	@Override
	public void removeListener(ChangeListener<? extends ObservableObject<String>> listener) {

	}

	@Override
	public String get() {
		return null;
	}

	@Override
	public void bind(WriteableObject<String> to) {

	}

	@Override
	public void unbind(WriteableObject<String> from) {

	}

}
