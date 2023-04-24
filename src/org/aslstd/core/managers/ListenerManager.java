package org.aslstd.core.managers;

import java.util.HashMap;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.Core;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import lombok.Getter;

@Deprecated(since = "1.2.19", forRemoval = true)
public class ListenerManager {

	private static HashMap<String, Listener> listeners  = new HashMap<>();

	@Getter EJPlugin mainPlugin = null;

	public final void register() {
		for (final Listener listener : listeners.values()) {
			mainPlugin.getServer().getPluginManager().registerEvents(listener, mainPlugin);
			if (Core.getCfg().DEBUG_RUNNING)
				EText.debug("Loaded listener: " + listener.getClass().getName());
		}
	}

	public ListenerManager(EJPlugin mainPlugin) {
		this.mainPlugin = mainPlugin;
	}

	public void addListener(String keyName, Listener listener) {
		if (!listeners.containsKey(keyName))
			listeners.put(keyName, listener);
	}

	public void addListener(String keyName, Listener listener, boolean condition) {
		if (condition)
			addListener(keyName, listener);
	}

	public void removeListener(String keyName) {
		if (listeners.containsKey(keyName))
			listeners.remove(keyName);
	}

	public final void unregisterAll() {
		listeners.clear();
		HandlerList.unregisterAll(getMainPlugin());
	}

}
