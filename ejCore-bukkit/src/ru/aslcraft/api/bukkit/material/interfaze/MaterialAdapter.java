package ru.aslcraft.api.bukkit.material.interfaze;

import org.bukkit.Material;

/**
 * <p>MaterialAdapter interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface MaterialAdapter {

	/**
	 * <p>attemptMaterial.</p>
	 *
	 * @param material a {@link java.lang.String} object
	 * @return a {@link org.bukkit.Material} object
	 */
	public Material attemptMaterial(String material);

}
