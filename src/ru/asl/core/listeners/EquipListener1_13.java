package ru.asl.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseArmorEvent;

import ru.asl.api.bukkit.events.equipment.EquipChangeEvent;
import ru.asl.api.ejcore.equip.EquipSlot;

public class EquipListener1_13 implements Listener {

	@EventHandler
	public void dispenseArmor(BlockDispenseArmorEvent e) {
		if (e.getItem().getType() == Material.AIR || e.getTargetEntity().getType() != EntityType.PLAYER) return;

		final Player p = (Player) e.getTargetEntity();
		final EquipSlot slot = EquipSlot.getFromItemType(e.getItem().getType(), true);

		Bukkit.getServer().getPluginManager().callEvent(new EquipChangeEvent(slot, e.getItem(), p.getPlayer()));
	}

}
