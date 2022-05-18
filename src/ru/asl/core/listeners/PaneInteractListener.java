package ru.asl.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import ru.asl.api.ejcore.items.InventoryUtil;
import ru.asl.api.ejinventory.Pane;
import ru.asl.core.Core;

/**
 * <p>PaneInteractListener class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class PaneInteractListener implements Listener {

	/**
	 * <p>onPaneClick.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPaneClick(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return;
		if (event.getClickedInventory() instanceof PlayerInventory) return;

		if (event.getInventory().getHolder() instanceof Pane) {

			final Player whoClicked = (Player) event.getWhoClicked();

			event.setCancelled(true);

			if (event.getCursor() != null) {
				InventoryUtil.addItem(event.getCursor(), whoClicked);
				event.getView().setCursor(null);
			}

			final ItemStack curr = event.getCurrentItem();

			event.setCurrentItem(null);
			event.getView().setItem(event.getRawSlot(), curr);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Core.instance(), () -> whoClicked.updateInventory());

			((Pane) event.getInventory().getHolder()).fire(event);
		}
	}

	/**
	 * <p>onPageClose.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryCloseEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPageClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() instanceof Pane)
			((Pane) event.getInventory().getHolder()).returnItems((Player) event.getPlayer(), event);
	}

	/**
	 * <p>onPaneDrag.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryDragEvent} object
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPaneDrag(InventoryDragEvent event) {
		if (event.getInventory().getHolder() instanceof Pane)
			event.setCancelled(true);
	}

}
