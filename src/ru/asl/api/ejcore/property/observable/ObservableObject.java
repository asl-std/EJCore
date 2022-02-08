package ru.asl.api.ejcore.property.observable;

public interface ObservableObject<T> {

	void addListener(ChangeListener<? extends ObservableObject<T>> listener);

	void removeListener(ChangeListener<? extends ObservableObject<T>> listener);

	T get();

}
