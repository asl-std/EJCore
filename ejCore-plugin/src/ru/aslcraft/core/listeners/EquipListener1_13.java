package ru.aslcraft.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;

import ru.aslcraft.api.bukkit.equip.EquipSlot;
import ru.aslcraft.api.bukkit.events.equipment.EquipChangeEvent;

/**
 * <p>EquipListener1_13 class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class EquipListener1_13 implements Listener {

	/**
	 * <p>dispenseArmor.</p>
	 *
	 * @param e a {@link org.bukkit.event.block.BlockDispenseArmorEvent} object
	 */
	@EventHandler
	public void dispenseArmor(BlockDispenseArmorEvent e) {
		if (e.getItem().getType() == Material.AIR || e.getTargetEntity().getType() != EntityType.PLAYER) return;

		final Player p = (Player) e.getTargetEntity();
		final EquipSlot slot = EquipSlot.getFromItemType(e.getItem().getType(), true);

		Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(slot, e.getItem(), p.getPlayer()));
	}

}
