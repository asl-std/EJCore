package ru.asl.api.varsystem.tags;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.value.StringSettings;
import ru.asl.api.varsystem.reader.VarReader;

public class Tag extends ArrayList<String> {

	private static final long serialVersionUID = 1L;

	@Getter private String key;

	@Getter private Class<?> clazz = null;

	@Getter private boolean initSubDirectories = false;

	@Getter private TagType type = TagType.DEFAULT;

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

	public Tag(String key, String... elements) {
		this(key);

		for (final String elem : elements) {
			this.add(elem);
		}
	}

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

	@Override
	public String get(int index) {
		if (type == TagType.DEFAULT)
			return super.get(index);
		else {
			EText.warn("You can't get value from ReturnString Tag type, use #get(String) instead");
			return null;
		}
	}

	@Override
	public boolean add(String e) {
		final boolean ret = super.add(e);

		return ret;
	}

	@Override
	public void add(int index, String element) {
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		final boolean ret = super.addAll(c);

		return ret;
	}

	@Override
	public boolean addAll(int index, Collection<? extends String> c) {
		final boolean ret = super.addAll(index, c);

		return ret;
	}

}
