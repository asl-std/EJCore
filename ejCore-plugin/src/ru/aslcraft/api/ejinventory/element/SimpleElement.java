package ru.aslcraft.api.ejinventory.element;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import ru.aslcraft.api.bukkit.items.IStatus;
import ru.aslcraft.api.bukkit.items.ItemStackUtil;
import ru.aslcraft.api.bukkit.message.EText;
import ru.aslcraft.api.bukkit.utils.BasicMetaAdapter;
import ru.aslcraft.api.ejinventory.Element;

/**
 * <p>SimpleElement class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class SimpleElement implements Element {

	@Getter private final String				hash;
	@Getter private ItemStack					icon;
	protected Consumer<InventoryClickEvent>		func;

	public final int pX, pY;

	/**
	 * <p>setFunction.</p>
	 *
	 * @param func a {@link java.util.function.Consumer} object
	 */
	public void setFunction(Consumer<InventoryClickEvent> func) {
		this.func = func;
	}

	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param createFunction a boolean
	 */
	public SimpleElement(ItemStack icon, boolean createFunction) {
		pX = 0; pY = 0;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		if (createFunction)
			setFunction(e -> e.setCancelled(true));
	}

	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param createFunction a boolean
	 * @param px a int
	 * @param py a int
	 */
	public SimpleElement(ItemStack icon, boolean createFunction, int px, int py) {
		pX = px; pY = py;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		if (createFunction)
			setFunction(e -> e.setCancelled(true));
	}


	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param function a {@link java.util.function.Consumer} object
	 */
	public SimpleElement(ItemStack icon, Consumer<InventoryClickEvent> function) {
		pX = 0; pY = 0;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		func = Objects.requireNonNull(function);
	}

	/**
	 * <p>Constructor for SimpleElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param function a {@link java.util.function.Consumer} object
	 * @param px a int
	 * @param py a int
	 */
	public SimpleElement(ItemStack icon, Consumer<InventoryClickEvent> function, int px, int py) {
		pX = px; pY = py;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		func = Objects.requireNonNull(function);
	}

	/**
	 * <p>changeIcon.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public void changeIcon(ItemStack stack) {
		if (ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL))
			changeType(stack.getType());
		if (ItemStackUtil.validate(stack, IStatus.HAS_DISPLAYNAME))
			setIconDisplayName(stack.getItemMeta().getDisplayName());
		if (ItemStackUtil.validate(stack, IStatus.HAS_LORE))
			setIconLore(stack.getItemMeta().getLore());
	}

	/**
	 * <p>changeType.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 */
	public void changeType(Material mat) {
		if (mat != Material.AIR)
			icon.setType(mat);
	}

	/**
	 * <p>setIconDisplayName.</p>
	 *
	 * @param name a {@link java.lang.String} object
	 */
	public void setIconDisplayName(String name) {
		if (name != null)
			BasicMetaAdapter.setDisplayName(icon, EText.c(name));
	}

	/**
	 * <p>setIconLore.</p>
	 *
	 * @param lore a {@link java.util.List} object
	 */
	public void setIconLore(List<String> lore) {
		if (lore != null)
			BasicMetaAdapter.setLore(icon, lore);
	}

	/** {@inheritDoc} */
	@Override
	public void accept(InventoryClickEvent event) {
		if (this.equals(event.getCurrentItem()))
			if (func != null) {
				func.accept(event);
			}
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Element element) {
		if (element instanceof SimpleElement)
			return this.equals(((SimpleElement) element).icon);
		else return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(ItemStack icon) {
		return ItemStackUtil.compareDisplayName(this.icon, icon);
	}

	/** {@inheritDoc} */
	@Override
	public void placeOn(Inventory inventory, int locX, int locY) {
		inventory.setItem(locX + locY * 9, icon.clone());
	}

}
