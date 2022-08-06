package ru.aslcraft.api.ejcore.value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.aslcraft.api.ejcore.yaml.YAML;

/**
 * <p>StringSettings class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class StringSettings extends Settings<String> {

	/**
	 * <p>importArray.</p>
	 *
	 * @param array a {@link java.util.List} object
	 * @param key a {@link java.lang.String} object
	 */
	public void importArray(List<? extends Object> array, String key) {
		for (int i = 0; i < array.size(); i++)
			settings.put(String.valueOf(key.toLowerCase()) + "." + i, array.get(i).toString());
	}

	/**
	 * <p>removeArray.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 */
	public void removeArray(String key) {
		for (int i = 0; hasKey(String.valueOf(key) + "." + i); i++)
			remove(String.valueOf(key) + "." + i);
	}

	/**
	 * <p>exportArray.</p>
	 *
	 * @param key a {@link java.lang.String} object
	 * @return a {@link java.util.List} object
	 */
	public List<String> exportArray(String key) {
		final List<String> keys = new ArrayList<>();
		for (int i = 0 ; hasKey(String.valueOf(key) + "." + i); i++)
			keys.add(settings.get(String.valueOf(key) + "." + i));
		return keys;
	}

	/**
	 * <p>importFromYAML.</p>
	 *
	 * @param file a {@link ru.aslcraft.api.ejcore.yaml.YAML} object
	 * @param section a {@link java.lang.String} object
	 */
	public void importFromYAML(YAML file, String section) {
		final Set<String> keys = (section != null && !section.equalsIgnoreCase("")) ? file.getSection(section).getKeys(true)
				: file.getKeys(true);

		if (section == null || section.equalsIgnoreCase(""))
			section = "";
		else
			section = section + ".";

		for (final String key : keys) {
			if (!file.getString(String.valueOf(section) + key).startsWith("MemorySection[path='")
					|| !file.getStringList(String.valueOf(section) + key).isEmpty()) {
				if (!file.getStringList(String.valueOf(section) + key).isEmpty()) {
					importArray(file.getStringList(String.valueOf(section) + key), key);
					continue;
				}
				if (file.getString(String.valueOf(section) + key).equalsIgnoreCase("remove")
						|| file.getString(String.valueOf(section) + key).equalsIgnoreCase("null")) {
					if (settings.containsKey(key))
						settings.remove(key);
					continue;
				}
				settings.put(key.toLowerCase(), file.getString(String.valueOf(section) + key));
			}
		}
	}

	/**
	 * <p>importFromSettings.</p>
	 *
	 * @param settings a {@link ru.aslcraft.api.ejcore.value.Settings} object
	 */
	public void importFromSettings(Settings<String> settings) {
		final Set<Map.Entry<String, String>> keys = settings.getKeys();

		for (final Map.Entry<String, String> param : keys) {
			if (param.getValue().equalsIgnoreCase("remove") || param.getValue().equalsIgnoreCase("null")) {
				if (this.settings.containsKey(param.getKey()))
					this.settings.remove(param.getKey());
				continue;
			}

			this.settings.put(param.getKey(), param.getValue());
		}
	}

	/**
	 * <p>exportToYAML.</p>
	 *
	 * @param file a {@link ru.aslcraft.api.ejcore.yaml.YAML} object
	 * @param section a {@link java.lang.String} object
	 */
	public void exportToYAML(YAML file, String section) {
		if (section == null || section.equalsIgnoreCase(""))
			section = "";
		else section = section + ".";

		for (final Map.Entry<String, String> key : getKeys()) {
			if (key.getKey().matches("^.*\\.[1-9]*"))
				continue;
			if (key.getKey().matches("^.*\\.0$")) {
				file.set(String.valueOf(section) + key.getKey().substring(0, key.getKey().length() - 2), exportArray(key.getKey().substring(0, key.getKey().length() - 2)));
				continue;
			}
			file.set(String.valueOf(section) + key.getKey(), key.getValue());
		}
	}
}
