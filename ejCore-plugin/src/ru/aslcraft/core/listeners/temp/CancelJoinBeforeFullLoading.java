package ru.aslcraft.core.listeners.temp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.core.Core;

/**
 * <p>CancelJoinBeforeFullLoading class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class CancelJoinBeforeFullLoading implements Listener {

	private static CancelJoinBeforeFullLoading instance;

	private static boolean registered = false;

	/**
	 * <p>register.</p>
	 */
	public static void register() {
		if (instance != null) return;
		instance = new CancelJoinBeforeFullLoading();
		Bukkit.getPluginManager().registerEvents(instance, Core.instance());
		registered = true;
	}

	/**
	 * <p>unregister.</p>
	 */
	public static void unregister() {
		if (registered)
			HandlerList.unregisterAll(instance);
	}

	/**
	 * <p>onJoin.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.AsyncPlayerPreLoginEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(AsyncPlayerPreLoginEvent e) {
		e.setLoginResult(Result.KICK_OTHER);
		e.setKickMessage(EText.c("&cPlease wait while server will be fully loaded!"));
	}
}
