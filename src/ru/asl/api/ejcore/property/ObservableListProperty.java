package ru.asl.api.ejcore.property;

import java.util.ArrayList;

import ru.asl.api.ejcore.property.observable.ChangeListener;
import ru.asl.api.ejcore.property.observable.ObservableList;
import ru.asl.api.ejcore.property.observable.ObservableObject;

public class ObservableListProperty<T> extends ArrayList<T> implements ObservableList<T> {
	private static final long serialVersionUID = 1L;

	@Override
	public void addListener(ChangeListener<? extends ObservableObject<T>> listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(ChangeListener<? extends ObservableObject<T>> listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public T get() {
		return super.get(0);
	}

}
