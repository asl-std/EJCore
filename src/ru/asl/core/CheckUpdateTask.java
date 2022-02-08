package ru.asl.core;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckUpdateTask extends BukkitRunnable {

	protected static CheckUpdateTask task = null;

	public static void runTask() {
		if (task == null)
			task = new CheckUpdateTask();
		else {
			Bukkit.getScheduler().cancelTask(task.getTaskId());
			task = new CheckUpdateTask();
		}

		task.runTaskTimerAsynchronously(Core.instance(), 20L, 144000L);
	}

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
