package ru.asl.api.ejcore.value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.asl.api.ejcore.yaml.YAML;

public class StringSettings extends Settings<String> {

	public void importArray(List<? extends Object> array, String key) {
		for (int i = 0; i < array.size(); i++)
			settings.put(String.valueOf(key.toLowerCase()) + "." + i, array.get(i).toString());
	}

	public void removeArray(String key) {
		for (int i = 0; hasKey(String.valueOf(key) + "." + i); i++)
			remove(String.valueOf(key) + "." + i);
	}

	public List<String> exportArray(String key) {
		final List<String> keys = new ArrayList<>();
		for (int i = 0 ; hasKey(String.valueOf(key) + "." + i); i++)
			keys.add(settings.get(String.valueOf(key) + "." + i));
		return keys;
	}

	public void importFromYAML(YAML file, String section) {
		final Set<String> keys = (section != null && !section.equalsIgnoreCase("")) ? file.getSection(section).getKeys(true)
				: file.getKeys(true);
		for (final String key : keys) {
			if (!file.getString(String.valueOf(section) + "." + key).startsWith("MemorySection[path='")
					|| !file.getStringList(String.valueOf(section) + "." + key).isEmpty()) {
				if (!file.getStringList(String.valueOf(section) + "." + key).isEmpty()) {
					importArray(file.getStringList(String.valueOf(section) + "." + key), key);
					continue;
				}
				if (file.getString(String.valueOf(section) + "." + key).equalsIgnoreCase("remove")
						|| file.getString(String.valueOf(section) + "." + key).equalsIgnoreCase("null")) {
					if (settings.containsKey(key))
						settings.remove(key);
					continue;
				}
				settings.put(key.toLowerCase(), file.getString(String.valueOf(section) + "." + key));
			}
		}
	}

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

	public void exportToYAML(YAML file, String section) {
		for (final Map.Entry<String, String> key : getKeys()) {
			if (key.getKey().matches("^.*\\.[1-9]*"))
				continue;
			if (key.getKey().matches("^.*\\.0$")) {
				file.set(String.valueOf(section) + "." + key.getKey().substring(0, key.getKey().length() - 2), exportArray(key.getKey().substring(0, key.getKey().length() - 2)));
				continue;
			}
			file.set(String.valueOf(section) + "." + key.getKey(), key.getValue());
		}
	}
}
