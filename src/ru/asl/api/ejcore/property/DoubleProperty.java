package ru.asl.api.ejcore.property;

import ru.asl.api.ejcore.property.observable.ChangeListener;
import ru.asl.api.ejcore.property.observable.ObservableObject;
import ru.asl.api.ejcore.property.writeable.WriteableDouble;
import ru.asl.api.ejcore.property.writeable.WriteableObject;

public class DoubleProperty implements WriteableDouble {

	@Override
	public void set(Double value) {
	}

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

	@Override
	public void bind(WriteableObject<Double> to) {

	}

	@Override
	public void unbind(WriteableObject<Double> from) {

	}

}
