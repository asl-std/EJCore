package org.aslstd.core.listeners;

import org.aslstd.core.tasks.ReloadPAPIExpansions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.clip.placeholderapi.events.ExpansionUnregisterEvent;

public class PapiListener implements Listener {

	@EventHandler
	public void onUnregister(ExpansionUnregisterEvent e) {
		if (!ReloadPAPIExpansions.isStarted())
			ReloadPAPIExpansions.start();
	}

}
