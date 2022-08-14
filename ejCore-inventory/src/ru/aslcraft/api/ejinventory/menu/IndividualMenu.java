package ru.aslcraft.api.ejinventory.menu;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import ru.aslcraft.api.ejinventory.Page;
import ru.aslcraft.api.ejinventory.page.LockedPage;
import ru.aslcraft.api.ejinventory.pane.MultiPagePane;

public abstract class IndividualMenu extends MultiPagePane {

	public LockedPage getPage() { return (LockedPage) pages.get(currentPage); }

	public List<Page> getPages() { return pages; }

	private Inventory inventory;

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * <p>Constructor for SimpleMenu.</p>
	 *
	 * @param title a {@link java.lang.String} object
	 * @param size a int
	 */
	public IndividualMenu(String title, int size, boolean addButtons) {
		super(title, size, addButtons);
		addPage(new LockedPage(size/9, title));
	}



	/** {@inheritDoc} */
	@Override
	public void showTo(Player... players) {
		// TODO
		inventory = Bukkit.createInventory(this, size, title);

		if (pages.size() == 0) return;

		if (currentPage >= pages.size()) currentPage = 0;
		if (currentPage < 0) currentPage = pages.size()-1;

		pages.get(currentPage).display(inventory);

		Player p = null;

		for (final Player pl : players) {
			if (pl != null) p = pl;
		}

		if (p != null) {
			p.closeInventory();
			p.openInventory(inventory);
		}
	}

}
