package ru.aslcraft.api.ejinventory;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class PluginHolder {

	@Getter private static JavaPlugin attachment;

	public static void attach(JavaPlugin plugin) {
		if (attachment == null) attachment = plugin;
	}

}
