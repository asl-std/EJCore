package ru.asl.api.ejinventory.element;

import java.util.function.Consumer;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.Setter;

public class FunctionalButton extends SimpleElement {

	public static final FunctionalButton getBackBtn() {

		return null;
	}

	/** Функциональные кнопки главного меню редактора */
	public static FunctionalButton btnDisplayName;

	public static FunctionalButton btnLore;

	public static FunctionalButton btnDescription;

	public static FunctionalButton btnStats;

	public static FunctionalButton btnMaterial;

	public static FunctionalButton btnCustomModelData;

	public static FunctionalButton btnEnchantments;

	public static FunctionalButton btnItemFlags;

	public static FunctionalButton btnItemType;

	public static FunctionalButton btnItemLevel;

	public static FunctionalButton btnUnbreakable;

	public static FunctionalButton btnRepairable;

	public static FunctionalButton btnDurability;

	static {

	}

	@Setter protected Consumer<InventoryClickEvent> leftClickFunction;
	@Setter protected Consumer<InventoryClickEvent> middleClickFunction;
	@Setter protected Consumer<InventoryClickEvent> rightClickFunction;

	public FunctionalButton(ItemStack icon, boolean createFunction) { super(icon, createFunction); }

	public FunctionalButton(ItemStack icon, boolean createFunction, int px, int py) { super(icon, createFunction, px, py); }

	public FunctionalButton(ItemStack icon, Consumer<InventoryClickEvent> function) { super(icon, function); }

	public FunctionalButton(ItemStack icon, Consumer<InventoryClickEvent> function, int px, int py) { super(icon, function, px, py); }

	@Override
	public void accept(InventoryClickEvent event) {
		if (this.equals(event.getCurrentItem())) {
			if (func != null)
				func.accept(event);

			if (leftClickFunction != null)
				if (event.isLeftClick())
					leftClickFunction.accept(event);

			if (middleClickFunction != null)
				if (event.getClick() == ClickType.MIDDLE)
					middleClickFunction.accept(event);

			if (rightClickFunction != null)
				if (event.isRightClick())
					rightClickFunction.accept(event);
		}
	}

}
