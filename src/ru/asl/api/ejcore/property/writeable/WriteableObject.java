package ru.asl.api.ejcore.property.writeable;

import ru.asl.api.ejcore.property.binding.Bindable;
import ru.asl.api.ejcore.property.observable.ObservableObject;

public interface WriteableObject<T> extends ObservableObject<T>, Bindable<T> {

	void set(T value);

}
