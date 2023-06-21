package org.aslstd.api.bukkit.redstone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.aslstd.api.bukkit.location.Vector3D;
import org.aslstd.api.bukkit.redstone.impl.RedstoneParts1_13;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import lombok.Getter;

/**
 * <p>Abstract RedstoneParts class.</p>
 *
 * @author Snoop1CattZ69
 */
public abstract class RedstoneParts {

	@Getter private static RedstoneParts redstoneParts;

	public static final void init() {
		redstoneParts = new RedstoneParts1_13();
	}

	/**
	 * <p>getBlocks.</p>
	 *
	 * @param from a {@link org.bukkit.block.Block} object
	 * @return a {@link java.util.List} object
	 */
	public abstract List<Block> getBlocks(Block from);

	/**
	 * <p>isEnabledRedstone.</p>
	 *
	 * @param mat a {@link org.bukkit.Material} object
	 * @return a boolean
	 */
	public abstract boolean isEnabledRedstone(Material mat);

	private static List<Block> collectPoweredBlocks(List<Block> list) {
		final List<Block> powered = new ArrayList<>();

		for (final Block b : list)
			if (b.getType().isSolid() && b.isBlockPowered())
				for (final Vector3D vec : FloorRedstoneTorch.vectors)
					powered.add(b.getLocation().getWorld().getBlockAt(vec.addTo(b.getLocation())));

		if (!powered.isEmpty())
			list.addAll(powered);

		return list;
	}

	/**
	 * <p>getNearBlocks.</p>
	 *
	 * @param from a {@link org.bukkit.block.Block} object
	 * @return a {@link java.util.List} object
	 */
	public static List<Block> getNearBlocks(Block from) {
		final List<Block> near = new ArrayList<>();
		final World w = from.getLocation().getWorld();

		for (final Vector3D vec : FloorRedstoneTorch.vectors)
			near.add(w.getBlockAt(vec.addTo(from.getLocation())));

		return near;
	}

	/**
	 * <p>blockWillBePowered.</p>
	 *
	 * @param from a {@link org.bukkit.block.Block} object
	 * @return a boolean
	 */
	public static boolean blockWillBePowered(Block from) {
		final List<Block> powered = new ArrayList<>();
		final World w = from.getLocation().getWorld();

		for (final Vector3D vec : FloorRedstoneTorch.vectors)
			powered.add(w.getBlockAt(vec.addTo(from.getLocation())));

		return !powered.stream().filter(b -> (b.isBlockPowered() || redstoneParts.isEnabledRedstone(b.getType()))).collect(Collectors.toList()).isEmpty();
	}

	protected static class WallLeverButton {
		private WallLeverButton() {}

		public static List<Block> getBlocks(Block from, Face face) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			final Block relative = w.getBlockAt(face.opposite().vec().addTo(from.getLocation()));

			for (final Vector3D vec : FloorRedstoneTorch.vectors) {
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));
				blocks.add(w.getBlockAt(vec.addTo(relative.getLocation())));
			}

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

	protected static class FloorLeverButton {
		private FloorLeverButton() {}

		public static List<Block> getBlocks(Block from) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			for (final Vector3D vec : FloorRedstoneTorch.vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			for (final Vector3D vec : PressurePlate.vectors)
				blocks.add(w.getBlockAt(vec.reverse().addTo(from.getLocation())));

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

	protected static class CeilingLeverButton {
		private CeilingLeverButton() {}

		public static List<Block> getBlocks(Block from) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			for (final Vector3D vec : FloorRedstoneTorch.vectors)
				if (vec.getY() != 1)
					blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));
				else
					blocks.add(w.getBlockAt(vec.reverse().addTo(from.getLocation())));

			for (final Vector3D vec : PressurePlate.vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

	protected static class PressurePlate {
		private PressurePlate() {}

		private static final Vector3D[] vectors = {
		                                           new Vector3D(-1, -1, 0),
		                                           new Vector3D(1, -1, 0),
		                                           new Vector3D(0, -1, 1),
		                                           new Vector3D(0, -1, -1),
		                                           new Vector3D(0, -2, 0)
		};

		public static List<Block> getBlocks(Block from) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			for (final Vector3D vec : vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			for (final Vector3D vec : FloorRedstoneTorch.vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

	protected static class RedstoneWire {
		private RedstoneWire() {}

		private static final Vector3D[] vectors = {
		                                           new Vector3D(-1, -1, 0),
		                                           new Vector3D(1, -1, 0),
		                                           new Vector3D(0, -1, 1),
		                                           new Vector3D(0, -1, -1),
		                                           new Vector3D(0, -2, 0)
		};

		public static List<Block> getBlocks(Block from) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			for (final Vector3D vec : vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			for (final Vector3D vec : FloorRedstoneTorch.vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

	protected static class FloorRedstoneTorch { //Floor Redstone Torch
		private FloorRedstoneTorch() {}

		private static final Vector3D[] vectors = {
		                                           new Vector3D(1, 0, 0),
		                                           new Vector3D(-1, 0, 0),
		                                           new Vector3D(0, 1, 0),
		                                           new Vector3D(0, -1, 0),
		                                           new Vector3D(0, 0, 1),
		                                           new Vector3D(0, 0, -1),
		};

		public static List<Block> getBlocks(Block from, boolean isTorch) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			for (final Vector3D vec : vectors)
				blocks.add(w.getBlockAt(vec.addTo(from.getLocation())));

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

	protected static class WallRedstoneTorch {
		private WallRedstoneTorch() {}

		public static List<Block> getBlocks(Block from, Face face) {
			final List<Block> blocks = new ArrayList<>();
			final World w = from.getWorld();

			blocks.add(w.getBlockAt(face.vec().addTo(from.getLocation())));
			blocks.add(w.getBlockAt(face.next().vec().addTo(from.getLocation())));
			blocks.add(w.getBlockAt(face.previous().vec().addTo(from.getLocation())));

			final Block block = w.getBlockAt(Face.UP.vec().addTo(from.getLocation()));
			if (block.getType().isSolid())
				blocks.add(w.getBlockAt(new Vector3D(0,2,0).addTo(from.getLocation())));
			blocks.add(block);
			blocks.add(w.getBlockAt(Face.DOWN.vec().addTo(from.getLocation())));

			return collectPoweredBlocks(blocks.stream().filter(b -> b.getType() != Material.AIR).collect(Collectors.toList()));
		}

	}

}
