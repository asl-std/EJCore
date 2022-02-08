package ru.asl.core;

import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import ru.asl.api.bukkit.message.EText;
import ru.asl.api.bukkit.plugin.EJPlugin;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.equip.EquipSlot;
import ru.aslteam.api.entity.RPGPlayer;
import ru.aslteam.module.ehunger.entity.Eater;

public class InitialiseEJPluginsTask extends BukkitRunnable {

	private final List<EJPlugin> plugins;
	private boolean allLoaded = true;

	public InitialiseEJPluginsTask(List<EJPlugin> plugins) {
		this.plugins = plugins;

		plugins.sort(Comparator.comparingInt(EJPlugin::getPriority));
	}

	@Override
	synchronized public void run() {
		for (final EJPlugin plugin : plugins)
			if (!plugin.isEnabled()) {
				allLoaded = false;
			}


		if (allLoaded) {
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
				}
				EJUpdateChecker.registerEJPlugin(plugin);
			}

			Bukkit.getScheduler().runTask(Core.instance(), () -> {
				if (Bukkit.getPluginManager().getPlugin("ElephantItems") == null)
					for (final Player p : Bukkit.getOnlinePlayers()) {
						for (final EquipSlot slot : EquipSlot.values()) {
							RPGPlayer.calculateEquip(EPlayer.getEPlayer(p), slot);
						}
						RPGPlayer.updateStats(EPlayer.getEPlayer(p));
					}
				if (Bukkit.getPluginManager().getPlugin("ElephantModule-eHunger") != null)
					for (final Player p : Bukkit.getOnlinePlayers())
						Eater.initPlayer(p);
			});

			CheckUpdateTask.runTask();

			Core.getEventLoader().register();

			final long aft = System.nanoTime();
			EText.fine("&aAll EJPlugins succesfuly loaded in " + EText.format((aft-bef)/1e9) +" sec.");
			EText.sendLB();

			Bukkit.getScheduler().cancelTask(getTaskId());
		}
	}
}

