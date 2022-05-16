package ru.asl.core.update;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import ru.asl.core.Core;

/**
 * <p>CheckUpdateTask class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class CheckUpdateTask extends BukkitRunnable {

	/** Constant <code>task</code> */
	protected static CheckUpdateTask task = null;

	/**
	 * <p>runTask.</p>
	 */
	public static void runTask() {
		if (task == null)
			task = new CheckUpdateTask();
		else {
			Bukkit.getScheduler().cancelTask(task.getTaskId());
			task = new CheckUpdateTask();
		}

		task.runTaskTimerAsynchronously(Core.instance(), 20L, 144000L);
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		EJUpdateChecker.checkUpdates();

		sendMessage();
	}

	private void sendMessage() {
		new BukkitRunnable() {

			@Override
			public void run() {
				EJUpdateChecker.sendUpdateMessage(Bukkit.getConsoleSender());
			}

		}.runTaskLater(Core.instance(), 40L);
	}

}
