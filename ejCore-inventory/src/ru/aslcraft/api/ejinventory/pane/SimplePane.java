package ru.aslcraft.api.ejinventory.pane;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import lombok.NonNull;
import ru.aslcraft.api.bukkit.items.InventoryUtil;
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
			final LockedPage lPage = (LockedPage) page;
			if (lPage.isUnlocked(event.getSlot())) {
				if (lPage.getEmptyClick() != null)
					lPage.getEmptyClick().accept(event);
				return;
			} else {
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

		Arrays.asList(players).forEach(p -> { p.closeInventory(); p.openInventory(inventory); });
	}

	/** {@inheritDoc} */
	@Override
	public void returnItems(Player p, InventoryCloseEvent event) {
		if (page instanceof LockedPage && returnItems) {
			final LockedPage lPage = (LockedPage) page;
			if (lPage.getUnlocked().isEmpty()) return;
			lPage.getUnlocked().stream().forEach(i -> {
				if (event.getView().getTopInventory().getItem(i) != null)
					InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), p);
			});
		}
	}

	@Override
	public void update(Inventory inv) {
		page.display(inv);
		inv.getViewers().stream().filter(h -> h instanceof Player).forEach(h -> ((Player)h).updateInventory());
	}

	@Override
	public void update(Inventory inv, int locX, int locY) {
		page.update(inv, locX, locY);
	}

}
