package org.aslstd.core.task;

import java.util.Comparator;
import java.util.Set;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.listener.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.manager.ModuleManager;
import org.aslstd.core.service.ListenerRegistrator;
import org.aslstd.core.update.CheckUpdateTask;
import org.aslstd.core.update.EJUpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InitialiseEJPluginsTask extends BukkitRunnable {

	private final Set<EJPlugin> plugins;

	@Override
	synchronized public void run() {
		final long bef = System.nanoTime();
		EText.sendLB();
		plugins.stream().sorted(Comparator.comparingInt(EJPlugin::getPriority)).forEachOrdered(plugin -> {
			EText.fine("&6Initialising " + plugin.getName() + " " + plugin.getDescription().getVersion());
			try {
				plugin.init();
			} catch (final Exception e) {
				EText.warn("Something went wrong while loading " + plugin.getName());
				e.printStackTrace();
				plugins.remove(plugin);
				return;
			}
			EJUpdateChecker.registerEJPlugin(plugin);
		});

		CheckUpdateTask.runTask();

		ModuleManager.enableModules();

		ListenerRegistrator.register();

		final long aft = System.nanoTime();
		EText.fine("&aAll EJPlugins succesfuly loaded in " + EText.format((aft-bef)/1e9) +" sec.");
		EText.sendLB();

		CancelJoinBeforeFullLoading.unregister();
		Bukkit.getScheduler().cancelTask(getTaskId());
	}
}

