package ru.asl.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

import ru.asl.api.bukkit.events.EquipChangeEvent;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.equip.EquipSlot;
import ru.asl.api.ejcore.utils.ServerVersion;
import ru.asl.core.Core;

public class EquipListener implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		if (!e.getWhoClicked().getInventory().equals(e.getClickedInventory())) return;
		final EPlayer p = EPlayer.getEPlayer((Player) e.getWhoClicked());

		if (e.isShiftClick() && e.getCurrentItem() != null) {
			final EquipSlot slot = EquipSlot.getFromItemType(e.getCurrentItem().getType(), false);
			new BukkitRunnable() {

				@Override
				public void run() {
					Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(slot, EquipSlot.getStackFromSlot(slot, p.getPlayer()), p.getPlayer()));
					Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, p.getPlayer()));
				}

			}.runTask(Core.instance());
		}

		new BukkitRunnable() {

			@Override
			public void run() {
				if (e.getSlotType() == SlotType.QUICKBAR) {
					if (p.getPlayer().getInventory().getHeldItemSlot() == e.getSlot())
						Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, p.getPlayer()));
				}

				if (e.getInventory().getType() == InventoryType.CRAFTING) {
					if (e.getSlot() > 35 && e.getSlot() < 41)
						Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.byID(e.getSlot()), p.getPlayer().getInventory().getItem(e.getSlot()), p.getPlayer()));
				}

			}

		}.runTask(Core.instance());
	}

	@EventHandler
	public void onHeldChange(PlayerItemHeldEvent e) {
		Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, e.getPlayer()));
	}

	@EventHandler
	public void onEquipWearing(PlayerInteractEvent e) {
		final Material stack = e.getMaterial();
		new BukkitRunnable() {

			@Override
			public void run() {
				if (stack.name().equalsIgnoreCase(e.getPlayer().getInventory().getItemInMainHand().getType().name())) return;
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && stack != null) {

					final EquipSlot slot = EquipSlot.getFromItemType(stack, false);
					Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(slot, EquipSlot.getStackFromSlot(slot, e.getPlayer()), e.getPlayer()));

					if (e.getHand() == EquipmentSlot.HAND)
						Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, e.getPlayer()));
					else
						Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.OFF, null, e.getPlayer()));
				}
			}

		}.runTask(Core.instance());
	}

	@EventHandler
	public void onDragEvent(InventoryDragEvent e) {
		final EPlayer p = EPlayer.getEPlayer((Player) e.getWhoClicked());

		new BukkitRunnable() {

			@Override
			public void run() {
				if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_13))
					for (final int i : e.getRawSlots()) {
						if (e.getView().getSlotType(i) == SlotType.QUICKBAR) {
							if (p.getPlayer().getInventory().getHeldItemSlot() == i%9)
								Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, p.getPlayer()));
						}
					}
				else {
					for (final int i : e.getRawSlots()) {
						if (i > 26) {
							if (p.getPlayer().getInventory().getHeldItemSlot() == i%9)
								Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, p.getPlayer()));
						}
					}
				}

				if (e.getInventory().getType() == InventoryType.CRAFTING) {
					for (int i = 36 ; i < 41 ; i++) {
						if (e.getInventorySlots().contains(i)) {
							Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.byID(i), p.getPlayer().getInventory().getItem(i), p.getPlayer()));
						}
					}
				}

			}

		}.runTask(Core.instance());
	}

	@EventHandler
	public void onItemPickup(EntityPickupItemEvent e) {
		if (e.getEntityType() == EntityType.PLAYER) {
			Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, (Player) e.getEntity()));
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(EquipSlot.HAND, null, e.getPlayer()));
	}

	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		if (e.getKeepInventory()) return;

		new BukkitRunnable() {

			@Override
			public void run() {
				if (e.getEntity().isDead()) return;

				EPlayer.getEPlayer(e.getEntity()).unequipAll();

				cancel();
			}

		}.runTaskTimer(Core.instance(), 1L, 2L);
	}

}
