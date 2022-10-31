package org.aslstd.api.bukkit.material;

import org.aslstd.api.bukkit.material.interfaze.MaterialAdapter;
import org.aslstd.api.bukkit.message.EText;
import org.bukkit.Material;

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
		EText.debug("Used 1.12 adapter for materials");
	}

	/** {@inheritDoc} */
	@Override
	public Material attemptMaterial(String material) {
		if (Material.matchMaterial(material.toUpperCase()) == null)
			return null;
		else return Material.getMaterial(material.toUpperCase());
	}

}
