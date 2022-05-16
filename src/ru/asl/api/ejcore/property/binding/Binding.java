package ru.asl.api.ejcore.property.binding;

import ru.asl.api.ejcore.property.observable.ObservableList;
import ru.asl.api.ejcore.property.writeable.WriteableObject;

/**
 * <p>Binding interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Binding<T extends WriteableObject<T>> {

	/**
	 * <p>getDependencies.</p>
	 *
	 * @return a {@link ru.asl.api.ejcore.property.observable.ObservableList} object
	 */
	ObservableList<T> getDependencies();

}
