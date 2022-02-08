package ru.asl.api.ejinventory.element;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.api.ejcore.items.IStatus;
import ru.asl.api.ejcore.items.ItemStackUtil;
import ru.asl.api.ejcore.utils.BasicMetaAdapter;
import ru.asl.api.ejinventory.Element;

public class SimpleElement implements Element {

	@Getter private final String				hash;
	@Getter private ItemStack					icon;
	protected Consumer<InventoryClickEvent>		func;

	public final int pX, pY;

	public void setFunction(Consumer<InventoryClickEvent> func) {
		this.func = func;
	}

	public SimpleElement(ItemStack icon, boolean createFunction) {
		pX = 0; pY = 0;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		if (createFunction)
			setFunction(e -> e.setCancelled(true));
	}

	public SimpleElement(ItemStack icon, boolean createFunction, int px, int py) {
		pX = px; pY = py;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		if (createFunction)
			setFunction(e -> e.setCancelled(true));
	}


	public SimpleElement(ItemStack icon, Consumer<InventoryClickEvent> function) {
		pX = 0; pY = 0;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		func = Objects.requireNonNull(function);
	}

	public SimpleElement(ItemStack icon, Consumer<InventoryClickEvent> function, int px, int py) {
		pX = px; pY = py;
		this.icon = icon;
		hash = ItemStackUtil.toString(icon);
		func = Objects.requireNonNull(function);
	}

	public void changeIcon(ItemStack stack) {
		if (ItemStackUtil.validate(stack, IStatus.HAS_MATERIAL))
			changeType(stack.getType());
		if (ItemStackUtil.validate(stack, IStatus.HAS_DISPLAYNAME))
			setIconDisplayName(stack.getItemMeta().getDisplayName());
		if (ItemStackUtil.validate(stack, IStatus.HAS_LORE))
			setIconLore(stack.getItemMeta().getLore());
	}

	public void changeType(Material mat) {
		if (mat != Material.AIR)
			icon.setType(mat);
	}

	public void setIconDisplayName(String name) {
		if (name != null)
			BasicMetaAdapter.setDisplayName(icon, EText.c(name));
	}

	public void setIconLore(List<String> lore) {
		if (lore != null)
			BasicMetaAdapter.setLore(icon, lore);
	}

	@Override
	public void accept(InventoryClickEvent event) {
		if (this.equals(event.getCurrentItem()))
			if (func != null) {
				func.accept(event);
			}
	}

	@Override
	public boolean equals(Element element) {
		if (element instanceof SimpleElement)
			return this.equals(((SimpleElement) element).icon);
		else return false;
	}

	@Override
	public boolean equals(ItemStack icon) {
		return ItemStackUtil.compareDisplayName(this.icon, icon);
	}

	@Override
	public void placeOn(Inventory inventory, int locX, int locY) {
		inventory.setItem(locX + locY * 9, icon.clone());
	}

}
