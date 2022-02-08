package ru.asl.api.ejinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public interface Pane extends InventoryHolder {

	void fire(InventoryClickEvent event);

	void showTo(Player... players);

	void returnItems(Player player, InventoryCloseEvent event);

}
