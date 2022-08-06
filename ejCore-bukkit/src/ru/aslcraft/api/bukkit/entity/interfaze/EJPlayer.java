package ru.aslcraft.api.bukkit.entity.interfaze;

import org.bukkit.entity.Player;

import ru.aslcraft.api.bukkit.equip.EquipInventory;
import ru.aslcraft.api.bukkit.value.DoubleSettings;
import ru.aslcraft.api.bukkit.value.StringSettings;

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
	 * @return a {@link ru.aslcraft.api.ejcore.value.DoubleSettings} object
	 */
	DoubleSettings getTempSettings();

	/**
	 * <p>getSettings.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejcore.value.StringSettings} object
	 */
	StringSettings getSettings();

	/**
	 * <p>updateStats.</p>
	 */
	void updateStats();

	/**
	 * <p>getEquipInventory.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejcore.equip.EquipInventory} object
	 */
	EquipInventory getEquipInventory();
}
