package org.aslstd.api.bukkit.equip;

import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;

/**
 *  To get Id for HAND you must use {link ru.asl.api.ejcore.entity.EPlayer#????}
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public enum EquipSlot {
	HAND(0), OFF(40), HEAD(39), BODY(38), LEGGS(37), FOOTS(36), ALL(-1);

	@Getter private int slotId;

	EquipSlot() {}
	EquipSlot(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * <p>byID.</p>
	 *
	 * @param id a int
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipSlot} object
	 */
	public static EquipSlot byID(int id) {
		for (final EquipSlot slot : values())
			if (id == slot.slotId) return slot;
		return null;
	}

	/**
	 * <p>getFromItemType.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @param checkoff a boolean
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipSlot} object
	 */
	public static EquipSlot getFromItemType(Material mat, boolean checkoff) {
		if (ItemStackUtil.isHelmet(mat)) return HEAD;
		if (ItemStackUtil.isChestplate(mat)) return BODY;
		if (ItemStackUtil.isLeggings(mat)) return LEGGS;
		if (ItemStackUtil.isBoots(mat)) return FOOTS;
		if (ItemStackUtil.isShield(mat) && checkoff) return OFF;
		return HAND;
	}

	/**
	 * <p>getStackFromSlot.</p>
	 *
	 * @param slot a {@link ru.aslcraft.api.ejcore.equip.EquipSlot} object
	 * @param p a {@link org.bukkit.entity.Player} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public static ItemStack getStackFromSlot(EquipSlot slot, Player p) {
		switch(slot) {
		case HEAD:
			return p.getInventory().getHelmet();
		case BODY:
			return p.getInventory().getChestplate();
		case LEGGS:
			return p.getInventory().getLeggings();
		case FOOTS:
			return p.getInventory().getBoots();
		case HAND:
			return p.getInventory().getItemInMainHand();
		case OFF:
			return p.getInventory().getItemInOffHand();
		default:
			return new ItemStack(Material.AIR);
		}
	}

}
