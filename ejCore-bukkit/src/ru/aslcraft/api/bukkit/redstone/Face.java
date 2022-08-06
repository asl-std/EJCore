package ru.aslcraft.api.bukkit.redstone;

import java.util.Arrays;
import java.util.List;

import org.bukkit.block.BlockFace;

import lombok.AllArgsConstructor;
import ru.aslcraft.api.bukkit.location.Vector3D;
import ru.aslcraft.api.bukkit.value.Pair;

/**
 * <p>Face class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
@AllArgsConstructor
public enum Face {
	NORTH(new Vector3D(0,0,-1)),
	WEST(new Vector3D(-1,0,0)),
	SOUTH(new Vector3D(0,0,1)),
	EAST(new Vector3D(1,0,0)),
	UP(new Vector3D(0,1,0)),
	DOWN(new Vector3D(0,-1,0));

	/**
	 * <p>vec.</p>
	 *
	 * @return a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 */
	final Vector3D vec;

	public Vector3D vec() {
		return vec;
	}

	/**
	 * <p>sides.</p>
	 *
	 * @param height a int
	 * @return a {@link java.util.List} object
	 */
	public static List<Pair<Face, Vector3D>> sides(int height) {
		return Arrays.asList(
				new Pair<>(NORTH, NORTH.vec.setY(height)),
				new Pair<>(WEST, WEST.vec.setY(height)),
				new Pair<>(SOUTH, SOUTH.vec.setY(height)),
				new Pair<>(EAST, EAST.vec.setY(height))
				);
	}

	/**
	 * <p>toInt.</p>
	 *
	 * @return a int
	 */
	public int toInt() {
		switch(this) {
		case NORTH:
			return 0;
		case WEST:
			return 1;
		case SOUTH:
			return 2;
		case EAST:
			return 3;
		default:
			return -1;
		}
	}

	/**
	 * <p>next.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejcore.redstone.Face} object
	 */
	public Face next() {
		switch(this) {
		case NORTH:
			return WEST;
		case WEST:
			return SOUTH;
		case SOUTH:
			return EAST;
		case EAST:
			return NORTH;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		default:
			return NORTH;
		}
	}

	/**
	 * <p>previous.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejcore.redstone.Face} object
	 */
	public Face previous() {
		switch(this) {
		case NORTH:
			return EAST;
		case WEST:
			return NORTH;
		case SOUTH:
			return WEST;
		case EAST:
			return SOUTH;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		default:
			return NORTH;
		}
	}

	/**
	 * <p>opposite.</p>
	 *
	 * @return a {@link ru.aslcraft.api.ejcore.redstone.Face} object
	 */
	public Face opposite() {
		switch(this) {
		case NORTH:
			return SOUTH;
		case WEST:
			return EAST;
		case SOUTH:
			return NORTH;
		case EAST:
			return WEST;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		default:
			return NORTH;
		}
	}

	/**
	 * <p>toBlockFace.</p>
	 *
	 * @return a {@link org.bukkit.block.BlockFace} object
	 */
	public BlockFace toBlockFace() {
		switch(this) {
		case NORTH:
			return BlockFace.NORTH;
		case WEST:
			return BlockFace.WEST;
		case SOUTH:
			return BlockFace.SOUTH;
		case EAST:
			return BlockFace.EAST;
		case UP:
			return BlockFace.UP;
		case DOWN:
			return BlockFace.DOWN;
		default:
			return BlockFace.NORTH;
		}
	}

	/**
	 * <p>fromString.</p>
	 *
	 * @param face a {@link java.lang.String} object
	 * @return a {@link ru.aslcraft.api.ejcore.redstone.Face} object
	 */
	public static Face fromString(String face) {
		switch(face.toUpperCase()) {
		case "NORTH":
			return NORTH;
		case "WEST":
			return WEST;
		case "SOUTH":
			return SOUTH;
		case "EAST":
			return EAST;
		case "UP":
			return UP;
		case "DOWN":
			return DOWN;
		default:
			return NORTH;
		}
	}
}
