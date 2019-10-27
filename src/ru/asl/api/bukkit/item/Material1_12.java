package ru.asl.api.bukkit.item;

import org.bukkit.Material;

import ru.asl.api.bukkit.item.interfaze.MaterialAdapter;
import ru.asl.api.bukkit.message.EText;
import ru.asl.core.Core;

public class Material1_12 implements MaterialAdapter {

	public Material1_12() {
		if (Core.getCfg().DEBUG_RUNNING)
			EText.fine("Used 1.12 adapter for materials");
	}

	@Override
	public Material attemptMaterial(String material) {
		if (Material.matchMaterial(material.toUpperCase()) == null)
			return null;
		else return Material.getMaterial(material.toUpperCase());
	}

}
