package ru.aslcraft.core.tasks;

import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.ejcore.plugin.EJPlugin;
import ru.aslcraft.core.listeners.RegisterEventListener;
import ru.aslcraft.core.listeners.temp.CancelJoinBeforeFullLoading;
import ru.aslcraft.core.update.CheckUpdateTask;
import ru.aslcraft.core.update.EJUpdateChecker;

/**
 * <p>InitialiseEJPluginsTask class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class InitialiseEJPluginsTask extends BukkitRunnable {

	private final List<EJPlugin> plugins;

	/**
	 * <p>Constructor for InitialiseEJPluginsTask.</p>
	 *
	 * @param plugins a {@link java.util.List} object
	 */
	public InitialiseEJPluginsTask(List<EJPlugin> plugins) {
		this.plugins = plugins;

		plugins.sort(Comparator.comparingInt(EJPlugin::getPriority));
	}

	/** {@inheritDoc} */
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

		RegisterEventListener.getListenerManager().register();

		final long aft = System.nanoTime();
		EText.fine("&aAll EJPlugins succesfuly loaded in " + EText.format((aft-bef)/1e9) +" sec.");
		EText.sendLB();

		CancelJoinBeforeFullLoading.unregister();
		Bukkit.getScheduler().cancelTask(getTaskId());
	}
}

