package ru.asl.api.ejcore.equip;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import ru.asl.api.ejcore.entity.EPlayer;
import ru.asl.api.ejcore.items.ItemStackUtil;

/**
 *  To get Id for HAND you must use {@link EPlayer#getHeldSlot()}
 */
public enum EquipSlot {
	HAND(0), OFF(40), HEAD(39), BODY(38), LEGGS(37), FOOTS(36);

	@Getter private int slotId;

	EquipSlot() {}
	EquipSlot(int slotId) {
		this.slotId = slotId;
	}

	public static EquipSlot byID(int id) {
		for (final EquipSlot slot : values())
			if (id == slot.slotId) return slot;
		return null;
	}

	public static EquipSlot getFromItemType(Material mat, boolean checkoff) {
		if (ItemStackUtil.isHelmet(mat)) return HEAD;
		if (ItemStackUtil.isChestplate(mat)) return BODY;
		if (ItemStackUtil.isLeggings(mat)) return LEGGS;
		if (ItemStackUtil.isBoots(mat)) return FOOTS;
		if (ItemStackUtil.isShield(mat) && checkoff) return OFF;
		return HAND;
	}

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
