package ru.asl.api.ejinventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * <p>Page interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Page {

	/**
	 * <p>getTitle.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getTitle();

	/**
	 * <p>add.</p>
	 *
	 * @param elements a {@link ru.asl.api.ejinventory.Element} object
	 * @return an array of {@link ru.asl.api.ejinventory.Element} objects
	 */
	Element[] add(Element... elements);

	/**
	 * <p>add.</p>
	 *
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 * @return a boolean
	 */
	boolean add(Element element);

	/**
	 * <p>add.</p>
	 *
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 * @param locX a int
	 * @param locY a int
	 * @param ignore a boolean
	 * @return a boolean
	 */
	boolean add(Element element, int locX, int locY, boolean ignore);

	/**
	 * <p>contains.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	boolean contains(ItemStack icon);

	/**
	 * <p>display.</p>
	 *
	 * @param inv a {@link org.bukkit.inventory.Inventory} object
	 */
	void display(Inventory inv);

	/**
	 * <p>fill.</p>
	 *
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 */
	void fill(Element element);

	/**
	 * <p>fire.</p>
	 *
	 * @param event a {@link org.bukkit.event.inventory.InventoryClickEvent} object
	 * @return a boolean
	 */
	boolean fire(InventoryClickEvent event);

	/**
	 * <p>height.</p>
	 *
	 * @return a int
	 */
	int height();

	/**
	 * <p>remove.</p>
	 *
	 * @param locX a int
	 * @param locY a int
	 */
	void remove(int locX, int locY);

	/**
	 * <p>remove.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	void remove(ItemStack stack);

	/**
	 * <p>width.</p>
	 *
	 * @return a int
	 */
	int width();

}
