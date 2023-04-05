package org.aslstd.api.bukkit.property.binding;

import org.aslstd.api.bukkit.property.writeable.WriteableObject;

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
	 * @param to a {@link org.aslstd.api.bukkit.property.writeable.WriteableObject} object
	 */
	void bind(WriteableObject<T> to);

	/**
	 * <p>unbind.</p>
	 *
	 * @param from a {@link org.aslstd.api.bukkit.property.writeable.WriteableObject} object
	 */
	void unbind(WriteableObject<T> from);

}
