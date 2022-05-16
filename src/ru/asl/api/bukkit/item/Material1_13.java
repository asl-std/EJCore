package ru.asl.api.bukkit.item;

import org.bukkit.Material;

import ru.asl.api.bukkit.item.interfaze.MaterialAdapter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

/**
 * Use this only for tools, weapons and armor,<br> any other materials not included in this adapter.
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Material1_13 implements MaterialAdapter {

	/**
	 * <p>Constructor for Material1_13.</p>
	 */
	public Material1_13() {
		if (Core.getCfg().DEBUG_RUNNING)
			EText.fine("Used 1.13 adapter for materials");
	}

	/** {@inheritDoc} */
	@Override
	public Material attemptMaterial(String material) {
		if (Material.matchMaterial(material.toUpperCase()) == null) {
			if (Material.matchMaterial(material.toUpperCase(), true) == null)
				return null;
			else return Material.getMaterial(material.toUpperCase(), true);
		} else return Material.getMaterial(material.toUpperCase());
	}

}
