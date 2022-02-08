package ru.asl.api.ejinventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Element {

	void accept(InventoryClickEvent event);

	ItemStack getIcon();

	boolean equals(Element element);

	boolean equals(ItemStack icon);

	void placeOn(Inventory inventory, int locX, int locY);

}
