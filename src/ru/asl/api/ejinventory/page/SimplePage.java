package ru.asl.api.ejinventory.page;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ru.asl.api.ejinventory.Element;
import ru.asl.api.ejinventory.Page;

public class SimplePage implements Page {

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public Element[] add(Element... elements) {
		return null;
	}

	@Override
	public boolean add(Element element) {
		return false;
	}

	@Override
	public boolean add(Element element, int locX, int locY, boolean ignore) {
		return false;
	}

	@Override
	public boolean contains(ItemStack icon) {
		return false;
	}

	@Override
	public void display(Inventory inv) {
	}

	@Override
	public void fill(Element element) {
	}

	@Override
	public boolean fire(InventoryClickEvent event) {
		return false;
	}

	@Override
	public int height() {
		return 0;
	}

	@Override
	public void remove(int locX, int locY) {
	}

	@Override
	public void remove(ItemStack stack) {
	}

	@Override
	public int width() {
		return 0;
	}

}
