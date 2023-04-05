package org.aslstd.api.bukkit.property.binding;

import org.aslstd.api.bukkit.property.observable.ObservableList;
import org.aslstd.api.bukkit.property.writeable.WriteableObject;

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
	 * @return a {@link org.aslstd.api.bukkit.property.observable.ObservableList} object
	 */
	ObservableList<T> getDependencies();

}
