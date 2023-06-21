package org.aslstd.core.task;

import java.util.Comparator;
import java.util.Set;

import org.aslstd.api.bukkit.message.Text;
import org.aslstd.api.openlib.plugin.OpenPlugin;
import org.aslstd.core.listener.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.service.ListenerRegistrator;
import org.aslstd.core.update.CheckUpdates;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoadOpenPlugins extends BukkitRunnable {

	private final Set<OpenPlugin> plugins;

	@SuppressWarnings("deprecation")
	@Override
	synchronized public void run() {
		final long bef = System.nanoTime();
		Text.sendLB();
		plugins.stream().sorted(Comparator.comparingInt(OpenPlugin::getPriority)).forEachOrdered(plugin -> {
			Text.fine("&6Initialising " + plugin.getName() + " " + plugin.getDescription().getVersion());
			try {
				plugin.init();
			} catch (final Exception e) {
				Text.warn("Something went wrong while loading " + plugin.getName());
				e.printStackTrace();
				plugins.remove(plugin);
				return;
			}
			CheckUpdates.registerOpenPlugin(plugin);
		});

		CheckUpdates.Task.runTask();

		ListenerRegistrator.register();

		final long aft = System.nanoTime();
		Text.fine("&aAll EJPlugins succesfuly loaded in " + Text.format((aft-bef)/1e9) +" sec.");
		Text.sendLB();

		CancelJoinBeforeFullLoading.unregister();
		Bukkit.getScheduler().cancelTask(getTaskId());
	}
}

