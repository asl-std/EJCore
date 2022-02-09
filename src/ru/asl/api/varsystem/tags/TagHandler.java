package ru.asl.api.varsystem.tags;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.core.Core;

public class TagHandler {

	public static final YAML file = new YAML(Core.instance().getDataFolder() + "/tags.yml");

	public static ConcurrentMap<String,Tag> tags = new ConcurrentHashMap<>();

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
