package org.aslstd.api.attributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.NamespacedKey;

/**
 * <p>WeaponAttributesManager class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class AttrManager {
	private static final Map<NamespacedKey, AttrBase> attributes = new ConcurrentHashMap<>();

	/**
	 * <p>getRegistered.</p>
	 *
	 * @return a {@link java.util.Collection} object
	 */
	public static final Collection<AttrBase> getRegistered() { return attributes.values(); }

	private static List<AttrBase> sortedList = new ArrayList<>();

	private static int size = -1;

	/**
	 * <p>Getter for the field <code>sortedList</code>.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public static final  List<AttrBase> getSortedList() {
		if (size == attributes.size()) { return sortedList; }

		final ArrayList<AttrBase> list = new ArrayList<>(attributes.values());

		list.sort(Comparator.comparingInt(AttrBase::getPriority));

		sortedList = list;
		size = attributes.size();
		return sortedList;
	}

	/**
	 * <p>register.</p>
	 *
	 * @param attr a {@link org.aslstd.api.attributes.AttrBase} object
	 */
	public static final void register(AttrBase attr) {
		if (attr != null && !attributes.containsKey(attr.getKey())) {
			attr.setUniquePosition(attributes.size());
			attributes.put(attr.getKey(), attr);
		}
	}

	public static final void register() {
		if (!attributes.isEmpty()) { return; }
		int pos = 0;
		for (final Field f : AttrManager.class.getFields()) {
			if (!f.trySetAccessible()) continue;

			if (AttrBase.class.isAssignableFrom(f.getType())) {
				try {
					register((AttrBase) f.get(null));
					((AttrBase) f.get(null)).setPriority(pos);
					pos++;
				} catch (final Exception e) { e.printStackTrace(); }
			}
		}
	}

	/**
	 * <p>getByKey.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a {@link org.aslstd.api.attributes.AttrBase} object
	 */
	public static final AttrBase getByKey(NamespacedKey key) {
		if (attributes.containsKey(key))
			return attributes.get(key);

		return null;
	}

	/**
	 * <p>reloadAttributes.</p>
	 */
	public static final void reloadAttributes() {
		for (final AttrBase attr : getRegistered()) {
			attr.initCustomSettings();

			attr.getVisualName();
			attr.getCostValue();
			attr.initializeBasicValues();
		}
	}

}
