package ru.asl.api.ejcore.property.binding;

import ru.asl.api.ejcore.property.observable.ObservableList;
import ru.asl.api.ejcore.property.writeable.WriteableObject;

public interface Binding<T extends WriteableObject<T>> {

	ObservableList<T> getDependencies();

}
