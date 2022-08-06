package ru.aslcraft.api.ejinventory.element;

import java.util.function.Consumer;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.Setter;

/**
 * <p>FunctionalButton class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class FunctionalButton extends SimpleElement {

	/**
	 * <p>getBackBtn.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejinventory.element.FunctionalButton} object
	 */
	public static final FunctionalButton getBackBtn() {

		return null;
	}

	/** Функциональные кнопки главного меню редактора */
	public static FunctionalButton btnDisplayName;

	/** Constant <code>btnLore</code> */
	public static FunctionalButton btnLore;

	/** Constant <code>btnDescription</code> */
	public static FunctionalButton btnDescription;

	/** Constant <code>btnStats</code> */
	public static FunctionalButton btnStats;

	/** Constant <code>btnMaterial</code> */
	public static FunctionalButton btnMaterial;

	/** Constant <code>btnCustomModelData</code> */
	public static FunctionalButton btnCustomModelData;

	/** Constant <code>btnEnchantments</code> */
	public static FunctionalButton btnEnchantments;

	/** Constant <code>btnItemFlags</code> */
	public static FunctionalButton btnItemFlags;

	/** Constant <code>btnItemType</code> */
	public static FunctionalButton btnItemType;

	/** Constant <code>btnItemLevel</code> */
	public static FunctionalButton btnItemLevel;

	/** Constant <code>btnUnbreakable</code> */
	public static FunctionalButton btnUnbreakable;

	/** Constant <code>btnRepairable</code> */
	public static FunctionalButton btnRepairable;

	/** Constant <code>btnDurability</code> */
	public static FunctionalButton btnDurability;

	static {

	}

	@Setter protected Consumer<InventoryClickEvent> leftClickFunction;
	@Setter protected Consumer<InventoryClickEvent> middleClickFunction;
	@Setter protected Consumer<InventoryClickEvent> rightClickFunction;

	/**
	 * <p>Constructor for FunctionalButton.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param createFunction a boolean
	 */
	public FunctionalButton(ItemStack icon, boolean createFunction) { super(icon, createFunction); }

	/**
	 * <p>Constructor for FunctionalButton.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param createFunction a boolean
	 * @param px a int
	 * @param py a int
	 */
	public FunctionalButton(ItemStack icon, boolean createFunction, int px, int py) { super(icon, createFunction, px, py); }

	/**
	 * <p>Constructor for FunctionalButton.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param function a {@link java.util.function.Consumer} object
	 */
	public FunctionalButton(ItemStack icon, Consumer<InventoryClickEvent> function) { super(icon, function); }

	/**
	 * <p>Constructor for FunctionalButton.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @param function a {@link java.util.function.Consumer} object
	 * @param px a int
	 * @param py a int
	 */
	public FunctionalButton(ItemStack icon, Consumer<InventoryClickEvent> function, int px, int py) { super(icon, function, px, py); }

	/** {@inheritDoc} */
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
