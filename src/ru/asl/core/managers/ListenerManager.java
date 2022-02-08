package ru.asl.core.managers;

import java.util.HashMap;

import org.bukkit.event.Listener;

import lombok.Getter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.core.Core;

public class ListenerManager {

	private static HashMap<String, Listener> preRegister  = new HashMap<>();

	@Getter EJPlugin mainPlugin = null;

	public final void register() {
		for (final Listener listener : ListenerManager.preRegister.values()) {
			mainPlugin.getServer().getPluginManager().registerEvents(listener, mainPlugin);
			if (Core.getCfg().DEBUG_RUNNING)
				EText.debug("Loaded listener: " + listener.getClass().getName());
		}
	}

	public ListenerManager(EJPlugin mainPlugin) {
		this.mainPlugin = mainPlugin;
	}

	public void addPreReg(String pluginName, Listener listener) {
		if (!ListenerManager.preRegister.containsKey(pluginName)) ListenerManager.preRegister.put(pluginName, listener);
	}

	public void addPreReg(String pluginName, Listener listener, boolean condition) {
		if (condition)
			addPreReg(pluginName, listener);
	}

	public void removePreReg(String pluginName) {
		if (ListenerManager.preRegister.containsKey(pluginName)) ListenerManager.preRegister.remove(pluginName);
	}

	public final void unregisterAll() {
		preRegister.clear();

		Core.instance().unregisterListeners();
	}

}
