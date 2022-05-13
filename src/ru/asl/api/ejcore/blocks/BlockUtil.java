package ru.asl.api.ejcore.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

import ru.asl.api.bukkit.location.Vector3D;

public class BlockUtil {

	public List<Block> getBlocksInRadius(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		final Vector3D center = Vector3D.fromLocation(target.getLocation());

		for (int x = -radius ; x < radius ; x++)
			for (final int y = -radius ; y < radius ; x++)
				for (int z = -radius ; z < radius ; z++)
					blocks.add(center.getWorld().getBlockAt(center.add(new Vector3D(x,y,z)).toLocation()));

		return blocks;
	}

}
