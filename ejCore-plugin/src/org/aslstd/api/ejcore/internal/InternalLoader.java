package org.aslstd.api.ejcore.internal;

import java.io.File;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.core.Core;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

public class InternalLoader {

	public static void loadPlugin(File jarFile) {
		try {
			final Plugin plugin = Core.instance().getPluginLoader().loadPlugin(jarFile);
			if (plugin == null) throw new InvalidPluginException("Something went wrong while loading plugin");
		} catch (UnknownDependencyException | InvalidPluginException e) {
			EText.warn("Plugin " + jarFile.getName() + " could't be loaded");
		}
	}

	public void initialize() {


		// PLUGIN DOWNLOADING LOGIC

		// File downloaded = DOWNLOADED METHOD
		// loadPlugin(downloaded)

	}

}
