package ru.aslcraft.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.clip.placeholderapi.events.ExpansionUnregisterEvent;
import ru.aslcraft.core.tasks.ReloadPAPIExpansions;

public class PapiListener implements Listener {

	@EventHandler
	public void onUnregister(ExpansionUnregisterEvent e) {
		if (!ReloadPAPIExpansions.isStarted())
			ReloadPAPIExpansions.start();
	}

}
