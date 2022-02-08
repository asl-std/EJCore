package ru.asl.api.ejinventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Page {

	String getTitle();

	Element[] add(Element... elements);

	boolean add(Element element);

	boolean add(Element element, int locX, int locY, boolean ignore);

	boolean contains(ItemStack icon);

	void display(Inventory inv);

	void fill(Element element);

	boolean fire(InventoryClickEvent event);

	int height();

	void remove(int locX, int locY);

	void remove(ItemStack stack);

	int width();

}
