package ru.asl.core.managers;

import java.util.HashMap;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import lombok.Getter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.core.Core;

public class ListenerManager {

	private static HashMap<String, Listener> listeners  = new HashMap<>();

	@Getter EJPlugin mainPlugin = null;

	public final void register() {
		for (final Listener listener : ListenerManager.listeners.values()) {
			mainPlugin.getServer().getPluginManager().registerEvents(listener, mainPlugin);
			if (Core.getCfg().DEBUG_RUNNING)
				EText.debug("Loaded listener: " + listener.getClass().getName());
		}
	}

	public ListenerManager(EJPlugin mainPlugin) {
		this.mainPlugin = mainPlugin;
	}
	

	public void addListener(String pluginName, Listener listener) {
		if (!ListenerManager.listeners.containsKey(pluginName))
			ListenerManager.listeners.put(pluginName, listener);
	}

	public void addListener(String pluginName, Listener listener, boolean condition) {
		if (condition)
			addListener(pluginName, listener);
	}

	public void removeListener(String pluginName) {
		if (ListenerManager.listeners.containsKey(pluginName))
			ListenerManager.listeners.remove(pluginName);
	}

	public final void unregisterAll() {
		listeners.clear();
		HandlerList.unregisterAll(getMainPlugin());
	}

}
