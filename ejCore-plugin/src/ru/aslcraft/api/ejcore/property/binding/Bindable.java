package ru.aslcraft.api.ejcore.property.binding;

import ru.aslcraft.api.ejcore.property.writeable.WriteableObject;

/**
 * <p>Bindable interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Bindable<T> {

	/**
	 * <p>bind.</p>
	 *
	 * @param to a {@link ru.aslcraft.api.ejcore.property.writeable.WriteableObject} object
	 */
	void bind(WriteableObject<T> to);

	/**
	 * <p>unbind.</p>
	 *
	 * @param from a {@link ru.aslcraft.api.ejcore.property.writeable.WriteableObject} object
	 */
	void unbind(WriteableObject<T> from);

}
