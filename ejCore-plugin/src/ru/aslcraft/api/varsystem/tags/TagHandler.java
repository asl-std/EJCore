package ru.aslcraft.api.varsystem.tags;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.aslcraft.api.ejcore.yaml.YAML;
import ru.aslcraft.core.Core;

/**
 * <p>TagHandler class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class TagHandler {

	/** Constant <code>file</code> */
	public static final YAML file = new YAML(Core.instance().getDataFolder() + "/tags.yml");

	/** Constant <code>tags</code> */
	public static ConcurrentMap<String,Tag> tags = new ConcurrentHashMap<>();

	/**
	 * <p>init.</p>
	 */
	public static void init() {
		for (final String key : file.getKeys(false)) {
			final List<String> array = file.getStringList(key);
			if (array == null || array.isEmpty())
				continue;
			String prepaired = key.replaceAll("\\s", "");
			if (key.indexOf("{") != -1)
				prepaired = key.substring(0, key.indexOf("{") - 1);

			tags.put(prepaired, new Tag(key, array.toArray(new String[array.size()])));
		}
	}

}
