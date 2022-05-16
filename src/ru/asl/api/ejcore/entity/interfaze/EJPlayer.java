package ru.asl.api.ejcore.entity.interfaze;

import org.bukkit.entity.Player;

import ru.asl.api.ejcore.equip.EquipInventory;
import ru.asl.api.ejcore.value.DoubleSettings;
import ru.asl.api.ejcore.value.StringSettings;

/**
 * <p>EJPlayer interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface EJPlayer {

	/**
	 * <p>getPlayer.</p>
	 *
	 * @return a {@link org.bukkit.entity.Player} object
	 */
	Player getPlayer();

	/**
	 * <p>getTempSettings.</p>
	 *
	 * @return a {@link ru.asl.api.ejcore.value.DoubleSettings} object
	 */
	DoubleSettings getTempSettings();

	/**
	 * <p>getSettings.</p>
	 *
	 * @return a {@link ru.asl.api.ejcore.value.StringSettings} object
	 */
	StringSettings getSettings();

	/**
	 * <p>updateStats.</p>
	 */
	void updateStats();

	/**
	 * <p>getEquipInventory.</p>
	 *
	 * @return a {@link ru.asl.api.ejcore.equip.EquipInventory} object
	 */
	EquipInventory getEquipInventory();
}
