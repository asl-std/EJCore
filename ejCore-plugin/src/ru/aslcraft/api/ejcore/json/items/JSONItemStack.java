package ru.aslcraft.api.ejcore.json.items;

import java.io.File;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.NonNull;

/**
 * <p>JSONItemStack class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class JSONItemStack {

	@Getter private ItemStack stack;

	/**
	 * <p>Constructor for JSONItemStack.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 */
	public JSONItemStack(@NonNull ItemStack stack) {

	}

	/**
	 * <p>Constructor for JSONItemStack.</p>
	 *
	 * @param file a {@link java.io.File} object
	 */
	public JSONItemStack(File file) {

	}

}
