package org.aslstd.api.ejinventory;

import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;

public class EJInventory {

	@Getter private static JavaPlugin attachment;

	public static void attach(JavaPlugin plugin) {
		if (attachment == null) attachment = plugin;
	}

}