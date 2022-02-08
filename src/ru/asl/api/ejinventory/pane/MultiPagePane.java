package ru.asl.api.ejinventory.pane;

import java.util.LinkedList;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import ru.asl.api.ejcore.items.InventoryUtil;
import ru.asl.api.ejcore.items.ItemStackUtil;
import ru.asl.api.ejinventory.Page;
import ru.asl.api.ejinventory.Pane;
import ru.asl.api.ejinventory.element.SimpleElement;
import ru.asl.api.ejinventory.page.LockedPage;

public class MultiPagePane implements Pane {
	/* Обычнай заглушка */
	@Override @Deprecated
	public Inventory getInventory() { return Bukkit.createInventory(null, 9); }

	private static SimpleElement btnNext =
			new SimpleElement(ItemStackUtil.toStack("ARROW:1:0:0♥&6Next Page"), e -> {
				final MultiPagePane pane = (MultiPagePane) e.getInventory().getHolder();
				pane.next((Player) e.getWhoClicked(), e);
			}),
			btnPrev =
			new SimpleElement(ItemStackUtil.toStack("ARROW:1:0:0♥&6Previous Page"), e -> {
				final MultiPagePane pane = (MultiPagePane) e.getInventory().getHolder();
				pane.previous((Player) e.getWhoClicked(), e);
			});

	private final String	title;
	private final int		size;
	protected final LinkedList<Page>	pages;
	private boolean addButtons = true;

	private int currentPage = 0;

	public MultiPagePane(String title, int size, boolean addButtons, Page... pages) {
		this(title, size, addButtons);
		for (final Page page : pages)
			addPage(page);
	}

	public MultiPagePane(String title, int size, boolean addButtons) {
		this.title = Objects.requireNonNull(title);
		this.size = size;
		this.addButtons = addButtons;
		pages = new LinkedList<>();
	}

	public void addPage(Page page) {
		pages.add(page);
		if (addButtons)
			if (pages.size() > 1) {
				pages.get(pages.size()-1).add(btnNext, 5, page.height()-1, true);
				page.add(btnPrev, 3, page.height()-1, true);
			}
	}


	@Override
	public void fire(InventoryClickEvent event) {
		final Page page = pages.get(currentPage);

		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
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

		if (pages.size() == 0) return;

		if (currentPage >= pages.size()) currentPage = 0;
		if (currentPage < 0) currentPage = pages.size()-1;

		pages.get(currentPage).display(inventory);

		for (final Player p : players) {
			p.closeInventory();
			p.openInventory(inventory);
		}
	}

	public void next(Player p, InventoryClickEvent event) {
		this.returnItems(p, event);
		currentPage += 1;
		showTo(p);
	}

	public void previous(Player p, InventoryClickEvent event) {
		this.returnItems(p, event);
		currentPage -= 1;
		showTo(p);
	}

	private void returnItems(Player p, InventoryClickEvent event) {
		final Page page = pages.get(currentPage);

		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
			if (lPage.getUnlocked().isEmpty()) return;
			for (final int i : lPage.getUnlocked())
				InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), p);
		}
	}

	@Override
	public void returnItems(Player p, InventoryCloseEvent event) {
		final Page page = pages.get(currentPage);

		if (page instanceof LockedPage) {
			final LockedPage lPage = (LockedPage) page;
			if (lPage.getUnlocked().isEmpty()) return;
			for (final Integer i : lPage.getUnlocked())
				InventoryUtil.addItem(event.getView().getTopInventory().getItem(i), p);
		}
	}

}
