package org.aslstd.core.listeners;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.equip.EquipSlot;
import org.aslstd.api.bukkit.events.equipment.EquipChangeEvent;
import org.aslstd.api.bukkit.events.player.PlayerBlockMoveEvent;
import org.aslstd.api.bukkit.location.Vector3D;
import org.aslstd.api.ejcore.plugin.BukkitListener;
import org.aslstd.api.ejcore.plugin.Named;
import org.aslstd.core.Core;
import org.aslstd.core.update.EJUpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * <p>PlayerListener class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@Named(key = "playerJoin")
public class PlayerListener implements BukkitListener {

	/**
	 * <p>registerEPlayer.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.PlayerJoinEvent} object
	 */
	@EventHandler
	public void registerEPlayer(PlayerJoinEvent e) {


		new BukkitRunnable() {

			@Override
			public void run() {
				final EPlayer p = EPlayer.getEPlayer(e.getPlayer());

				for (final EquipSlot slot : EquipSlot.values()) {
					Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(slot, EquipSlot.getStackFromSlot(slot, e.getPlayer()), e.getPlayer()));
				}

				for (final String param : Core.getCfg().PLAYER_DATA_DEFAULTS) {
					final String[] data = param.split(":");
					if (data.length < 2) return;

					if (!p.getSettings().hasKey(data[0]))
						p.getSettings().setValue(data[0], data[1]);
				}
			}

		}.runTask(Core.instance());

		if (e.getPlayer().isOp() || e.getPlayer().hasPermission("*"))
			EJUpdateChecker.sendUpdateMessage(e.getPlayer());

	}

	/**
	 * <p>unregisterEPlayer.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.PlayerQuitEvent} object
	 */
	@EventHandler
	public void unregisterEPlayer(PlayerQuitEvent e) {
		new BukkitRunnable() {

			@Override
			public void run() {
				final EPlayer p = EPlayer.getEPlayer(e.getPlayer());

				p.save();

				EPlayer.unregister(e.getPlayer());
			}

		}.runTask(Core.instance());
	}

	/**
	 * <p>throwPlayerBlockMove.</p>
	 *
	 * @param e a {@link org.bukkit.event.player.PlayerMoveEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void throwPlayerBlockMove(PlayerMoveEvent e) {
		if (!e.isCancelled())
			if (Vector3D.blockEquals(e.getFrom(), e.getTo())) {
				final PlayerBlockMoveEvent event = new PlayerBlockMoveEvent(e.getPlayer(),e.getFrom(),e.getTo());
				Bukkit.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					e.setCancelled(true);
					return;
				}
				if (event.isLocChanged())
					e.setTo(e.getTo().add(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ()));
			}
	}

}
