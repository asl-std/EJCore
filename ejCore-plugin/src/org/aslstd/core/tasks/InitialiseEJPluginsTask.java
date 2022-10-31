package org.aslstd.core.tasks;

import java.util.Comparator;
import java.util.List;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.ejcore.plugin.EJPlugin;
import org.aslstd.core.listeners.RegisterEventListener;
import org.aslstd.core.listeners.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.managers.ModuleManager;
import org.aslstd.core.update.CheckUpdateTask;
import org.aslstd.core.update.EJUpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class InitialiseEJPluginsTask extends BukkitRunnable {

	private final List<EJPlugin> plugins;

	public InitialiseEJPluginsTask(List<EJPlugin> plugins) {
		this.plugins = plugins;

		plugins.sort(Comparator.comparingInt(EJPlugin::getPriority));
	}

	@Override
	synchronized public void run() {
		final long bef = System.nanoTime();
		EText.sendLB();

		for (final EJPlugin plugin : plugins) {

			EText.fine("&6Initialising " + plugin.getName() + " " + plugin.getDescription().getVersion());
			try {
				plugin.init();
			}catch (final Exception e) {
				EText.warn("Something went wrong while loading " + plugin.getName());
				e.printStackTrace();
				plugins.remove(plugin);
				continue;
			}
			EJUpdateChecker.registerEJPlugin(plugin);
		}

		CheckUpdateTask.runTask();

		ModuleManager.enableModules();

		RegisterEventListener.getListenerManager().register();

		final long aft = System.nanoTime();
		EText.fine("&aAll EJPlugins succesfuly loaded in " + EText.format((aft-bef)/1e9) +" sec.");
		EText.sendLB();

		CancelJoinBeforeFullLoading.unregister();
		Bukkit.getScheduler().cancelTask(getTaskId());
	}
}

