package ru.asl.api.ejcore.property.binding;

import ru.asl.api.ejcore.property.writeable.WriteableObject;

public interface Bindable<T> {

	void bind(WriteableObject<T> to);

	void unbind(WriteableObject<T> from);

}
