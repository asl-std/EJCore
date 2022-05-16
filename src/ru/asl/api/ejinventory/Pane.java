package ru.asl.api.ejinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * <p>Pane interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Pane extends InventoryHolder {

	/**
	 * <p>fire.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	void fire(InventoryClickEvent event);

	/**
	 * <p>showTo.</p>
	 *
	 * @param players a {@link org.bukkit.entity.Player} object
	 */
	void showTo(Player... players);

	/**
	 * <p>returnItems.</p>
	 *
	 * @param player a {@link org.bukkit.entity.Player} object
	 * @param event a {@link org.bukkit.event.inventory.InventoryCloseEvent} object
	 */
	void returnItems(Player player, InventoryCloseEvent event);

}
