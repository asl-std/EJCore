package org.aslstd.core.listener;

import org.aslstd.api.bukkit.entity.pick.Pick;
import org.aslstd.api.bukkit.equip.EquipSlot;
import org.aslstd.api.bukkit.events.equipment.PrepareEquipEvent;
import org.aslstd.api.ejcore.player.EPlayer;
import org.aslstd.api.ejcore.plugin.BukkitListener;
import org.aslstd.api.ejcore.plugin.Named;
import org.aslstd.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseArmorEvent;
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

@Named(key = "equip")
public class EquipListener implements BukkitListener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void inventoryClick(InventoryClickEvent e) {
		if (e.isCancelled()) return;
		if (!e.getWhoClicked().getInventory().equals(e.getClickedInventory())) return;

		final EPlayer p = Pick.of((Player)e.getWhoClicked());

		if (e.isShiftClick() && e.getCurrentItem() != null) {
			final EquipSlot slot = EquipSlot.get(e.getCurrentItem().getType(), false);

			Bukkit.getScheduler().runTask(Core.instance(), () -> {
				PrepareEquipEvent.call(slot, EquipSlot.get(slot, p.getPlayer()), p.getPlayer());
				PrepareEquipEvent.call(EquipSlot.HAND, null, p.getPlayer());
			});
		}

		Bukkit.getScheduler().runTask(Core.instance(), () -> {
			if (e.getSlotType() == SlotType.QUICKBAR) {
				if (p.getPlayer().getInventory().getHeldItemSlot() == e.getSlot())
					PrepareEquipEvent.call(EquipSlot.HAND, null, p.getPlayer());
			}

			if (e.getInventory().getType() == InventoryType.CRAFTING) {
				if (e.getSlot() > 35 && e.getSlot() < 41)
					PrepareEquipEvent.call(EquipSlot.id(e.getSlot()), p.getPlayer().getInventory().getItem(e.getSlot()), p.getPlayer());
			}
		});
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onHeldChange(PlayerItemHeldEvent e) {
		PrepareEquipEvent.call(EquipSlot.HAND, null, e.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onEquipWearing(PlayerInteractEvent e) {
		if (e.useInteractedBlock() == Result.DENY || e.useItemInHand() == Result.DENY) return;

		final Material stack = e.getMaterial();
		Bukkit.getScheduler().runTask(Core.instance(), () -> {
			if (stack.name().equalsIgnoreCase(e.getPlayer().getInventory().getItemInMainHand().getType().name())) return;

			if ( ( e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK ) && stack != null) {

				final EquipSlot slot = EquipSlot.get(stack, false);
				PrepareEquipEvent.call(slot, EquipSlot.get(slot, e.getPlayer()), e.getPlayer());

				if (e.getHand() == EquipmentSlot.HAND)
					PrepareEquipEvent.call(EquipSlot.HAND, null, e.getPlayer());
				else
					PrepareEquipEvent.call(EquipSlot.OFF, null, e.getPlayer());
			}
		});
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onDragEvent(InventoryDragEvent e) {
		if (e.isCancelled()) return;
		final EPlayer p = Pick.of((Player)e.getWhoClicked());

		Bukkit.getScheduler().runTask(Core.instance(), () -> {
			for (final int i : e.getRawSlots()) {
				final int converted = e.getView().convertSlot(i);
				switch(e.getView().getSlotType(i)) {
					case ARMOR -> {
						switch(converted) {
							case 39 ->  PrepareEquipEvent.call(EquipSlot.HEAD, null, p.getPlayer());
							case 38 ->  PrepareEquipEvent.call(EquipSlot.BODY, null, p.getPlayer());
							case 37 ->  PrepareEquipEvent.call(EquipSlot.LEGGS, null, p.getPlayer());
							case 36 ->  PrepareEquipEvent.call(EquipSlot.FOOTS, null, p.getPlayer());
						}
					}
					case QUICKBAR -> {
						if (converted == p.getPlayer().getInventory().getHeldItemSlot())
							PrepareEquipEvent.call(EquipSlot.HAND, null, p.getPlayer());
						if (converted == 40)
							PrepareEquipEvent.call(EquipSlot.OFF, null, p.getPlayer());
					}
					default -> { /* ignore */ }
				}
			}
		});
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onItemPickup(EntityPickupItemEvent e) {
		if (e.getEntityType() == EntityType.PLAYER)
			PrepareEquipEvent.call(EquipSlot.HAND, null, (Player)e.getEntity());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onItemDrop(PlayerDropItemEvent e) {
		PrepareEquipEvent.call(EquipSlot.HAND, null, e.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void dispenseArmor(BlockDispenseArmorEvent e) {
		if (e.getItem().getType() == Material.AIR || e.getTargetEntity().getType() != EntityType.PLAYER) return;

		final EquipSlot slot = EquipSlot.get(e.getItem().getType(), true);

		PrepareEquipEvent.call(slot, e.getItem(), (Player) e.getTargetEntity());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDie(PlayerDeathEvent e) {
		if (e.getKeepInventory()) return;

		new BukkitRunnable() {

			@Override
			public void run() {
				if (e.getEntity().isDead()) return;

				PrepareEquipEvent.call(EquipSlot.ALL, null, e.getEntity());

				cancel();
			}

		}.runTaskTimer(Core.instance(), 1L, 2L);
	}

}
