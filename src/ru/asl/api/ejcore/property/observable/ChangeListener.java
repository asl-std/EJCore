package ru.asl.api.ejcore.property.observable;

public interface ChangeListener<T> {

	void changed(ObservableObject<? extends T> observable, T oldValue, T newValue);

}
