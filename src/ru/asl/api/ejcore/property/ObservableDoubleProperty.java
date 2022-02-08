package ru.asl.api.ejcore.property;

import ru.asl.api.ejcore.property.observable.ChangeListener;
import ru.asl.api.ejcore.property.observable.ObservableDouble;
import ru.asl.api.ejcore.property.observable.ObservableObject;

public class ObservableDoubleProperty implements ObservableDouble {

	@Override
	public void addListener(ChangeListener<? extends ObservableObject<Double>> listener) {

	}

	@Override
	public void removeListener(ChangeListener<? extends ObservableObject<Double>> listener) {
	}

	@Override
	public Double get() {
		return null;
	}

}
