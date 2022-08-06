package ru.aslcraft.api.bukkit.plugin.hook;

import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.core.Core;

/**
 * <p>HookManager class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class HookManager {

	/**
	 * <p>tryHookPAPI.</p>
	 */
	public static void tryHookPAPI() {
		if (!isPluginEnabled("PlaceholderAPI"))
			EText.warn("I can't create new PAPI expansion because PlaceholderAPI not installed.");
		else
			EText.fine("PAPI expansion loaded!");
	}

	/**
	 * <p>isPluginEnabled.</p>
	 *
	 * @param pluginName a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isPluginEnabled(String pluginName) {
		return Core.instance().getServer().getPluginManager().isPluginEnabled(pluginName);
	}

}
