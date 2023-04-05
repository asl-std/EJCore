package org.aslstd.api.bukkit.material;

import org.aslstd.api.bukkit.material.interfaze.MaterialAdapter;
import org.aslstd.api.bukkit.message.EText;
import org.bukkit.Material;

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
		EText.debug("Used 1.13 adapter for materials");
	}

	/** {@inheritDoc} */
	@Override
	public Material attemptMaterial(String material) {
		Material mat = Material.matchMaterial(material.toUpperCase());
		if (mat == null)
			mat = Material.matchMaterial(material.toUpperCase(), true);

		return mat;
	}

}
