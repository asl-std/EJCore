package ru.asl.api.ejcore.equip;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

public class EquipInventory {

	/* |=========================|
	 * | 0 = hand  | 1 = offhand |;
	 * | 2 = head  | 3 = body    |;
	 * | 4 = leggs | 5 = foots   |;
	 * |=========================|
	 */
	public EquipInventory() {}

	private HashMap<Integer, ItemStack> equipments = new HashMap<>();

	public ItemStack getHand()    { return this.getEquip(EquipSlot.HAND);	 }
	public ItemStack getOffHand() { return this.getEquip(EquipSlot.OFF); }
	public ItemStack getHead()    { return this.getEquip(EquipSlot.HEAD);	 }
	public ItemStack getBody()    { return this.getEquip(EquipSlot.BODY);	 }
	public ItemStack getLeggs()   { return this.getEquip(EquipSlot.LEGGS);	 }
	public ItemStack getFoots()   { return this.getEquip(EquipSlot.FOOTS);	 }

	public EquipInventory setHand    (ItemStack stack) { this.setItem(EquipSlot.HAND, 	 stack); return this; }
	public EquipInventory setOffHand (ItemStack stack) { this.setItem(EquipSlot.OFF, stack); return this; }
	public EquipInventory setHead    (ItemStack stack) { this.setItem(EquipSlot.HEAD, 	 stack); return this; }
	public EquipInventory setBody    (ItemStack stack) { this.setItem(EquipSlot.BODY, 	 stack); return this; }
	public EquipInventory setLeggs   (ItemStack stack) { this.setItem(EquipSlot.LEGGS, 	 stack); return this; }
	public EquipInventory setFoots   (ItemStack stack) { this.setItem(EquipSlot.FOOTS, 	 stack); return this; }

	public ItemStack 	getEquip(EquipSlot slot) 				  { return this.getEquip(slot.getSlotId()); }
	public ItemStack 	getEquip(int id) 				 		  { return equipments.get(id); 	}
	public void 		setItem (EquipSlot slot, ItemStack equip) { this.setItem(slot.getSlotId(), equip); 	}

	public void setItem (int id, ItemStack equip) { equipments.put(id, equip); }

}

