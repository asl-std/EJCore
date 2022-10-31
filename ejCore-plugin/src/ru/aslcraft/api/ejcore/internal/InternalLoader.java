package ru.aslcraft.api.ejcore.internal;

import java.io.File;

import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.core.Core;

public class InternalLoader {

	public static void enablePlugin(File jarFile) {
		try {
			final Plugin plugin = Core.instance().getPluginLoader().loadPlugin(jarFile);
			if (plugin == null) throw new InvalidPluginException("Something went wrong while loading plugin");

			if (!plugin.isEnabled())
				Core.instance().getPluginLoader().enablePlugin(plugin);
		} catch (UnknownDependencyException | InvalidPluginException e) {
			EText.warn("Plugin " + jarFile.getName() + " could't be loaded");
		}
	}

	public void initialize() {

		// PLUGIN DOWNLOADING LOGIC


		// File downloaded = DOWNLOADED METHOD
		// enablePlugin(downloaded)

	}

}
