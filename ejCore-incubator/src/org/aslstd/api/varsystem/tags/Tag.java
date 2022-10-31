package org.aslstd.api.varsystem.tags;

import java.util.ArrayList;
import java.util.Collection;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.bukkit.value.StringSettings;
import org.aslstd.api.varsystem.reader.VarReader;

import lombok.Getter;

/**
 * <p>Tag class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Tag extends ArrayList<String> {

	private static final long serialVersionUID = 1L;

	@Getter private String key;

	@Getter private Class<?> clazz = null;

	@Getter private boolean initSubDirectories = false;

	@Getter private TagType type = TagType.DEFAULT;

	/**
	 * <p>Constructor for Tag.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 */
	public Tag(String key) {
		this.key = key.indexOf("{") == -1 ? key : key.substring(0, key.indexOf("{"));

		final StringSettings sts = VarReader.readLine(key);

		if (sts.hasKey("class"))
			try {
				clazz = Class.forName(sts.getValue("class"));
			} catch (final ClassNotFoundException e) {
				EText.warn("Can't receive class from tag " + key + ", please correct the class way");
			}

		if (sts.hasKey("initSubDirectories")) {
			final String isd = sts.getValue("initSubDirectories");

			initSubDirectories = isd.equalsIgnoreCase("true");
		}

	}

	/**
	 * <p>Constructor for Tag.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @param elements a {@link java.lang.String} object
	 */
	public Tag(String key, String... elements) {
		this(key);

		for (final String elem : elements) {
			this.add(elem);
		}
	}

	/**
	 * <p>get.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a {@link java.lang.String} object
	 */
	public String get(String key) {
		if (type == TagType.DEFAULT) {
			for (final String search : this.toArray(new String[size()])) {
				final String prepaired = search.indexOf("{") == -1 ? search : search.substring(0, key.indexOf("{"));

				if (prepaired.equalsIgnoreCase(key))
					return search;
			}
			return null;
		} else {
			EText.warn("You can't get value from ReturnString Tag type, use #get(String) instead");
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String get(int index) {
		if (type == TagType.DEFAULT)
			return super.get(index);
		else {
			EText.warn("You can't get value from ReturnString Tag type, use #get(String) instead");
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(String e) {
		final boolean ret = super.add(e);

		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public void add(int index, String element) {
		super.add(index, element);
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(Collection<? extends String> c) {
		final boolean ret = super.addAll(c);

		return ret;
	}

	/** {@inheritDoc} */
	@Override
	public boolean addAll(int index, Collection<? extends String> c) {
		final boolean ret = super.addAll(index, c);

		return ret;
	}

}
