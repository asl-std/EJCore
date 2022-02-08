package ru.asl.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import ru.asl.api.ejcore.items.InventoryUtil;
import ru.asl.api.ejinventory.Pane;
import ru.asl.core.Core;

public class PaneInteractListener implements Listener {

	@EventHandler
	public void onPaneClick(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return;
		if (event.getClickedInventory() instanceof PlayerInventory) return;
		if (event.getInventory().getHolder() instanceof Pane) {

			event.setCancelled(true);

			if (event.getCursor() != null) {
				InventoryUtil.addItem(event.getCursor(), (Player) event.getWhoClicked());
				event.getView().setCursor(null);
			}

			final ItemStack curr = event.getCurrentItem();

			event.setCurrentItem(null);
			event.getView().setItem(event.getRawSlot(), curr);

			new BukkitRunnable() {

				@Override
				public void run() {
					((Player)event.getWhoClicked()).updateInventory();
				}

			}.runTask(Core.instance());

			((Pane) event.getInventory().getHolder()).fire(event);
		}
	}

	@EventHandler
	public void onPageClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof Pane)
			((Pane) event.getInventory().getHolder()).returnItems((Player) event.getPlayer(), event);
	}

	@EventHandler
	public void onPaneDrag(InventoryDragEvent event) {
		if (event.getInventory().getHolder() instanceof Pane)
			event.setCancelled(true);
	}

}
