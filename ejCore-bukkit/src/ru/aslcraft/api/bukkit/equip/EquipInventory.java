package ru.aslcraft.api.bukkit.equip;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

/**
 * <p>EquipInventory class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class EquipInventory {

	/*
	 * |=========================|
	 * | 0 = hand  | 1 = offhand |
	 * | 2 = head  | 3 = body    |
	 * | 4 = leggs | 5 = foots   |
	 * |=========================|
	 */
	/**
	 * <p>Constructor for EquipInventory.</p>
	 */
	public EquipInventory() {}

	private HashMap<Integer, ItemStack> equipments = new HashMap<>();

	/**
	 * <p>getHand.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack getHand()    	 { 	return this.getEquip(EquipSlot.HAND);	}
	/**
	 * <p>getOffHand.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack getOffHand() 	 { 	return this.getEquip(EquipSlot.OFF); 	}
	/**
	 * <p>getHead.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack getHead()    	 { 	return this.getEquip(EquipSlot.HEAD);	}
	/**
	 * <p>getBody.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack getBody()    	 { 	return this.getEquip(EquipSlot.BODY);	}
	/**
	 * <p>getLeggs.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack getLeggs()   	 { 	return this.getEquip(EquipSlot.LEGGS);	}
	/**
	 * <p>getFoots.</p>
	 *
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack getFoots()   	 { 	return this.getEquip(EquipSlot.FOOTS);	}

	/**
	 * <p>setHand.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	public EquipInventory setHand    (ItemStack stack) { this.setItem(EquipSlot.HAND, 	stack); return this; }
	/**
	 * <p>setOffHand.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	public EquipInventory setOffHand (ItemStack stack) { this.setItem(EquipSlot.OFF, 	stack); return this; }
	/**
	 * <p>setHead.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	public EquipInventory setHead    (ItemStack stack) { this.setItem(EquipSlot.HEAD, 	stack); return this; }
	/**
	 * <p>setBody.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	public EquipInventory setBody    (ItemStack stack) { this.setItem(EquipSlot.BODY, 	stack); return this; }
	/**
	 * <p>setLeggs.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	public EquipInventory setLeggs   (ItemStack stack) { this.setItem(EquipSlot.LEGGS, 	stack); return this; }
	/**
	 * <p>setFoots.</p>
	 *
	 * @param stack a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	public EquipInventory setFoots   (ItemStack stack) { this.setItem(EquipSlot.FOOTS, 	stack); return this; }

	/**
	 * <p>getEquip.</p>
	 *
	 * @param slot a {@link ru.aslcraft.api.ejcore.equip.EquipSlot} object
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack 	getEquip(EquipSlot slot) 				  { return this.getEquip(slot.getSlotId()); }
	/**
	 * <p>getEquip.</p>
	 *
	 * @param id a int
	 * @return a {@link org.bukkit.inventory.ItemStack} object
	 */
	public ItemStack 	getEquip(int id) 				 		  { return equipments.get(id); 	}
	/**
	 * <p>setItem.</p>
	 *
	 * @param slot a {@link ru.aslcraft.api.ejcore.equip.EquipSlot} object
	 * @param equip a {@link org.bukkit.inventory.ItemStack} object
	 */
	public void 		setItem (EquipSlot slot, ItemStack equip) { this.setItem(slot.getSlotId(), equip); 	}

	/**
	 * <p>setItem.</p>
	 *
	 * @param id a int
	 * @param equip a {@link org.bukkit.inventory.ItemStack} object
	 */
	public void setItem (int id, ItemStack equip) { equipments.put(id, equip); }

}

