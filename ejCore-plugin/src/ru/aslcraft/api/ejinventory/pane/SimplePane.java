package ru.aslcraft.api.ejinventory.pane;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import lombok.NonNull;
import ru.aslcraft.api.ejcore.items.InventoryUtil;
import ru.aslcraft.api.ejinventory.Page;
import ru.aslcraft.api.ejinventory.Pane;
import ru.aslcraft.api.ejinventory.page.LockedPage;

/**
 * <p>SimplePane class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class SimplePane implements Pane {
	/* Заглушка */
	/** {@inheritDoc} */
	@Override @Deprecated
	public Inventory getInventory() { return Bukkit.createInventory(null, 9); }

	protected String			title;
	private final int		size;
	protected Page 			page;

	private boolean returnItems = true;

	/**
	 * <p>Constructor for SimplePane.</p>
	 *
	 * @param title a {@link java.lang.String} object
	 * @param size a int
	 * @param page a {@link ru.aslcraft.api.ejinventory.Page} object
	 */
	public SimplePane(@NonNull String title, int size, Page page) {
		this.title = title;
		this.size = size;

		this.page = page;
	}

	/**
	 * <p>Setter for the field <code>title</code>.</p>
	 *
	 * @param title a {@link java.lang.String} object
	 */
	public void setTitle(String title) { this.title = title; }

	/**
	 * <p>Setter for the field <code>page</code>.</p>
	 *
	 * @param page a {@link ru.aslcraft.api.ejinventory.Page} object
	 */
	public void setPage(Page page) { this.page = page; }

	/**
	 * <p>returnItems.</p>
	 *
	 * @param ret a boolean
	 */
	public void returnItems(boolean ret) { returnItems = ret; }

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void showTo(Player... players) {
		final Inventory inventory = Bukkit.createInventory(this, size, title);

		page.display(inventory);

		for (Player p : players) {
			p.closeInventory();
			p.openInventory(inventory);
		}
	}

	/** {@inheritDoc} */
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
