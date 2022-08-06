package ru.aslcraft.api.bukkit.redstone.impl;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.Directional;

import ru.aslcraft.api.bukkit.redstone.Face;
import ru.aslcraft.api.bukkit.redstone.RedstoneParts;

/**
 * 1.12.2 - Lever on floor = UP;
 * 1.13.+ - Lever on floor = DOWN;
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class RedstoneParts1_12 extends RedstoneParts {

	/** {@inheritDoc} */
	@Override
	public List<Block> getBlocks(Block from) {
		final String typeName = from.getType().name();
		if (typeName.contains("LEVER") || typeName.contains("BUTTON")) {
			final Directional data = (Directional) from.getState().getData();
			switch(data.getFacing()) {
			case EAST:
			case NORTH:
			case SOUTH:
			case WEST:
				return WallLeverButton.getBlocks(from, Face.fromString(data.getFacing().name()));
			case UP:
				return CeilingLeverButton.getBlocks(from);
			case DOWN:
				return FloorLeverButton.getBlocks(from);
			default:
				break;
			}
		}

		if (typeName.contains("_PLATE"))
			return PressurePlate.getBlocks(from);

		if (typeName.contains("REDSTONE_TORCH")) {
			final Directional data = (Directional) from.getState().getData();
			switch(data.getFacing()) {
			case EAST:
			case NORTH:
			case SOUTH:
			case WEST:
				return WallRedstoneTorch.getBlocks(from, Face.fromString(data.getFacing().name()));
			default:
				break;
			}
		}

		if (from.getType() == Material.REDSTONE_WIRE)
			return RedstoneWire.getBlocks(from);

		return FloorRedstoneTorch.getBlocks(from, false);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEnabledRedstone(Material mat) {
		if (mat.name().contains("REDSTONE_TORCH") ||
				mat.name().contains("REDSTONE_BLOCK") ||
				mat.name().contains("DAYLIGHT_DETECTOR"))
			return true;

		return false;
	}

}
