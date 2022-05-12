package ru.asl.core.tasks;

import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.core.Core;
import ru.asl.core.listeners.temp.CancelJoinBeforeFullLoading;
import ru.asl.core.update.CheckUpdateTask;
import ru.asl.core.update.EJUpdateChecker;

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

		Core.getEventLoader().register();

		final long aft = System.nanoTime();
		EText.fine("&aAll EJPlugins succesfuly loaded in " + EText.format((aft-bef)/1e9) +" sec.");
		EText.sendLB();

		CancelJoinBeforeFullLoading.unregister();
		Bukkit.getScheduler().cancelTask(getTaskId());
	}
}

