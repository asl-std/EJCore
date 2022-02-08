package ru.asl.core;

import java.util.HashMap;

import org.bukkit.event.Listener;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;

public class ListenerLoader {

	private static HashMap<String, Listener> preRegister  = new HashMap<>();

	EJPlugin mainPlugin = null;

	public final void register() {
		for (final Listener listener : ListenerLoader.preRegister.values()) {
			mainPlugin.getServer().getPluginManager().registerEvents(listener, mainPlugin);
			if (Core.getCfg().DEBUG_RUNNING)
				EText.debug("Loaded listener: " + listener.getClass().getName());
		}
	}

	protected ListenerLoader(EJPlugin mainPlugin) {
		this.mainPlugin = mainPlugin;
	}

	public void addPreReg(String pluginName, Listener listener) {
		if (!ListenerLoader.preRegister.containsKey(pluginName)) ListenerLoader.preRegister.put(pluginName, listener);
	}

	public void addPreReg(String pluginName, Listener listener, boolean condition) {
		if (condition)
			addPreReg(pluginName, listener);
	}

	public void removePreReg(String pluginName) {
		if (ListenerLoader.preRegister.containsKey(pluginName)) ListenerLoader.preRegister.remove(pluginName);
	}

	public final void unregisterAll() {
		preRegister.clear();

		Core.instance().unregisterListeners();
	}

}
