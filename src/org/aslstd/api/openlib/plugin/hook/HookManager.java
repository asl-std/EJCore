package org.aslstd.api.openlib.plugin.hook;

import org.aslstd.core.OpenLib;

import lombok.Getter;

/**
 * <p>HookManager class.</p>
 *
 * @author Snoop1CattZ69
 */
public class HookManager {

	@Getter private static boolean papiEnabled = false;


	/**
	 * <p>tryHookPAPI.</p>
	 */
	public static boolean tryHookPAPI() {
		if (!isPluginEnabled("PlaceholderAPI")) {
			return (papiEnabled = false);
		} else {
			return (papiEnabled = true);
		}
	}

	/**
	 * <p>isPluginEnabled.</p>
	 *
	 * @param pluginName a {@link String} object
	 * @return a boolean
	 */
	public static boolean isPluginEnabled(String pluginName) {
		return OpenLib.instance().getServer().getPluginManager().isPluginEnabled(pluginName);
	}

}
