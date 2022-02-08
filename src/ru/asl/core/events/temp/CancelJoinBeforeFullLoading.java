package ru.asl.core.events.temp;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

public class CancelJoinBeforeFullLoading implements Listener {

	private static CancelJoinBeforeFullLoading instance;

	public static void register() {
		if (instance != null) return;
		instance = new CancelJoinBeforeFullLoading();
		Bukkit.getPluginManager().registerEvents(instance, Core.instance());
	}

	public static void unregister() {
		HandlerList.unregisterAll(instance);
	}

	@EventHandler
	public void onJoin(AsyncPlayerPreLoginEvent e) {
		e.setLoginResult(Result.KICK_OTHER);
		e.setKickMessage(EText.c("&cPlease wait while server will be fully loaded!"));
	}
}
