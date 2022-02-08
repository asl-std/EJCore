package ru.asl.api.ejcore.entity.interfaze;

import org.bukkit.entity.Player;

import ru.asl.api.ejcore.equip.EquipInventory;
import ru.asl.api.ejcore.value.DoubleSettings;
import ru.asl.api.ejcore.value.StringSettings;

public interface EJPlayer {

	Player getPlayer();

	DoubleSettings getTempSettings();

	StringSettings getSettings();

	void updateStats();

	EquipInventory getEquipInventory();
}
