package ru.asl.api.bukkit.plugin.hook;

import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

public class HookManager {

	public static void tryHookPAPI() {
		if (!isPluginEnabled("PlaceholderAPI"))
			EText.warn("I can't create new PAPI expansion because PlaceholderAPI not installed.");
		else
			EText.fine("PAPI expansion loaded!");
	}

	public static boolean isPluginEnabled(String pluginName) {
		return Core.instance().getServer().getPluginManager().isPluginEnabled(pluginName);
	}

}
