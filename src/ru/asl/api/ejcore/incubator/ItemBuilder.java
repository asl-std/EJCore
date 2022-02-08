package ru.asl.api.ejcore.incubator;

import org.bukkit.inventory.ItemStack;

import ru.asl.api.ejcore.value.StringSettings;
import ru.asl.api.ejcore.yaml.YAML;

public class ItemBuilder {

	private StringSettings settings = new StringSettings();

	private ItemStack result;

	public ItemBuilder(YAML file) {
		settings.importFromYAML(file, "");
	}

	public ItemBuilder(YAML file, String section) {
		settings.importFromYAML(file, section);
	}

}
