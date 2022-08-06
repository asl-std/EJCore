package ru.aslcraft.api.ejcore.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import ru.aslcraft.api.bukkit.message.EText;

/**
 * <p>Incompatibility class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Incompatibility {

	private static final Map<String, String> pluginsIncompat = new HashMap<String, String>() {{
		put("ExecutableItems", "ElephantItems");
		put("ExecutableBlocks", "ElephantItems");
	}};

	/**
	 * <p>check.</p>
	 */
	public static void check() {
		Plugin source, target;
		boolean f = false;

		final Iterator<Map.Entry<String,String>> it = pluginsIncompat.entrySet().iterator();
		for (; it.hasNext(); ) {
			final Map.Entry<String,String> entry = it.next();
			source = Bukkit.getPluginManager().getPlugin(entry.getKey());
			target = Bukkit.getPluginManager().getPlugin(entry.getValue());

			if (source == null || target == null) continue;

			if (!f) {
				EText.warn("            <---------- CAUTION ---------->");
				f = true;
			}

			EText.warn(target.getName() + ": Plugin incompatibility found -> " + source.getName());
		}

		if (!it.hasNext() && f) {
			EText.warn("You will not receive any support due to plugin incompatibility");
			EText.warn("            <---------- CAUTION ---------->");
			EText.sendLB();
		}
	}

}
