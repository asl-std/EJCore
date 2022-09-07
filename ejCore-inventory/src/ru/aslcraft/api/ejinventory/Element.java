package ru.aslcraft.api.ejinventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * <p>Element interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Element {

	/**
	 * <p>accept.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 */
	void accept(InventoryClickEvent event);

	/**
	 * <p>getIcon.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	ItemStack getIcon();

	/**
	 * <p>equals.</p>
	 *
	 * @param element a {@link ru.aslcraft.api.ejinventory.Element} object
	 * @return a boolean
	 */
	default boolean equals(Element element) {
		return equals(element.getIcon());
	}

	/**
	 * <p>equals.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	boolean equals(ItemStack icon);

	default void update(Inventory inventory, int locX, int locY) {
		placeOn(inventory,locX,locY);
	}

	/**
	 * <p>placeOn.</p>
	 *
	 * @param inventory a {@link org.bukkit.inventory.Inventory} object
	 * @param locX a int
	 * @param locY a int
	 */
	void placeOn(Inventory inventory, int locX, int locY);

}
