package ru.aslcraft.core.managers;

import java.util.HashMap;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import lombok.Getter;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.bukkit.plugin.EJPlugin;
import ru.aslcraft.core.Core;

/**
 * <p>ListenerManager class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class ListenerManager {

	private static HashMap<String, Listener> listeners  = new HashMap<>();

	@Getter EJPlugin mainPlugin = null;

	/**
	 * <p>register.</p>
	 */
	public final void register() {
		for (final Listener listener : ListenerManager.listeners.values()) {
			mainPlugin.getServer().getPluginManager().registerEvents(listener, mainPlugin);
			if (Core.getCfg().DEBUG_RUNNING)
				EText.debug("Loaded listener: " + listener.getClass().getName());
		}
	}

	/**
	 * <p>Constructor for ListenerManager.</p>
	 *
	 * @param mainPlugin a {@link ru.aslcraft.api.bukkit.plugin.EJPlugin} object
	 */
	public ListenerManager(EJPlugin mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	

	/**
	 * <p>addListener.</p>
	 *
	 * @param pluginName a {@link java.lang.String} object
	 * @param listener a {@link org.bukkit.event.Listener} object
	 */
	public void addListener(String pluginName, Listener listener) {
		if (!ListenerManager.listeners.containsKey(pluginName))
			ListenerManager.listeners.put(pluginName, listener);
	}

	/**
	 * <p>addListener.</p>
	 *
	 * @param pluginName a {@link java.lang.String} object
	 * @param listener a {@link org.bukkit.event.Listener} object
	 * @param condition a boolean
	 */
	public void addListener(String pluginName, Listener listener, boolean condition) {
		if (condition)
			addListener(pluginName, listener);
	}

	/**
	 * <p>removeListener.</p>
	 *
	 * @param pluginName a {@link java.lang.String} object
	 */
	public void removeListener(String pluginName) {
		if (ListenerManager.listeners.containsKey(pluginName))
			ListenerManager.listeners.remove(pluginName);
	}

	/**
	 * <p>unregisterAll.</p>
	 */
	public final void unregisterAll() {
		listeners.clear();
		HandlerList.unregisterAll(getMainPlugin());
	}

}
