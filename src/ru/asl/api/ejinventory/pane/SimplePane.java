package ru.asl.api.ejinventory.pane;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import lombok.NonNull;
import ru.asl.api.ejcore.items.InventoryUtil;
import ru.asl.api.ejinventory.Page;
import ru.asl.api.ejinventory.Pane;
import ru.asl.api.ejinventory.page.LockedPage;

public class SimplePane implements Pane {
	/* Заглушка */
	@Override @Deprecated
	public Inventory getInventory() { return Bukkit.createInventory(null, 9); }

	protected String			title;
	private final int		size;
	protected Page 			page;

	private boolean returnItems = true;

	public SimplePane(@NonNull String title, int size, Page page) {
		this.title = title;
		this.size = size;

		this.page = page;
	}

	public void setTitle(String title) { this.title = title; }

	public void setPage(Page page) { this.page = page; }

	public void returnItems(boolean ret) { returnItems = ret; }

	@Override
	public void fire(InventoryClickEvent event) {
		if (page instanceof LockedPage) {
			LockedPage lPage = (LockedPage) page;
			if (lPage.isUnlocked(event.getSlot()))
				return;
			else {
				lPage.fire(event);
				return;
			}
		}

		page.fire(event);
	}

	@Override
	public void showTo(Player... players) {
		final Inventory inventory = Bukkit.createInventory(this, size, title);

		page.display(inventory);

		for (Player p : players) {
			p.closeInventory();
			p.openInventory(inventory);
		}
	}

	@Override
	public void returnItems(Player p, InventoryCloseEvent event) {
		if (page instanceof LockedPage && returnItems) {
			LockedPage lPage = (LockedPage) page;
			if (lPage.getUnlocked().isEmpty()) return;
			for (Integer i : lPage.getUnlocked())
				InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), p);
		}
	}

}
