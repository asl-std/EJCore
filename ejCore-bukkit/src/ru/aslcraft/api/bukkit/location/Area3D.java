package ru.aslcraft.api.bukkit.location;

import org.bukkit.Location;

/**
 * <p>Area3D class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Area3D {

	private Vector3D from, to;

	/**
	 * <p>Constructor for Area3D.</p>
	 *
	 * @param fPos a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 * @param sPos a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 */
	public Area3D(Vector3D fPos, Vector3D sPos) {
		from = Vector3D.getMinimum(fPos, sPos);
		to = Vector3D.getMaximum(fPos, sPos);
	}

	/**
	 * <p>getFirstPosition.</p>
	 *
	 * @return a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 */
	public Vector3D getFirstPosition() { return from.clone(); }
	/**
	 * <p>getSecondPosition.</p>
	 *
	 * @return a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 */
	public Vector3D getSecondPosition() { return to.clone(); }

	/**
	 * <p>isInArea2D.</p>
	 *
	 * @param loc a {@link org.bukkit.Location} object
	 * @return a boolean
	 */
	public boolean isInArea2D(Location loc) { return isInArea2D(Vector3D.fromLocation(loc)); }

	/**
	 * <p>isInArea3D.</p>
	 *
	 * @param loc a {@link org.bukkit.Location} object
	 * @return a boolean
	 */
	public boolean isInArea3D(Location loc) { return isInArea3D(Vector3D.fromLocation(loc)); }

	/**
	 * <p>isInArea2D.</p>
	 *
	 * @param point a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 * @return a boolean
	 */
	public boolean isInArea2D(Vector3D point) {
		return 	point.getX() >= from.getX() &&
				point.getX() <= to.getX() &&
				point.getZ() >= from.getZ() &&
				point.getZ() <= to.getZ();
	}

	/**
	 * <p>isInArea3D.</p>
	 *
	 * @param point a {@link ru.aslcraft.api.bukkit.location.Vector3D} object
	 * @return a boolean
	 */
	public boolean isInArea3D(Vector3D point) {
		return  point.getX() >= from.getX() &&
				point.getX() <= to.getX() &&
				point.getY() >= from.getY() &&
				point.getY() <= to.getY() &&
				point.getZ() >= from.getZ() &&
				point.getZ() <= to.getZ();
	}

}
