package org.aslstd.api.bukkit.property.binding;

import org.aslstd.api.bukkit.property.writeable.WriteableDouble;
import org.aslstd.api.bukkit.property.writeable.WriteableString;

/**
 * <p>BindingHelper class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BindingHelper {

	/**
	 * <p>bind.</p>
	 *
	 * @param parent a {@link org.aslstd.api.bukkit.property.writeable.WriteableDouble} object
	 * @param childs a {@link org.aslstd.api.bukkit.property.writeable.WriteableDouble} object
	 */
	public static void bind(WriteableDouble parent, WriteableDouble... childs) {
		if (parent == null)
			throw new IllegalArgumentException("Writeable parent object cannot be null");

		for (final WriteableDouble child : childs)
			if (child != null)
				parent.bind(child);
	}

	/**
	 * <p>bind.</p>
	 *
	 * @param parent a {@link org.aslstd.api.bukkit.property.writeable.WriteableString} object
	 * @param childs a {@link org.aslstd.api.bukkit.property.writeable.WriteableString} object
	 */
	public static void bind(WriteableString parent, WriteableString... childs) {
		if (parent == null)
			throw new IllegalArgumentException("Writeable parent object cannot be null");

		for (final WriteableString child : childs)
			if (child != null)
				parent.bind(child);
	}

}
