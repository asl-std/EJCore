package ru.asl.api.ejcore.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

import ru.asl.api.bukkit.location.Vector3D;

public class BlockUtil {

	public static List<Block> getBlocksCuboid(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		final Vector3D center = Vector3D.fromLocation(target.getLocation());

		if (radius%2 == 0)
			radius-=1;

		if (radius > 1)
			radius /= 2;

		for (int x = -radius ; x < radius ; x++)
			for (final int y = -radius ; y < radius ; x++)
				for (int z = -radius ; z < radius ; z++)
					blocks.add(center.getWorld().getBlockAt(center.add(new Vector3D(x,y,z)).toLocation()));

		return blocks;
	}

	public static List<Block> getBlocksFlat(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		final Vector3D center = Vector3D.fromLocation(target.getLocation());

		if (radius%2 == 0)
			radius-=1;

		if (radius > 1)
			radius /= 2;

		for (int x = -radius ; x < radius ; x++)
			for (int z = -radius ; z < radius ; z++)
				blocks.add(center.getWorld().getBlockAt(center.add(new Vector3D(x,0,z)).toLocation()));

		return blocks;

	}

}
