package ru.asl.core.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import ru.asl.api.bukkit.events.EquipChangeEvent;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.equip.EquipSlot;
import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.core.Core;
import ru.asl.core.update.EJUpdateChecker;

public class PlayerListener implements Listener {

	@EventHandler
	public void registerEPlayer(PlayerJoinEvent e) {
		new BukkitRunnable() {

			@Override
			public void run() {
				final EPlayer p = EPlayer.getEPlayer(e.getPlayer());

				for (final EquipSlot slot : EquipSlot.values()) {
					Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(slot, EquipSlot.getStackFromSlot(slot, e.getPlayer()), e.getPlayer()));
				}

				p.getSettings().importFromYAML(YAML.getPlayerFile(e.getPlayer()), "");
			}

		}.runTask(Core.instance());

		if (e.getPlayer().isOp() || e.getPlayer().hasPermission("*"))
			EJUpdateChecker.sendUpdateMessage(e.getPlayer());

	}

	@EventHandler
	public void unregisterEPlayer(PlayerQuitEvent e) {
		new BukkitRunnable() {

			@Override
			public void run() {
				final EPlayer p = EPlayer.getEPlayer(e.getPlayer());

				p.getSettings().exportToYAML(YAML.getPlayerFile(e.getPlayer()), "");

				EPlayer.removeRegistered(e.getPlayer());
			}

		}.runTask(Core.instance());
	}

}
