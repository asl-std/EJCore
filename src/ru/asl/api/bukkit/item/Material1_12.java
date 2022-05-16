package ru.asl.api.bukkit.item;

import org.bukkit.Material;

import ru.asl.api.bukkit.item.interfaze.MaterialAdapter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

/**
 * <p>Material1_12 class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Material1_12 implements MaterialAdapter {

	/**
	 * <p>Constructor for Material1_12.</p>
	 */
	public Material1_12() {
		if (Core.getCfg().DEBUG_RUNNING)
			EText.fine("Used 1.12 adapter for materials");
	}

	/** {@inheritDoc} */
	@Override
	public Material attemptMaterial(String material) {
		if (Material.matchMaterial(material.toUpperCase()) == null)
			return null;
		else return Material.getMaterial(material.toUpperCase());
	}

}
