package ru.aslcraft.api.ejinventory.page;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ru.aslcraft.api.ejinventory.Element;
import ru.aslcraft.api.ejinventory.Page;

/**
 * <p>SimplePage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class SimplePage implements Page {

	/** {@inheritDoc} */
	@Override
	public String getTitle() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Element[] add(Element... elements) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(Element element) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(Element element, int locX, int locY, boolean ignore) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(ItemStack icon) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void display(Inventory inv) {
	}

	/** {@inheritDoc} */
	@Override
	public void fill(Element element) {
	}

	/** {@inheritDoc} */
	@Override
	public boolean fire(InventoryClickEvent event) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public int height() {
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public void remove(int locX, int locY) {
	}

	/** {@inheritDoc} */
	@Override
	public void remove(ItemStack stack) {
	}

	/** {@inheritDoc} */
	@Override
	public int width() {
		return 0;
	}

	@Override
	public void update(Inventory inv, int locX, int locY) {
	}

}
